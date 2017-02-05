package ru.alex.egais;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import ru.crystals.pos.egais.documents.DocBody;
import ru.crystals.pos.egais.documents.Documents;
import ru.crystals.pos.egais.documents.FLReplyType;
import ru.crystals.pos.egais.documents.FOType;
import ru.crystals.pos.egais.documents.ObjectFactory;
import ru.crystals.pos.egais.documents.OrgAddressTypeFOTS;
import ru.crystals.pos.egais.documents.OrgAddressTypeULFLReply;
import ru.crystals.pos.egais.documents.OrgInfoReplyV2;
import ru.crystals.pos.egais.documents.ProductInfoReplyV2;
import ru.crystals.pos.egais.documents.ProductType2;
import ru.crystals.pos.egais.documents.ReplyRestsShopV2;
import ru.crystals.pos.egais.documents.ReplyRestsV2;
import ru.crystals.pos.egais.documents.ShopPositionType;
import ru.crystals.pos.egais.documents.StockPositionType2;
import ru.crystals.pos.egais.documents.TSReplyType;
import ru.crystals.pos.egais.documents.ULReplyType;
import ru.crystals.pos.egais.documents.WbUnitType3;

public class RestsGenerator {
    private static final String[] NON_EXCISE_ALCOHOL = new String[]{ "261", "262", "263", "500", "510", "520" };
    private final Marshaller marshaller;
    private final ObjectFactory factory;

    public static boolean isExcise(String vCode) {
        return StringUtils.isNotEmpty(vCode) && StringUtils.indexOfAny(vCode, NON_EXCISE_ALCOHOL) < 0;
    }

    public RestsGenerator() throws JAXBException {
        factory = new ObjectFactory();
        JAXBContext context = JAXBContext.newInstance(Documents.class.getPackage().getName());
        marshaller = context.createMarshaller();

        int maxSalesroomCount = 370;
        int maxWarehouseCount = 3;
        int count = RandomUtils.nextInt(1, maxSalesroomCount - maxWarehouseCount);
        Documents salesroom = generateSalesroom(370);
        marshall(salesroom, "ReplyRestsShop_v2.xml");
        marshall(generateWarehouse(salesroom.getDocument().getReplyRestsShopV2().getProducts().getShopPosition().subList(count, count+3)), "ReplyRests_v2.xml");
    }

    private Documents generateSalesroom(int count) {
        Documents document = factory.createDocuments();

        DocBody body = factory.createDocBody();
        ReplyRestsShopV2 rests = factory.createReplyRestsShopV2();
        ReplyRestsShopV2.Products products = factory.createReplyRestsShopV2Products();

        for (int i = 1; i <= count; i++) {
            ShopPositionType position = factory.createShopPositionType();
            position.setQuantity(BigDecimal.valueOf(RandomUtils.nextInt(1, 5)));
            //position.setInformF1RegId(RandomStringUtils.randomNumeric(15));
            //position.setInformF2RegId(RandomStringUtils.randomNumeric(15));

            ProductInfoReplyV2 product = factory.createProductInfoReplyV2();
            product.setAlcCode("00"+RandomStringUtils.randomNumeric(17));
            product.setAlcVolume(BigDecimal.valueOf(RandomUtils.nextDouble(4, 45)));
            product.setFullName("Алкогольный товар " + i);
            product.setShortName("Aлк." + i);
            product.setProductVCode(RandomStringUtils.randomNumeric(3));
            product.setType(ProductType2.АП);

            product.setUnitType(isExcise(product.getProductVCode()) ? WbUnitType3.PACKED : WbUnitType3.values()[RandomUtils.nextInt(0, WbUnitType3.values().length)]);
            if (product.getUnitType() == WbUnitType3.PACKED) {
                product.setCapacity(BigDecimal.valueOf(RandomUtils.nextDouble(0.33, 3)));
            } else {
                product.setCapacity(BigDecimal.ZERO);
            }
            product.setProducer(generateProducer());
            position.setProduct(product);
            products.getShopPosition().add(position);
        }

        rests.setProducts(products);
        body.setReplyRestsShopV2(rests);
        document.setDocument(body);

        return document;
    }

    private Documents generateWarehouse(List<ShopPositionType> shopPositions) {
        Documents document = factory.createDocuments();

        DocBody body = factory.createDocBody();
        ReplyRestsV2 rests = factory.createReplyRestsV2();
        ReplyRestsV2.Products products = factory.createReplyRestsV2Products();

        for (int i = 1; i <= shopPositions.size(); i++) {
            ShopPositionType shopPos = shopPositions.get(i - 1);
            StockPositionType2 position = factory.createStockPositionType2();
            position.setQuantity(BigDecimal.valueOf(RandomUtils.nextInt(1, 3)));
            position.setInformF1RegId(RandomStringUtils.randomNumeric(15));
            position.setInformF2RegId(RandomStringUtils.randomNumeric(15));
            position.setProduct(shopPos.getProduct());
            products.getStockPosition().add(position);
        }

        rests.setProducts(products);
        body.setReplyRestsV2(rests);
        document.setDocument(body);

        return document;
    }

    private OrgInfoReplyV2 generateProducer() {
        OrgInfoReplyV2 producer = factory.createOrgInfoReplyV2();
        switch (RandomUtils.nextInt(0, 4)) {
            case 0:
                ULReplyType ul = factory.createULReplyType();
                ul.setClientRegId(RandomStringUtils.randomNumeric(12));
                ul.setFullName("ООО \"" + RandomStringUtils.randomAlphabetic(20) + "\"");
                ul.setShortName(ul.getFullName());
                ul.setINN(RandomStringUtils.randomNumeric(10));
                ul.setKPP(RandomStringUtils.randomNumeric(9));
                OrgAddressTypeULFLReply type = factory.createOrgAddressTypeULFLReply();
                type.setCountry(RandomStringUtils.randomNumeric(3));
                type.setRegionCode(RandomStringUtils.randomNumeric(2));
                type.setDescription(RandomStringUtils.randomAlphanumeric(100));
                ul.setAddress(type);
                producer.setUL(ul);
                break;
            case 1:
                FLReplyType fl = factory.createFLReplyType();
                fl.setClientRegId(RandomStringUtils.randomNumeric(12));
                fl.setFullName("ЧП \"" + RandomStringUtils.randomAlphabetic(20) + "\"");
                fl.setShortName(fl.getFullName());
                fl.setINN(RandomStringUtils.randomNumeric(12));
                OrgAddressTypeULFLReply type1 = factory.createOrgAddressTypeULFLReply();
                type1.setCountry(RandomStringUtils.randomNumeric(3));
                type1.setRegionCode(RandomStringUtils.randomNumeric(2));
                type1.setDescription(RandomStringUtils.randomAlphanumeric(100));
                fl.setAddress(type1);
                producer.setFL(fl);
                break;
            case 2:
                FOType fo = factory.createFOType();
                fo.setClientRegId(RandomStringUtils.randomNumeric(12));
                fo.setFullName(RandomStringUtils.randomAlphabetic(20) + " GMBh.");
                fo.setShortName(fo.getFullName());
                OrgAddressTypeFOTS type2 = factory.createOrgAddressTypeFOTS();
                type2.setCountry(RandomStringUtils.randomNumeric(3));
                type2.setDescription(RandomStringUtils.randomAlphanumeric(100));
                fo.setAddress(type2);
                producer.setFO(fo);
                break;
            case 3:
                TSReplyType ts = factory.createTSReplyType();
                ts.setClientRegId(RandomStringUtils.randomNumeric(12));
                ts.setFullName("Фирма \"" + RandomStringUtils.randomAlphabetic(20) + "\"");
                ts.setShortName(ts.getFullName());
                OrgAddressTypeFOTS type3 = factory.createOrgAddressTypeFOTS();
                type3.setCountry(RandomStringUtils.randomNumeric(3));
                type3.setDescription(RandomStringUtils.randomAlphanumeric(100));
                ts.setAddress(type3);
                producer.setTS(ts);
                break;
        }
        return producer;
    }

    private void marshall(Documents document, String filename) throws JAXBException {
        File file = new File("./" + filename);
        marshaller.marshal(document, file);
    }

    public static void main(String[] args) throws JAXBException {
        new RestsGenerator();
    }
}
