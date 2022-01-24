package ru.dreamkas.printable;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PageRanges;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

public class PrintableDocument implements Printable {
    private final DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
    private HashPrintRequestAttributeSet printerSet;
    private final float printerWidthMM = 57;
    private final float printerHeightMM = 59;

    public PrintableDocument() {
    }

    public void doPrint(PrintService printService) throws PrintException {
        DocPrintJob job = printService.createPrintJob();
        job.addPrintJobListener(new PrintJobAdapter() {
            @Override
            public void printJobFailed(PrintJobEvent pje) {
                System.err.printf("printJobFailed: %s%n", pje);
            }

            @Override
            public void printJobCompleted(PrintJobEvent pje) {
                System.out.println("printJobCompleted");
            }

            @Override
            public void printDataTransferCompleted(PrintJobEvent pje) {
                System.out.println("printDataTransferCompleted");
            }

            @Override
            public void printJobNoMoreEvents(PrintJobEvent pje) {
                System.out.println("printJobNoMoreEvents");
            }
        });
        Doc doc = new SimpleDoc(this, flavor, null);
        job.print(doc, getPrinterSet());
    }

    private PrintRequestAttributeSet getPrinterSet() {
        if (printerSet == null) {
            //создаем обьект настроек принтера
            printerSet = new HashPrintRequestAttributeSet();
            printerSet.add(OrientationRequested.PORTRAIT);
            //количество копий
            printerSet.add(new Copies(1));
            //количество страниц
            printerSet.add(new PageRanges(1));
            //область печати
            printerSet.add(new MediaPrintableArea(0.1f, 0.1f, printerWidthMM*3, printerHeightMM*3, MediaPrintableArea.MM));
            //printerSet.add(new PrinterResolution(150, 150, PrinterResolution.DPI));
            printerSet.add(new JobName("Document", Locale.getDefault()));
        }
        return printerSet;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
        Graphics2D graphics2D = (Graphics2D) graphics;

        int x = (int) pageFormat.getImageableX();
        int y = (int)pageFormat.getImageableY() + 135;
        graphics2D.setFont(new Font("Roboto", Font.BOLD, 15));
        String str = "Морковь Россия проверка длины строки";
        List<String> lines = MyStringUtils.wordWrap(str, graphics2D.getFontMetrics(), 146);
        for (String line: lines) {
            graphics2D.drawString(line, x, y);
            y += (int) (graphics2D.getFont().getSize() * 1.2);
        }
        graphics2D.setFont(new Font("Roboto", Font.PLAIN, 9));
        graphics2D.drawString("Цена: "+135.53+" руб.", x, y);
        y += (int) (graphics2D.getFont().getSize() * 1.2);;
        graphics2D.drawString("Цена: "+135.53+" руб.", x, y);
        y += (int) (graphics2D.getFont().getSize() * 1.2);;
        graphics2D.drawString("Цена: "+135.53+" руб.", x, y);
        y += (int) (graphics2D.getFont().getSize() * 1.2);;

        try {
            BufferedImage img = ImageIO.read(new File("D:\\-1\\barcode.png"));
            y = 240;
            graphics2D.drawImage(img, x, y, Math.round(img.getWidth()/2.5f), Math.round(img.getHeight()/2.5f), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return PAGE_EXISTS;
    }
}
