package ru.dreamkas.datamax;

import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaName;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFOutputStream;
import org.freehep.graphicsio.emf.EMFTag;
import org.freehep.graphicsio.emf.EMFTagSet;

import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.controls.swing.printer.DocumentPrinter;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import ru.dreamkas.FileChooser;
import ru.dreamkas.printable.PrintableDocument;


public class Datamax {
    private final PrintService printer;
    private final Path fileToPrint;

    public Datamax(Map<String, String> parameters) {
        fileToPrint = openFile(parameters.get("file"));
        String printerName = parameters.getOrDefault("printer", "Gainscha GS-2406T");
        PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, null);
        printer = Arrays.stream(pss).filter(ps -> ps.getName().equals(printerName)).findFirst().orElse(null);
    }

    public void printOnSystemPrinter() throws Exception {
        if (printer == null) {
            throw new Exception("Принтер не выбран");
        }
        if (fileToPrint == null) {
            throw new Exception("Файл не выбран");
        }
        DocPrintJob printJob = printer.createPrintJob();
        Doc doc = new SimpleDoc(new FileInputStream(fileToPrint.toFile()), DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        try {
            PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();
            attrib.add(new Copies(1));
            attrib.add(new JobName(fileToPrint.getFileName().toString(), Locale.getDefault()));
            printJob.print(doc, new HashPrintRequestAttributeSet());
        } catch (PrintException e) {
            throw new RuntimeException(e);
        }

//        DocumentPrinter prn = new DocumentPrinter();
//        prn.loadDocument(fileToPrint.toAbsolutePath().toString(), "");
//        prn.print(printer.getName(), new PageRangeSettings(), 1);
    }

    public void printDoc() {
        try {
            PrintableDocument doc = new PrintableDocument();
            doc.doPrint(printer);

        } catch (PrintException e) {
            e.printStackTrace();
        }
    }

    public void printDPL() throws Exception {
        if (printer == null) {
            throw new Exception("Принтер не выбран");
        }
        if (fileToPrint == null) {
            throw new Exception("Файл не выбран");
        }
        byte[] labelContent = FileUtils.readFileToByteArray(fileToPrint.toFile());
        System.out.printf("Print file %s (size is %d bytes)%n", fileToPrint.toAbsolutePath(), labelContent.length);

//        byte[] pdf = convertToPDF(labelContent);
//        String s = FilenameUtils.getBaseName(fileToPrint.getFileName().toString()) + ".pdf";
//        File pdfFile = fileToPrint.getParent().resolve(s).toFile();
//        FileUtils.writeByteArrayToFile(pdfFile, pdf);

        DocPrintJob printJob = printer.createPrintJob();
        Doc doc = new SimpleDoc(new PrintableDocument(), DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
        try {
            PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();
            attrib.add(new Copies(1));
            attrib.add(new JobName(fileToPrint.getFileName().toString(), Locale.getDefault()));
            printJob.print(doc, new HashPrintRequestAttributeSet());
        } catch (PrintException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] convertToPDF(byte[] docx) {
        try {
            InputStream doc = new ByteArrayInputStream(docx);
            XWPFDocument document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(document, out, options);
            System.out.println("Done");
            return out.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new byte[0];
    }

    private Path openFile(String fileName) {
        Path path = fileName != null ? Paths.get(fileName) : Paths.get(".");
        if (Files.isDirectory(path)) {
            FileChooser chooser = FileChooser.getOpenFileChooser(path, "Выберите файл для печати", null);
            FileNameExtensionFilter msWord = new FileNameExtensionFilter("MS Word файлы (*.doc, *.docx)", "doc", "docx");
            FileNameExtensionFilter pdf = new FileNameExtensionFilter("PDF файлы (*.pdf)", "pdf");
            FileNameExtensionFilter dpl = new FileNameExtensionFilter("DPL файлы (*.lbl, *.dpl, *.txt)", "lbl", "dpl", "txt");
            chooser.addChoosableFileFilter(msWord);
            chooser.addChoosableFileFilter(pdf);
            chooser.addChoosableFileFilter(dpl);
            chooser.setFileFilter(msWord);
            if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                return null;
            }
            path = Paths.get(chooser.getSelectedFile().toURI());
        }
        return Files.exists(path) ? path : null;
    }
}
