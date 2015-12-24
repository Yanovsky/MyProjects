package ru.crystals.egais;

import java.io.File;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
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

    public static byte[] getPurchase(String id) {
        return Commons.readBytesFromFile("./purchases/" + id + ".xml");
    }
    
}
