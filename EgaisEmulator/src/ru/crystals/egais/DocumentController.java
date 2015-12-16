package ru.crystals.egais;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ru.crystals.egais.documents.ConclusionType;
import ru.crystals.egais.documents.Documents;
import ru.crystals.egais.documents.Documents.Document;
import ru.crystals.egais.documents.ObjectFactory;
import ru.crystals.egais.documents.TicketResultType;
import ru.crystals.egais.documents.TicketType;

public class DocumentController {
    private static final String docFolder = "Documents/";
    private static ObjectFactory documentsFactory = new ObjectFactory();

    public static String getDocumentsListAnswer() throws Exception {
        File folder = new File(docFolder);
        if (folder.exists() && folder.isDirectory()) {
            return AnswerController.makeAnswer(null, Arrays.stream(folder.list())
                    .map(item -> {
                        String url = null;
                        File f = new File(docFolder + item);
                        if (f.exists() && f.isFile()) {
                            String docType = StringUtils.substringBefore(item, "_");
                            String docIndex = StringUtils.substringAfter(item, "_");
                            url = "http://localhost:8080/opt/out/" + docType + "/" + docIndex;
                        }
                        return url;
                    })
                    .filter(url -> url != null)
                    .collect(Collectors.toList())
                    .toArray(new String[0]));
        }
        return null;
    }

    public static String getDocument(String docType, long id) {
        File file = new File(docFolder + docType + "_" + id + ".xml");
        if (file.exists()) {
            try {
                return FileUtils.readFileToString(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String deleteDocument(String docType, long id) {
        File file = new File(docFolder + docType + "_" + id + ".xml");
        if (file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String postWayBillAct(MultipartFile file) throws Exception {
        String xml = Commons.openFile(file);
        System.out.println(xml);
        Documents wayBillAct = (Documents) Marchallers.getUnmarshaller(Documents.class).unmarshal(new StringReader(xml));
        String replyId = makeTicket(wayBillAct);
        return AnswerController.makeAnswer(Commons.generateLongSign(), replyId);
    }

    private static String makeTicket(Documents wayBillAct) throws Exception {
        Documents document = documentsFactory.createDocuments();
        document.setVersion("1.0");
        document.setOwner(wayBillAct.getOwner());

        Document doc = documentsFactory.createDocumentsDocument();
        TicketType ticket = documentsFactory.createTicketType();

        GregorianCalendar calendar = new GregorianCalendar();
        ticket.setTicketDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
        ticket.setIdentity(RandomStringUtils.randomNumeric(10));
        ticket.setDocId(Commons.generateShortSign().toUpperCase());
        ticket.setTransportId(Commons.generateShortSign());
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
            Marchallers.getMarshaller(Documents.class).marshal(document, stream);
            File filename = new File(docFolder + "Ticket_" + RandomUtils.nextInt(1, 300) + ".xml");
            FileUtils.writeStringToFile(filename, stream.toString());
        }

        return ticket.getTransportId();
    }

}