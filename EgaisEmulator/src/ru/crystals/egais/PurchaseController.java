package ru.crystals.egais;

import org.springframework.web.multipart.MultipartFile;

public class PurchaseController {

    public static String postPurchase(MultipartFile file) throws Exception {
        return AnswerController.makeAnswer(Commons.generateLongSign(), Commons.generateShortSign());
    }
    
}
