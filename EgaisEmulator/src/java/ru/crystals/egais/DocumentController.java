package ru.crystals.egais;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ru.crystals.egais.documents.ConclusionType;
import ru.crystals.egais.documents.ConfirmTicketType;
import ru.crystals.egais.documents.ConfirmTicketType.Header;
import ru.crystals.egais.documents.Documents;
import ru.crystals.egais.documents.Documents.Document;
import ru.crystals.egais.documents.ObjectFactory;
import ru.crystals.egais.documents.SenderInfo;
import ru.crystals.egais.documents.TicketResultType;
import ru.crystals.egais.documents.TicketType;

public class DocumentController {
    private static final String docFolder = "documents/";
    private static final String tmpTicketFolder = docFolder + "Tickets/";
    private static final String trashFolder = docFolder + "Trash/";
    private static ObjectFactory documentsFactory = new ObjectFactory();
    private static String wayBillList = null;

    public static String getDocumentsList(boolean refresh, String localAddress) throws Exception {
        if (StringUtils.isBlank(wayBillList)) {
            wayBillList = AnswerController.makeAnswer(null);
        }
        if (refresh) {
            File folder = new File(docFolder);
            if (folder.exists() && folder.isDirectory()) {
                wayBillList = AnswerController.makeAnswer(null, Arrays.stream(folder.list())
                        .map(item -> {
                            String url = null;
                            File f = new File(docFolder + item);
                            if (f.exists() && f.isFile()) {
                                String docType = StringUtils.substringBefore(item, "_");
                                String docIndex = StringUtils.substringAfter(item, "_");
                                url = "http://" + localAddress + "/opt/out/" + docType + "/" + docIndex;
                            }
                            return url;
                        })
                        .filter(url -> StringUtils.isNotBlank(url) && StringUtils.endsWithIgnoreCase(url, ".xml"))
                        .collect(Collectors.toList())
                        .toArray(new String[0]));
            }
        }
        return wayBillList;
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
        File destFile = new File(trashFolder + docType + "_" + id + ".xml");
        if (file.exists()) {
            try {
                FileUtils.copyFile(file, destFile);
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String postWayBillAct(MultipartFile file) throws Exception {
        String xml = Commons.openFile(file);
        Documents wayBillAct = (Documents) Marchallers.getUnmarshaller(Documents.class).unmarshal(new StringReader(xml));
        String replyId = makeTicket(wayBillAct);
        if (wayBillAct.getDocument().getWayBillAct().getContent().getPosition().size() > 0) {
            makeConfirmTicket(wayBillAct);
        }
        return AnswerController.makeAnswer(Commons.generateLongSign(), replyId);
    }

    private static void makeConfirmTicket(Documents wayBillAct) throws Exception {
        Documents document = documentsFactory.createDocuments();
        document.setVersion("1.0");

        SenderInfo owner = documentsFactory.createSenderInfo();
        owner.setFSRARID(RandomStringUtils.randomNumeric(8));
        document.setOwner(owner);

        Document doc = documentsFactory.createDocumentsDocument();
        ConfirmTicketType ticket = documentsFactory.createConfirmTicketType();

        GregorianCalendar calendar = new GregorianCalendar();

        Header header = documentsFactory.createConfirmTicketTypeHeader();
        header.setIsConfirm(ConclusionType.ACCEPTED);
        header.setTicketDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
        header.setTicketNumber(RandomStringUtils.randomNumeric(4));
        header.setWBRegId(wayBillAct.getDocument().getWayBillAct().getHeader().getWBRegId());
        header.setNote("Документ успешно принят.");
        ticket.setHeader(header);
        doc.setConfirmTicket(ticket);
        document.setDocument(doc);

        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            Marchallers.getMarshaller(Documents.class).marshal(document, stream);
            File file = new File(tmpTicketFolder + "WayBillTicket_" + RandomUtils.nextInt(1, 300) + ".xml");
            FileUtils.writeStringToFile(file, stream.toString());

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    File destFile = new File(docFolder + file.getName());
                    try {
                        FileUtils.copyFile(file, destFile);
                        FileUtils.forceDelete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 6000);

        }
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
            File file = new File(tmpTicketFolder + "Ticket_" + RandomUtils.nextInt(1, 300) + ".xml");
            FileUtils.writeStringToFile(file, stream.toString());

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    File destFile = new File(docFolder + file.getName());
                    try {
                        FileUtils.copyFile(file, destFile);
                        FileUtils.forceDelete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 5000);

        }

        return ticket.getTransportId();
    }

}
