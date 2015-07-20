package ru.alex.phonebook.visual;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import net.coobird.thumbnailator.Thumbnailator;

import org.apache.commons.lang.StringUtils;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;

public class PhoneBookModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final String[] columnNames = new String[] {"Абонент", "Телефоны", "Фото"};
    private List<VCard> phonebook = new ArrayList<VCard>();
    private File phoneBookFile;
    private Comparator<? super VCard> nameSorter = new Comparator<VCard>() {
        @Override
        public int compare(VCard card1, VCard card2) {
            StructuredName sName1 = card1.getStructuredName();
            StructuredName sName2 = card2.getStructuredName();
            String s1 = (StringUtils.defaultString(sName1.getFamily(), "") + " " + StringUtils.defaultString(sName1.getGiven(), "")).trim();
            String s2 = (StringUtils.defaultString(sName2.getFamily(), "") + " " + StringUtils.defaultString(sName2.getGiven(), "")).trim();
            return s1.compareTo(s2);
        }
    };

    public void setPhoneBookFile(File phoneBookFile) throws IOException {
        if (phoneBookFile != null && phoneBookFile.exists()) {
            phonebook = Ezvcard.parse(phoneBookFile).all();
            Collections.sort(phonebook, nameSorter);
            fireTableDataChanged();
        }
        this.phoneBookFile = phoneBookFile;
    }

    public File getPhoneBookFile() {
        return phoneBookFile;
    };

    public List<VCard> getPhoneBook() {
        return phonebook;
    }

    public VCard getCardAt(int rowIndex) {
        return phonebook.get(rowIndex);
    }

    public void setCardAt(int rowIndex, VCard card) {
        phonebook.set(rowIndex, card);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public int getAddCard(VCard card) {
        phonebook.add(card);
        Collections.sort(phonebook, nameSorter);
        int result = phonebook.indexOf(card);
        fireTableDataChanged();
        return result;
    }

    @Override
    public int getRowCount() {
        return phonebook.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > -1 && rowIndex < phonebook.size()) {
            VCard card = phonebook.get(rowIndex);
            if (card != null) {
                switch (columnIndex) {
                    case 0:
                        StructuredName sName = card.getStructuredName();
                        return (StringUtils.defaultString(sName.getFamily(), "") + " " + StringUtils.defaultString(sName.getGiven(), "")).trim();
                    case 1:
                        return getPhoneNumbers(card);
                    case 2:
                        return getPhoto(card);
                    default:
                        return null;
                }
            }
        }
        return null;
    }

    private String getPhoneNumbers(VCard card) {
        StringBuilder sb = new StringBuilder("<HTML>");
        List<Telephone> numbers = VCardUtils.sortTelephones(card.getTelephoneNumbers());
        for (Telephone number : numbers) {
            boolean main = false;
            String typeS = "";
            for (TelephoneType type : number.getTypes()) {
                if (type == TelephoneType.PREF) {
                    main = true;
                } else {
                    typeS = translateTelephoneType(type);
                }
            }
            if (numbers.size() < 2) {
                main = false;
            }
            if (main) {
                sb.append("<b>");
            }
            sb.append(typeS + ": " + number.getText());
            if (main) {
                sb.append("</b>");
            }
            sb.append("<br>");
        }
        sb.append("</HTML>");
        return sb.toString();
    }

    private String translateTelephoneType(TelephoneType telephoneType) {
        String valueToFind = telephoneType.getValue();
        try {
            return PhoneType.valueOf(valueToFind.toUpperCase()).getValue();
        } catch (Exception e) {
            return valueToFind;
        }
    }

    private Icon getPhoto(VCard card) {
        ImageIcon icon = new ImageIcon();
        if (card != null && card.getPhotos() != null && card.getPhotos().size() > 0) {
            try {
                InputStream input = new ByteArrayInputStream(card.getPhotos().get(0).getData());
                BufferedImage image = ImageIO.read(input);
                if (image != null) {
                    icon.setImage(Thumbnailator.createThumbnail(image, 100, 100));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return icon;
        }
        return icon;
    }

    public void removeCard(int cardIndex) {
        phonebook.remove(cardIndex);
        fireTableRowsDeleted(cardIndex, cardIndex);
    }

}
