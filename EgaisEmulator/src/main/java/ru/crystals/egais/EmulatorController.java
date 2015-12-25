package ru.crystals.egais;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EmulatorController {
    private Logger log = LoggerFactory.getLogger(EmulatorController.class);

    @RequestMapping("/opt/out")
    public String getDocumentsList(@RequestParam(value = "refresh", defaultValue = "false") boolean refresh, HttpServletRequest request) throws Exception {
        log.info("[{}:{}] invoke getDocumentsList refresh={}", request.getRemoteHost(), request.getRemotePort(), refresh);
        return DocumentController.getDocumentsList(refresh, request.getHeader("host"));
    }

    @RequestMapping(value = "/opt/out/{docType}/{id}")
    public String getDocument(@PathVariable String docType, @PathVariable long id, HttpServletRequest request) {
        log.info("[{}:{}] invoke getDocument docType={}; id={}", request.getRemoteHost(), request.getRemotePort(), docType, id);
        return DocumentController.getDocument(docType, id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/opt/out/{docType}/{id}")
    public String deleteDocument(@PathVariable String docType, @PathVariable long id, HttpServletRequest request) {
        log.info("[{}:{}] invoke deleteDocument docType={}; id={}", request.getRemoteHost(), request.getRemotePort(), docType, id);
        return DocumentController.deleteDocument(docType, id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opt/in/WayBillAct")
    public String postWayBillAct(@RequestParam(value = "xml_file", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        log.info("[{}:{}] invoke postWayBillAct xml_file={}", request.getRemoteHost(), request.getRemotePort(), Commons.openFile(file));
        return DocumentController.postWayBillAct(file);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/xml")
    public String postPurchase(@RequestParam(value = "xml_file", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        log.info("[{}:{}] invoke postPurchase xml_file={}", request.getRemoteHost(), request.getRemotePort(), Commons.openFile(file));
        return PurchaseController.postPurchase(file, request.getLocalPort());
    }

    @RequestMapping(value = { "*", "*/*" }, produces = MediaType.ALL_VALUE)
    public void getWeb(@RequestParam(value = "id", defaultValue = "") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("[{}:{}] invoke getWeb {}", request.getRemoteHost(), request.getRemotePort(), request.getRequestURL());
        if (!StringUtils.isBlank(id)) {
            log.info("load purchase " + id);
            ServletOutputStream stream = response.getOutputStream();
            stream.write(PurchaseController.getPurchase(id));
        } else {
            ServletOutputStream stream = response.getOutputStream();
            String path = StringUtils.substringAfter(request.getRequestURL().toString(), request.getHeader("host") + "/");
            stream.write(Commons.getBytes(path));
        }
    }

}
