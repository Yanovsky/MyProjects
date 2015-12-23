package ru.crystals.egais;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        return PurchaseController.postPurchase(file);
    }

    @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public void getMain(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException {
        log.info("[{}:{}] invoke getMain", request.getRemoteHost(), request.getRemotePort());
        PrintWriter writer = response.getWriter();
        writer.println(Commons.getMainHTML(request.getHeader("host")));
    }

}
