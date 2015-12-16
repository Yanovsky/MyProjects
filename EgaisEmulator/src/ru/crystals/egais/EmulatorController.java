package ru.crystals.egais;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EmulatorController {

    @RequestMapping("/opt/out")
    public String getDocumentsList(@RequestParam(value = "refresh", defaultValue = "false") String refresh) throws Exception {
        return DocumentController.getDocumentsListAnswer();
    }

    @RequestMapping(value = "/opt/out/{docType}/{id}")
    public String getDocument(@PathVariable String docType, @PathVariable long id) {
        System.out.println("Get a " + docType + " with id=" + id);
        return DocumentController.getDocument(docType, id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/opt/out/{docType}/{id}")
    public String deleteDocument(@PathVariable String docType, @PathVariable long id) {
        System.out.println("Delete a " + docType + " with id=" + id);
        return DocumentController.deleteDocument(docType, id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opt/in/WayBillAct")
    public String postWayBillAct(@RequestParam(value = "xml_file", required = false) MultipartFile file) throws Exception {
        System.out.println("postWayBillAct");
        return DocumentController.postWayBillAct(file);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/xml")
    public String postPurchase(@RequestParam(value = "xml_file", required = false) MultipartFile file) throws Exception {
        System.out.println("postPurchase");
        return PurchaseController.postPurchase(file);
    }


}
