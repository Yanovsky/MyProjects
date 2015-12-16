package ru.crystals.egais.generators;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import ru.crystals.egais.Marchallers;
import ru.crystals.egais.documents.Documents;
import ru.crystals.egais.documents.Documents.Document;
import ru.crystals.egais.documents.InformBPositionType;
import ru.crystals.egais.documents.ObjectFactory;
import ru.crystals.egais.documents.OrgAddressType;
import ru.crystals.egais.documents.OrgInfo;
import ru.crystals.egais.documents.PositionType;
import ru.crystals.egais.documents.ProductInfo;
import ru.crystals.egais.documents.SenderInfo;
import ru.crystals.egais.documents.TransportType;
import ru.crystals.egais.documents.WayBillInformBRegType;
import ru.crystals.egais.documents.WayBillType;
import ru.crystals.egais.documents.WayBillType.Content;
import ru.crystals.egais.documents.WayBillType.Header;
import ru.crystals.egais.documents.WbType;
import ru.crystals.egais.documents.WbUnitType;

public enum WayBillGenerator {
    INSTANCE;

    private static final String docFolder = "Documents/";
    private static ObjectFactory factory = new ObjectFactory();

    private int startIndex = 1;
    private String identity = RandomStringUtils.randomNumeric(5);
    private String wayBillNumber = RandomStringUtils.randomNumeric(5);

    public static void main(String[] args) {
        WayBillGenerator.INSTANCE.setStartIndex(1);
        for (int i = 1; i <= 10; i++) {
            WayBillGenerator.INSTANCE.setWayBillNumber(RandomStringUtils.randomAlphanumeric(5)).generate("3463047");
        }
    }

    public void generate(String fsRARID) {
        identity = RandomStringUtils.randomNumeric(5);
        Documents wayBill = generateWayBill(fsRARID, startIndex++);
        generateBREGINFORM(wayBill, startIndex++);
    }

    private Documents generateWayBill(String fsRARID, int index) {
        Documents documents = factory.createDocuments();
        documents.setVersion("1.0");
        SenderInfo owner = factory.createSenderInfo();
        owner.setFSRARID(fsRARID);
        documents.setOwner(owner);

        Document doc = factory.createDocumentsDocument();
        WayBillType wayBill = factory.createWayBillType();

        wayBill.setIdentity(identity);

        Header header = factory.createWayBillTypeHeader();
        header.setNUMBER(wayBillNumber);
        header.setDate(getNow());
        header.setShippingDate(getNow());
        header.setUnitType(WbUnitType.PACKED);
        header.setType(WbType.WB_INVOICE_FROM_ME);
        header.setShipper(generateSupplier());
        header.setTransport(generateTransport());
        header.setSupplier(generateSupplier());
        header.setConsignee(generateConsignee());
        wayBill.setHeader(header);

        wayBill.setContent(generatePositions(RandomUtils.nextInt(5, 20)));

        doc.setWayBill(wayBill);
        documents.setDocument(doc);

        saveToFile(documents, docFolder + "WayBill_" + index + ".xml");
        return documents;
    }

    private Content generatePositions(int count) {
        Content content = factory.createWayBillTypeContent();
        for (int i = 0; i < count; i++) {
            PositionType pos = factory.createPositionType();
            pos.setIdentity(String.valueOf(i + 1));
            pos.setPackID("Палета");
            pos.setPrice(BigDecimal.valueOf(RandomUtils.nextDouble(10.0, 1000.0)).setScale(2, RoundingMode.HALF_UP));
            pos.setQuantity(BigDecimal.valueOf(RandomUtils.nextDouble(1, 100)).setScale(0, RoundingMode.HALF_UP));
            ProductInfo product = factory.createProductInfo();
            product.setAlcCode(RandomStringUtils.randomNumeric(19));
            product.setAlcVolume(BigDecimal.valueOf(RandomUtils.nextDouble(5.0, 41.0)).setScale(1, RoundingMode.HALF_UP));
            product.setCapacity(BigDecimal.valueOf(RandomUtils.nextDouble(0.2, 5.0)).setScale(1, RoundingMode.HALF_UP));
            product.setShortName("Алкогольный товар");
            product.setFullName(product.getShortName() + " " + product.getAlcVolume() + "% об.");
            product.setProductVCode("200");
            pos.setProduct(product);
            content.getPosition().add(pos);
        }
        return content;
    }

    private OrgInfo generateSupplier() {
        OrgInfo result = factory.createOrgInfo();
        result.setFullName("ООО \"Рога и Копыта\"");
        result.setShortName("Рога и Копыта");
        result.setClientRegId(RandomStringUtils.randomNumeric(8));
        result.setINN(new StringElement("4221001176"));
        result.setKPP("420532005");
        result.setAddress(generateAddress());
        return result;
    }

    private OrgAddressType generateAddress() {
        OrgAddressType address = factory.createOrgAddressType();
        address.setArea("643");
        address.setCity("Москва");
        address.setRegionCode("77");
        return address;
    }

    private TransportType generateTransport() {
        TransportType result = factory.createTransportType();
        result.setTRANTYPE("413");
        result.setTRANCOMPANY("ООО \"Азурит\"");
        return result;
    }

    private OrgInfo generateConsignee() {
        OrgInfo result = factory.createOrgInfo();
        result.setFullName("ООО \"Дримкас\"");
        result.setShortName("Дримкас");
        result.setClientRegId(RandomStringUtils.randomNumeric(8));
        result.setINN(new StringElement("4715026477"));
        result.setKPP("471545004");
        result.setAddress(generateAddress());
        return result;
    }

    private void generateBREGINFORM(Documents wayBill, int index) {
        Documents documents = factory.createDocuments();
        documents.setVersion("1.0");
        documents.setOwner(wayBill.getOwner());

        Document doc = factory.createDocumentsDocument();
        WayBillInformBRegType breg = factory.createWayBillInformBRegType();

        ru.crystals.egais.documents.WayBillInformBRegType.Header header = factory.createWayBillInformBRegTypeHeader();
        header.setIdentity(wayBill.getDocument().getWayBill().getIdentity());
        header.setConsignee(wayBill.getDocument().getWayBill().getHeader().getConsignee());
        header.setShipper(wayBill.getDocument().getWayBill().getHeader().getShipper());
        header.setSupplier(wayBill.getDocument().getWayBill().getHeader().getShipper());
        header.setWBRegId(RandomStringUtils.randomNumeric(10));
        header.setWBNUMBER(wayBill.getDocument().getWayBill().getHeader().getNUMBER());
        header.setWBDate(wayBill.getDocument().getWayBill().getHeader().getDate());
        breg.setHeader(header);

        ru.crystals.egais.documents.WayBillInformBRegType.Content content = factory.createWayBillInformBRegTypeContent();
        content.getPosition().addAll(
                wayBill.getDocument().getWayBill().getContent().getPosition().stream()
                        .map(wp -> {
                            InformBPositionType pos = factory.createInformBPositionType();
                            pos.setIdentity(wp.getIdentity());
                            pos.setInformBRegId(RandomStringUtils.randomNumeric(15));
                            return pos;
                        })
                        .collect(Collectors.toList()));
        breg.setContent(content);

        doc.setTTNInformBReg(breg);
        documents.setDocument(doc);
        saveToFile(documents, docFolder + "FORMBREGINFO_" + index + ".xml");
    }

    private void saveToFile(Documents documents, String filename) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            Marchallers.getMarshaller(Documents.class).marshal(documents, stream);
            File file = new File(filename);
            FileUtils.writeStringToFile(file, stream.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private XMLGregorianCalendar getNow() {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WayBillGenerator setIdentity(String identity) {
        this.identity = identity;
        return this;
    }

    public WayBillGenerator setWayBillNumber(String wayBillNumber) {
        this.wayBillNumber = wayBillNumber;
        return this;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
