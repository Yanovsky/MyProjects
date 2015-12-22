package ru.crystals.egais;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ru.crystals.egais.cheque.Cheque;
import ru.crystals.egais.exceptions.EgaisException;

public class PurchaseController {
    private static SimpleDateFormat fd = new SimpleDateFormat("ddMMyyHHmm");

    public static String postPurchase(MultipartFile file) throws Exception {
        String xml = Commons.openFile(file);
        Cheque purchase = (Cheque) Marchallers.getUnmarshaller(Cheque.class).unmarshal(new StringReader(xml));

        try {
            if (StringUtils.isBlank(purchase.getInn())) {
                throw new EgaisException("Не заполнен ИНН");
            }
            if (StringUtils.isBlank(purchase.getKpp())) {
                throw new EgaisException("Не заполнен КПП");
            }
            if (StringUtils.isBlank(purchase.getKassa())) {
                throw new EgaisException("Не заполнен заводской номер кассы");
            }
            if (StringUtils.isBlank(purchase.getAddress())) {
                throw new EgaisException("Не заполнен адрес");
            }
            if (StringUtils.isBlank(purchase.getName())) {
                throw new EgaisException("Не заполнено наименование организации");
            }
            if (purchase.getBottle().isEmpty() && purchase.getNopdf().isEmpty()) {
                throw new EgaisException("Отсутствуют позиции чека");
            }
            if (!purchase.getBottle().isEmpty() && !purchase.getNopdf().isEmpty()) {
                throw new EgaisException("Недопустимый состав чека. В чеке не могут одновременно находиться и акцизные и неакцизные товары");
            }

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
