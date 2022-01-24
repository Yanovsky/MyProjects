package ru.dreamkas.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.spire.pdf.PdfDocument;

public class PDFConverter {
    public static void main(String[] args) throws Exception {
        //load the sample PDF
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(Paths.get("D:","0","Печатная форма УПД №11056 от 11.05.2021.pdf").toString());

        //save every PDF to .png image
        BufferedImage image;
        for (int i = 0; i < pdf.getPages().getCount(); i++) {
            image = pdf.saveAsImage(i);
            File file = new File(String.format("ToImage-img-%d.png", i));
            ImageIO.write(image, "png", file);
        }
        pdf.close();
    }
}
