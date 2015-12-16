package ru.crystals.egais;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EmulatorController {
    private Logger log = Logger.getLogger(EmulatorController.class);

    @RequestMapping("/opt/out")
    public String getDocumentsList(@RequestParam(value = "refresh", defaultValue = "false") String refresh, HttpServletRequest request) throws Exception {
        log.info("[" + request.getRemoteHost() + ":" + request.getRemotePort() + "] invoke getDocumentsList " + request.getQueryString());
        return DocumentController.getDocumentsListAnswer(request.getHeader("host"));
    }

    @RequestMapping(value = "/opt/out/{docType}/{id}")
    public String getDocument(@PathVariable String docType, @PathVariable long id) {
        return DocumentController.getDocument(docType, id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/opt/out/{docType}/{id}")
    public String deleteDocument(@PathVariable String docType, @PathVariable long id, HttpServletRequest request) {
        log.info("[" + request.getRemoteHost() + ":" + request.getRemotePort() + "] invoke deleteDocument docType=" + docType + "; id=" + id);
        return DocumentController.deleteDocument(docType, id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opt/in/WayBillAct")
    public String postWayBillAct(@RequestParam(value = "xml_file", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        log.info("[" + request.getRemoteHost() + ":" + request.getRemotePort() + "] invoke postWayBillAct xm_file=" + Commons.openFile(file));
        return DocumentController.postWayBillAct(file);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/xml")
    public String postPurchase(@RequestParam(value = "xml_file", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        log.info("[" + request.getRemoteHost() + ":" + request.getRemotePort() + "] invoke postPurchase xm_file=" + Commons.openFile(file));
        return PurchaseController.postPurchase(file);
    }


}
