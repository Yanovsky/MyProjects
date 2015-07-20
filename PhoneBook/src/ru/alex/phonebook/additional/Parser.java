package ru.alex.phonebook.additional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Telephone;

public class Parser {

    public static void inspect(String folder, String ext) throws IOException {
        Finder finder = new Finder();
        List<File> files = finder.search(folder, ext);
        List<VCardData> cards = new ArrayList<VCardData>();

        for (File file : files) {
            List<VCard> phonebook = Ezvcard.parse(file).all();

            for (VCard vCard : phonebook) {
                if (StringUtils.containsIgnoreCase(vCard.getFormattedName().getValue(), "андрей") ||
                    StringUtils.containsIgnoreCase(vCard.getFormattedName().getValue(), "андрюха") || StringUtils.containsIgnoreCase(vCard.getFormattedName().getValue(), "амлет")) {
                    VCardData cd = new VCardData(vCard);
                    if (!cards.contains(cd)) {
                        cards.add(cd);
                    }
                }
            }
        }

        for (VCardData vCard : cards) {
            System.out.println(vCard.getCardData().getFormattedName().getValue());
            for (Telephone number : vCard.getCardData().getTelephoneNumbers()) {
                System.out.println("\t" + number.getTypes() + " : " + number.getText());
            }
        }
    }

    public static void compare(File file1, File file2) throws IOException {
        List<VCard> phonebook1 = Ezvcard.parse(file1).all();
        List<VCard> phonebook2 = Ezvcard.parse(file2).all();

        System.out.println("Записей в " + file1.getName() + " " + phonebook1.size() + " размер: " + file1.length());
        System.out.println("Записей в " + file2.getName() + " " + phonebook2.size() + " размер: " + file2.length());
     
        List<VCardData> cards1 = new ArrayList<VCardData>();
        for (VCard vCard : phonebook1) {
            cards1.add(new VCardData(vCard));
        }

        List<VCardData> cards2 = new ArrayList<VCardData>();
        for (VCard vCard : phonebook2) {
            cards2.add(new VCardData(vCard));
        }
        
        System.out.println("В файле " + file2.getName() + " отстутсвуют записи:");
        for (VCardData vCard : cards1) {
            if (!cards2.contains(vCard)) {
                System.out.println("\t" + vCard.getCardData().getFormattedName().getValue());
                System.out.println("\tв " + file1.getName() + " " + vCard.getCardData().getPhotos().get(0).getData().length);
                for (Telephone number : vCard.getCardData().getTelephoneNumbers()) {
                    System.out.println("\t\t" + number.getTypes() + " : " + number.getText());
                }
            }
        }
        
        System.out.println("В файле " + file1.getName() + " отстутсвуют записи:");
        for (VCardData vCard : cards2) {
            if (!cards1.contains(vCard)) {
                System.out.println("\t" + vCard.getCardData().getFormattedName().getValue());
                System.out.println("\tв " + file2.getName() + " " + vCard.getCardData().getPhotos().get(0).getData().length);
                for (Telephone number : vCard.getCardData().getTelephoneNumbers()) {
                    System.out.println("\t\t" + number.getTypes() + " : " + number.getText());
                }
            }
        }
        
    }

    public static void main(String[] args) throws IOException {
        //inspect("D:/", "vcf");
        compare(new File("D:/YandexDisk/phonebook_20150225.vcf"), new File("D:/YandexDisk/phonebook_20150713.vcf"));
    }

}
