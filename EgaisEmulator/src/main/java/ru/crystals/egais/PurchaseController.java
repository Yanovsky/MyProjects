package ru.crystals.egais;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import ru.crystals.egais.cheque.Cheque;
import ru.crystals.egais.exceptions.EgaisException;

public class PurchaseController {
    private static SimpleDateFormat fd = new SimpleDateFormat("ddMMyyHHmm");

    public static String postPurchase(MultipartFile file) throws Exception {
        String xml = Commons.openFile(file);
        Cheque purchase = (Cheque) Marchallers.getUnmarshaller(Cheque.class).unmarshal(new StringReader(xml));

        try {
            Validator.validatePurchase(purchase);
            String url = "http://check.egais.ru?id=" 
                            + Commons.generateShortSign() 
                            + "&dt=" + fd.format(Calendar.getInstance().getTime()) 
                            + "&cn=" + RandomStringUtils.randomNumeric(12);
            return AnswerController.makeAnswer(Commons.generateLongSign(), url);
        } catch (EgaisException e) {
            return AnswerController.makeAnswer(e);
        }

    }
    
}
