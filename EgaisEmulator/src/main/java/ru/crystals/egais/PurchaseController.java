package ru.crystals.egais;

import java.io.File;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.multipart.MultipartFile;

import ru.crystals.egais.cheque.Cheque;
import ru.crystals.egais.exceptions.EgaisException;

public class PurchaseController {
    private static SimpleDateFormat fd = new SimpleDateFormat("ddMMyyHHmm");
    private static final String purchasesFolder = "purchases/";

    public static String postPurchase(MultipartFile file, int port) throws Exception {
        String xml = Commons.openFile(file);
        Cheque purchase = (Cheque) Marchallers.getUnmarshaller(Cheque.class).unmarshal(new StringReader(xml));

        try {
            Validator.validatePurchase(purchase);
            String purchaseSign = Commons.generateShortSign();
            // check.egais.ru
            String url = String.format("http://%s?id=%s&dt=%s&cn=%s",
                    Commons.getCurrentIp() + ":" + port,
                    purchaseSign,
                    fd.format(Calendar.getInstance().getTime()),
                    RandomStringUtils.randomNumeric(12));
            FileUtils.writeStringToFile(new File(purchasesFolder + purchaseSign + ".xml"), xml, StandardCharsets.UTF_8.name());
            return AnswerController.makeAnswer(Commons.generateLongSign(), url);
        } catch (EgaisException e) {
            return AnswerController.makeAnswer(e);
        }

    }

    public static byte[] getPurchase(String id) throws Exception {
        String xml = Commons.readTextFromFile("./purchases/" + id + ".xml");
        Cheque purchase = (Cheque) Marchallers.getUnmarshaller(Cheque.class).unmarshal(new StringReader(xml));

        SimpleDateFormat f = new SimpleDateFormat("ddMMyyHHmm");
        SimpleDateFormat f1 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = f.parse(purchase.getDatetime());

        String html = Commons.readTextFromFile(Commons.WEB_PATH + "purchase.html");
        Document doc = Jsoup.parse(html);
        doc.charset(StandardCharsets.UTF_8);

        Element divContainer = doc.select("div.container").first();
        Elements infoConatiners = divContainer.select("div.infocontainer");
        {
            Element divInfo = infoConatiners.get(0);
            divInfo.append("<li>" + "Наименование организации: " + purchase.getName() + "</li>");
            divInfo.append("<li>" + "ИНН: " + purchase.getInn() + "</li>");
            divInfo.append("<li>" + "КПП: " + purchase.getKpp() + "</li>");
            divInfo.append("<li>" + "Адрес организации: " + purchase.getAddress() + "</li>");
        }
        {
            Element divInfo = infoConatiners.get(1);
            divInfo.append("<li>" + "Касса № " + purchase.getKassa() + "</li>");
            divInfo.append("<li>" + "Смена № " + purchase.getShift() + "</li>");
            divInfo.append("<li>" + "Чек № " + purchase.getNumber() + " от " + f1.format(date) + "</li>");
        }
        purchase.getBottle().stream().forEach(item -> {
            Element divInfo = infoConatiners.get(2);
            divInfo.append("<li>" + item.getBarcode() + "</li>");
        });
        purchase.getNopdf().stream().forEach(item -> {
            Element divInfo = infoConatiners.get(2);
            divInfo.append("<li>" + item.getBname() + "</li>");
            divInfo.append("<p>&ensp;Штрих-код: " + item.getEan() + "</p>");
            divInfo.append("<p>&ensp;Объём: " + item.getVolume() + " л" + "</p>");
            divInfo.append("<p>&ensp;Содержание алкоголя: " + item.getAlc() + " %" + "</p>");
            divInfo.append("<p>&ensp;Цена: " + item.getPrice() + " руб." + "</p>");
            divInfo.append("<p>&ensp;Количество: " + item.getCount().toString() + " шт." + "</p>");
        });

        return doc.toString().getBytes(StandardCharsets.UTF_8.name());
    }

    
    public static void main(String[] args) throws Exception {
        String xml = FileUtils.readFileToString(new File("./purchases/" + "331ec0cf-596b-29e2-9216-7b8dcae32a4d" + ".xml"));
        Cheque purchase = (Cheque) Marchallers.getUnmarshaller(Cheque.class).unmarshal(new StringReader(xml));

        String html = FileUtils.readFileToString(new File(Commons.WEB_PATH + "purchase.html"));
        Document doc = Jsoup.parse(html);

        Element divContainer = doc.select("div.container").first();
        Elements infoConatiners = divContainer.select("div.infocontainer");
        {
            Element divInfo = infoConatiners.get(0);
            divInfo.append("<ul>");
            divInfo.append("<li>" + purchase.getName() + "</li>");
            divInfo.append("<li>" + purchase.getAddress() + "</li>");
            divInfo.append("</ul>");
        }
        purchase.getBottle().stream().forEach(item -> {
            Element divInfo = infoConatiners.get(2);
            divInfo.append("<ul>");
            divInfo.append("<li>" + item.getBarcode() + "</li>");
            divInfo.append("</ul>");
            divInfo.append("<br>");
        });

        System.out.println(doc.toString());
    }
}
