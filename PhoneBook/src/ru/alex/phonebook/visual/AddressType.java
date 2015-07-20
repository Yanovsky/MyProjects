package ru.alex.phonebook.visual;

import org.apache.commons.lang.StringUtils;

public enum AddressType {
    HOME("Домашний"),
    WORK("Рабочий"),
    DOM("dom"),
    INTL("intl"),
    POSTAL("Почтовый"),
    PARCEL("Доставка"),
    PREF("pref");

    private String value;

    private AddressType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public static AddressType valueOfType(ezvcard.parameter.AddressType type) {
        for (AddressType t : values()) {
            if (StringUtils.equalsIgnoreCase(t.name(), type.getValue())) {
                return t;
            }
        }
        return POSTAL;
    }
}
