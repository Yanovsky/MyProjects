package ru.crystals.egais;

import org.springframework.web.multipart.MultipartFile;

public class PurchaseController {

    public static String postPurchase(MultipartFile file) throws Exception {
        System.out.println(Commons.openFile(file));
        return AnswerController.makeAnswer(Commons.generateLongSign(), Commons.generateShortSign());
    }
    
}
