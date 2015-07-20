package ru.alex.phonebook.additional;

import org.apache.commons.lang.StringUtils;

import ezvcard.VCard;
import ezvcard.property.FormattedName;
import ezvcard.property.Telephone;

public class VCardData {

    VCard cardData;
    
    public VCardData(VCard cardData) {
        this.cardData = cardData;
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof VCardData))
            return false;
        if (cardData == null)
            return false;
        VCardData otherCardData = (VCardData) other;
        if (otherCardData.getCardData() == null)
            return false;
        
        if (cardData.getFormattedName() == null)
            return false;
        if (otherCardData.getCardData().getFormattedName() == null)
            return false;

        for (FormattedName name: cardData.getFormattedNames()) {
            for (FormattedName otherName: cardData.getFormattedNames()) {
                if (StringUtils.equalsIgnoreCase(name.getValue(), otherName.getValue())) {
                    if (cardData.getPhotos().size() != otherCardData.getCardData().getPhotos().size()) {
                        return false;
                    }
                    if (cardData.getPhotos().size() == otherCardData.getCardData().getPhotos().size() && cardData.getPhotos().size() > 0) {
                        if (!cardData.getPhotos().get(0).getData().equals(otherCardData.getCardData().getPhotos().get(0).getData()))
                            return false;
                    }
                    return true;
                }
            }
        }
        
        for (Telephone number: cardData.getTelephoneNumbers()) {
            for (Telephone otherNumber: otherCardData.getCardData().getTelephoneNumbers()) {
                if (StringUtils.equalsIgnoreCase(number.getText(), otherNumber.getText())) {
                    return true;
                }
            }
        }
        
        return false;
    }

    public VCard getCardData() {
        return cardData;
    }
}
