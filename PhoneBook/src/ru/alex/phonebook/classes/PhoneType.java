package ru.alex.phonebook.classes;

import org.apache.commons.lang.StringUtils;

import ezvcard.parameter.TelephoneType;



public enum PhoneType {
    BBS("BBS"),
    CAR("Авто"),
    CELL("Мобильный"),
    FAX("Факс"),
    HOME("Домашний"),
    ISDN("ISDN"),
    MODEM("Модем"),
    MSG("Сообщения"),
    PAGER("Пейджер"),
    PCS("PCS"),
    PREF("PREF"),
    TEXT("Текст"),
    TEXTPHONE("Домашний текст"),
    VIDEO("Видео"),
    VOICE("Голосовой"),
    WORK("Рабочий"),
    X_CUSTOM_COMPANY("Рабочий");

    private String value;

    private PhoneType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public static PhoneType valueOfType(TelephoneType type) {
        for (PhoneType t : values()) {
            if (StringUtils.equalsIgnoreCase(t.name(), type.getValue())) {
                return t;
            }
        }
        return CELL;
    }
}
