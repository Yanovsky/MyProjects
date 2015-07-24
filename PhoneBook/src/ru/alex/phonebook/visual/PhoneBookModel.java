package ru.alex.phonebook.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

import net.coobird.thumbnailator.Thumbnailator;

import org.apache.commons.lang.StringUtils;

import ru.alex.phonebook.classes.PhoneType;
import ru.alex.phonebook.tools.VCardUtils;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;

public class PhoneBookModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final String[] columnNames = new String[] {"Абонент", "Телефоны", "Фото"};
    private List<VCard> originalPhonebook = new ArrayList<VCard>();
    private List<VCard> phonebook = new ArrayList<VCard>();
    private File phoneBookFile;
    private String filter = "";
    private Timer filterTimer;

    private Predicate<? super VCard> filterPredicate = new Predicate<VCard>() {
        @Override
        public boolean test(VCard card) {
            return StringUtils.isEmpty(filter) || StringUtils.containsIgnoreCase(getAbonentName(card), filter) || StringUtils.containsIgnoreCase(getPhoneNumbers(card), filter);
        }
    };

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
    private ActionListener searchTask = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            phonebook = originalPhonebook.stream().filter(filterPredicate).collect(Collectors.toList());
            fireTableDataChanged();
        }
    };

    public PhoneBookModel() {
        filterTimer = new Timer(1000, searchTask);
        filterTimer.setRepeats(false);
    }

    public void setPhoneBookFile(File phoneBookFile) throws IOException {
        if (phoneBookFile != null && phoneBookFile.exists()) {
            originalPhonebook = Ezvcard.parse(phoneBookFile).all().stream().sorted(nameSorter).filter(filterPredicate).collect(Collectors.toList());
            Collections.sort(originalPhonebook, nameSorter);
            setFilter(Optional.ofNullable(filter), false);
        }
        this.phoneBookFile = phoneBookFile;
    }

    public File getPhoneBookFile() {
        return phoneBookFile;
    };

    public List<VCard> getPhoneBook() {
        return originalPhonebook;
    }

    public VCard getCardAt(int rowIndex) {
        return phonebook.get(rowIndex);
    }

    public int addCard(VCard card) {
        originalPhonebook.add(card);
        Collections.sort(originalPhonebook, nameSorter);
        setFilter(Optional.ofNullable(filter), false);
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
                        return getAbonentName(card);
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

    private String getAbonentName(VCard card) {
        StructuredName sName = card.getStructuredName();
        return (StringUtils.defaultString(sName.getFamily(), "") + " " + StringUtils.defaultString(sName.getGiven(), "")).trim();
    }

    private String getPhoneNumbers(VCard card) {
        boolean moreThenOne = card.getTelephoneNumbers().size() > 1;
        return new StringBuilder("<HTML>").append(card.getTelephoneNumbers()
            .stream()
            .sorted(VCardUtils.numberSorter)
            .map((number) -> this.mapToHTMLNumber(number, moreThenOne))
            .collect(Collectors.joining("<br>")))
        .append("</HTML>").toString();
    }
    
    private String mapToHTMLNumber(Telephone number, boolean moreThenOne) {
        StringBuilder sb = new StringBuilder();
        boolean main = number.getTypes().contains(TelephoneType.PREF) && moreThenOne;
        String typeS = number.getTypes().stream().filter(t -> t != TelephoneType.PREF).map(this::translateTelephoneType).findFirst().orElseGet(() -> "");
        if (main) {
            sb.append("<b>").append(typeS).append(": ").append(number.getText()).append("</b>");
        } else {
            sb.append(typeS).append(": ").append(number.getText());
        }
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
        Optional.ofNullable(card.getPhotos()).ifPresent(photos -> {
            photos.stream().findFirst().ifPresent(photo -> {
                Optional.ofNullable(photo.getData()).ifPresent(bytes -> {
                    try {
                        InputStream input = new ByteArrayInputStream(bytes);
                        BufferedImage image = ImageIO.read(input);
                        Optional.ofNullable(image).ifPresent(img -> {
                            icon.setImage(Thumbnailator.createThumbnail(img, 100, 100));
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        });
        return icon;
    }

    public void removeCard(int cardIndex) {
        phonebook.remove(cardIndex);
        fireTableRowsDeleted(cardIndex, cardIndex);
    }

    public void setFilter(Optional<String> filter, boolean delayed) {
        filter.ifPresent(f -> {
            this.filter = f;
            if (delayed) {
                filterTimer.start();
            } else {
                searchTask.actionPerformed(null);
            }
        });
    }

}
