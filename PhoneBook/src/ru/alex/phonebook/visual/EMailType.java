package ru.alex.phonebook.visual;

import org.apache.commons.lang.StringUtils;

import ezvcard.parameter.EmailType;

public enum EMailType {

    INTERNET("Служебный"),
    X400(null),
    PREF(null),
    AOL(null),
    APPLELINK(null),
    ATTMAIL(null),
    CIS(null),
    EWORLD(null),
    IBMMAIL(null),
    MCIMAIL(null),
    POWERSHARE(null),
    PRODIGY(null),
    TLX(null),
    HOME("Домашний"),
    WORK("Рабочий");

    private String value;

    private EMailType(String value) {
        this.value = value == null ? name() : value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public static Object valueOfType(EmailType type) {
        for (EMailType t : values()) {
            if (StringUtils.equalsIgnoreCase(t.name(), type.getValue())) {
                return t;
            }
        }
        return HOME;
    }
}
