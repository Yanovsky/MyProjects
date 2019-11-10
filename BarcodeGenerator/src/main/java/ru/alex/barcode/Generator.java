package ru.alex.barcode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class Generator {
    private static final String SEPARATOR = "^";
    private static final String HELP = "Use follows parameters:\n"+
        "\tinput -\t\tInput text file contains data. Default is D:\\barcodes.txt\n"+
        "\toutput -\tOutput file for store MS Word document. Default is D:\\barcodes.doc\n"+
        "\tencoding -\tEncoding of the input text file. Default is UTF-8\n"+
        "\tformat -\tFormat of the result barcodes. Default is PDF_417\n"+
        "\t\t\t\tYou can use one of:\n"+
        getFormatString()+"\n"+
        "For example:\n" +
        "\tbarcodeGenerator /?\n"+
        "\t\t Shows this help\n"+
        "\tbarcodeGenerator input=D:\\1\\barcodes.txt output=D:\\1\\result.doc encoding=cp1251 separator=~\n"+
        "";

    private static String getFormatString() {
        return Stream.of(BarcodeFormat.values()).map(s -> "\t\t\t\t\t"+s.name()).collect(Collectors.joining("\n"));
    }

    @SuppressWarnings("WeakerAccess")
    public static void generate(BarcodeFormat format, Map<String, List<String>> barcodes, Path outputFile) throws Exception {
        MultiFormatWriter barcodeProducer = new MultiFormatWriter();
        XWPFDocument document = new XWPFDocument();

        AtomicInteger i = new AtomicInteger(0);
        barcodes.forEach((titleText, list) -> {
            try {
                XWPFParagraph title = document.createParagraph();
                title.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun text = title.createRun();
                text.setBold(true);
                text.setFontSize(18);
                text.setText(titleText);
                System.out.println("Start generating excises for '" + titleText + "':");
                list.forEach(barcode -> {
                    try {
                        BitMatrix matrix = barcodeProducer.encode(barcode, format, 0, 0);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
                        XWPFParagraph paragraph = document.createParagraph();
                        paragraph.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun paragraphRun = paragraph.createRun();
                        paragraphRun.addPicture(new ByteArrayInputStream(stream.toByteArray()), XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU(matrix.getWidth()), Units.toEMU(matrix.getHeight()));
                        System.out.println("\tImage for barcode '" + barcode + "' has been added");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                if (i.incrementAndGet() < barcodes.size()) {
                    document.createParagraph().createRun().addBreak(BreakType.PAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        FileOutputStream out = new FileOutputStream(outputFile.toFile());
        document.write(out);
        out.close();
        document.close();
        System.out.println("Result file "+outputFile.toString()+" has complete");
    }

    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            props.putAll(Stream.of(args).collect(Collectors.toMap(s -> StringUtils.substringBefore(s, "="), v -> StringUtils.substringAfter(v, "="))));
            if (props.isEmpty()) {
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
                return;
            }
            String separator = props.getProperty("separator", SEPARATOR);
            if (props.containsKey("-h") || props.containsKey("--h") || props.containsKey("/?")) {
                System.out.println(HELP);
                return;
            }
            Path inputFile = Paths.get(props.getProperty("input", "D:\\barcodes.txt"));
            if (Files.notExists(inputFile)) {
                throw new Exception("File " + inputFile + " is absent");
            }
            Path outputFile = Paths.get(props.getProperty("output", "D:\\barcodes.doc"));
            Charset encoding = Charset.forName(props.getProperty("encoding", StandardCharsets.UTF_8.name()));
            BarcodeFormat format = BarcodeFormat.valueOf(props.getProperty("format", BarcodeFormat.PDF_417.name()));

            Map<String, List<String>> excises = FileUtils.readLines(inputFile.toFile(), encoding).stream()
                .collect(
                    Collectors.groupingBy(
                        s -> StringUtils.substringBefore(s, separator),
                        Collectors.mapping(n -> StringUtils.substringAfter(n, separator), Collectors.toList())
                    )
                );
            generate(format, excises, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
