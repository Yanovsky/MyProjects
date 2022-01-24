package ru.dreamkas.printable;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.print.DocFlavor;

public class PrintableDocument implements Printable {
    private final DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;

    public PrintableDocument() {
        documentData = new 
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        documentData.print(g2d, pageFormat, 0);
        return PAGE_EXISTS;
    }
}
