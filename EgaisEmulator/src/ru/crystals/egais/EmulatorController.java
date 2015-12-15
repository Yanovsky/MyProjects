package ru.crystals.egais;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ru.crystals.egais.answer.Answer;
import ru.crystals.egais.answer.AnswerURL;
import ru.crystals.egais.answer.ObjectFactory;
import ru.crystals.egais.documents.ConclusionType;
import ru.crystals.egais.documents.Documents;
import ru.crystals.egais.documents.Documents.Document;
import ru.crystals.egais.documents.TicketResultType;
import ru.crystals.egais.documents.TicketType;

@RestController
public class EmulatorController {
    ObjectFactory answerFactory = new ObjectFactory();
    ru.crystals.egais.documents.ObjectFactory documentsFactory = new ru.crystals.egais.documents.ObjectFactory();
    private static final Map<Class<?>, Unmarshaller> unmarshallers = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Marshaller> marshallers = new ConcurrentHashMap<>();
    private static final String docFolder = "Documents";

    @RequestMapping("/opt/out")
    public String getDocumentsList(@RequestParam(value = "refresh", defaultValue = "false") String refresh) throws Exception {
        Answer answer = answerFactory.createAnswer();
        answer.setVer(1);
        File folder = new File(docFolder);
        if (folder.exists() && folder.isDirectory()) {
            answer.getUrl().addAll(
                Arrays.stream(folder.list())
                        .map(item -> {
                            AnswerURL url = null;
                            File f = new File(docFolder + "/" + item);
                            if (f.exists() && f.isFile()) {
                                String docType = StringUtils.substringBefore(item, "_");
                                String docIndex = StringUtils.substringAfter(item, "_");
                                url = new AnswerURL("http://localhost:8080/opt/out/" + docType + "/" + docIndex);
                            }
                            return url;
                        })
                        .filter(url -> url != null)
                        .collect(Collectors.toList())
            );
        }
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            getMarshaller(Answer.class).marshal(answer, result);
            return result.toString();
        }
    }

    @RequestMapping(value = "/opt/out/{docType}/{id}")
    public String getDocument(@PathVariable String docType, @PathVariable long id) {
        System.out.println("Get a " + docType + " with id=" + id);
        File file = new File(docFolder + "/" + docType + "_" + id + ".xml");
        if (file.exists()) {
            try {
                return FileUtils.readFileToString(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/opt/out/{docType}/{id}")
    public String deleteDocument(@PathVariable String docType, @PathVariable long id) {
        System.out.println("Delete a " + docType + " with id=" + id);
        File file = new File(docFolder + "/" + docType + "_" + id + ".xml");
        if (file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opt/in/WayBillAct")
    public String postWayBillAct(@RequestParam(value = "xml_file", required = false) MultipartFile file) throws Exception {
        System.out.println("postWayBillAct");
        String xml = openFile(file);
        System.out.println(xml);
        if (xml.startsWith("\uFEFF")) {
            xml = xml.substring(1);
        }
        Documents wayBillAct = (Documents) getUnmarshaller(Documents.class).unmarshal(new StringReader(xml));
        String replyId = makeTicket(wayBillAct);
        return makePostAnswer(replyId, "B92CAD115B57B02C7D12ADC488066D99B60549D57A737B0CAC18E5E3E1C72E6B8D414C763CB58A5E67DE7C8C2ECE908451C4AE6838479A42ABBA8179D0CE8");
    }

    private String makeTicket(Documents wayBillAct) throws Exception {
        Documents document = documentsFactory.createDocuments();
        document.setVersion("1.0");
        document.setOwner(wayBillAct.getOwner());

        Document doc = documentsFactory.createDocumentsDocument();
        TicketType ticket = documentsFactory.createTicketType();
        
        GregorianCalendar calendar = new GregorianCalendar();
        ticket.setTicketDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
        ticket.setIdentity(RandomStringUtils.randomNumeric(10));
        ticket.setDocId(StringUtils.upperCase(RandomStringUtils.randomAlphanumeric(8) + "-" +
                RandomStringUtils.randomAlphanumeric(4) + "-" +
                RandomStringUtils.randomAlphanumeric(4) + "-" +
                RandomStringUtils.randomAlphanumeric(4) + "-" +
                RandomStringUtils.randomAlphanumeric(12)));
        ticket.setTransportId(StringUtils.lowerCase(RandomStringUtils.randomAlphanumeric(8) + "-" +
                RandomStringUtils.randomAlphanumeric(4) + "-" +
                RandomStringUtils.randomAlphanumeric(4) + "-" +
                RandomStringUtils.randomAlphanumeric(4) + "-" +
                RandomStringUtils.randomAlphanumeric(12)));
        ticket.setRegID(RandomStringUtils.randomNumeric(10));
        ticket.setDocType("WayBill");
        TicketResultType result = documentsFactory.createTicketResultType();
        result.setConclusion(ConclusionType.ACCEPTED);
        result.setConclusionDate(ticket.getTicketDate());
        result.setComments("Документ успешно принят.");
        ticket.setResult(result);
        doc.setTicket(ticket);
        document.setDocument(doc);

        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            getMarshaller(Documents.class).marshal(document, stream);
            File filename = new File(docFolder + "/Ticket_" + RandomUtils.nextInt(1, 300) + ".xml");
            FileUtils.writeStringToFile(filename, stream.toString());
        }

        return ticket.getTransportId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/xml")
    public String postPurchase(@RequestParam(value = "xml_file", required = false) MultipartFile file) throws Exception {
        System.out.println("postPurchase");
        System.out.println(openFile(file));
        return makePostAnswer("3fbf9613-ddc3-4a6e-aa6f-3459466c2aa5", "B92CAD115B57B02C7D12ADC488066D99B60549D57A737B0CAC18E5E3E1C72E6B8D414C763CB58A5E67DE7C8C2ECE908451C4AE6838479A42ABBA8179D0CE8");
    }

    private String openFile(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                return new String(file.getBytes());
            }
        } catch (Exception e) {
        }
        return null;
    }

    private String makePostAnswer(String url, String sign) throws IOException, JAXBException {
        Answer answer = answerFactory.createAnswer();
        answer.setVer(1);
        answer.setUrl(new AnswerURL(url));
        answer.setSign(sign);
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            getMarshaller(Answer.class).marshal(answer, result);
            return result.toString();
        }
    }

    protected Unmarshaller getUnmarshaller(Class<?> clazz) throws JAXBException {
        if (!unmarshallers.containsKey(clazz)) {
            JAXBContext context = JAXBContext.newInstance(clazz.getPackage().getName());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshallers.put(clazz, unmarshaller);
        }
        return unmarshallers.get(clazz);
    }

    protected Marshaller getMarshaller(Class<?> clazz) throws JAXBException {
        if (!marshallers.containsKey(clazz)) {
            JAXBContext context = JAXBContext.newInstance(clazz.getPackage().getName());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshallers.put(clazz, marshaller);
        }
        return marshallers.get(clazz);
    }

}
