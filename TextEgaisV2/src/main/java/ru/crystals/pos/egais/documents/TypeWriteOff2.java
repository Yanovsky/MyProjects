//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.12 at 12:12:18 PM MSK 
//


package ru.crystals.pos.egais.documents;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TypeWriteOff.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TypeWriteOff">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Пересортица"/>
 *     &lt;enumeration value="Недостача"/>
 *     &lt;enumeration value="Уценка"/>
 *     &lt;enumeration value="Порча"/>
 *     &lt;enumeration value="Потери"/>
 *     &lt;enumeration value="Проверки"/>
 *     &lt;enumeration value="Арест"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TypeWriteOff", namespace = "http://fsrar.ru/WEGAIS/ActWriteOff")
@XmlEnum
public enum TypeWriteOff2 {


    /**
     * Недостача пересортица
     * 
     */
    @XmlEnumValue("\u041f\u0435\u0440\u0435\u0441\u043e\u0440\u0442\u0438\u0446\u0430")
    ПЕРЕСОРТИЦА("\u041f\u0435\u0440\u0435\u0441\u043e\u0440\u0442\u0438\u0446\u0430"),

    /**
     * Недостача (хищение, злоупотребление материально ответственных лиц, ошибки учета, естественная убыль,...)
     * 
     */
    @XmlEnumValue("\u041d\u0435\u0434\u043e\u0441\u0442\u0430\u0447\u0430")
    НЕДОСТАЧА("\u041d\u0435\u0434\u043e\u0441\u0442\u0430\u0447\u0430"),

    /**
     * Уценка (списание) в результате порчи
     * 
     */
    @XmlEnumValue("\u0423\u0446\u0435\u043d\u043a\u0430")
    УЦЕНКА("\u0423\u0446\u0435\u043d\u043a\u0430"),

    /**
     * Списание объема продукции, не подлежащей дальнейшей реализации (бой, срок годности, порча)
     * 
     */
    @XmlEnumValue("\u041f\u043e\u0440\u0447\u0430")
    ПОРЧА("\u041f\u043e\u0440\u0447\u0430"),

    /**
     * Потери при транспортировке
     * 
     */
    @XmlEnumValue("\u041f\u043e\u0442\u0435\u0440\u0438")
    ПОТЕРИ("\u041f\u043e\u0442\u0435\u0440\u0438"),

    /**
     * Списание объема продукции, израсходованной на лабораторные нужды для прохождения добровольной проверки качества, рекламные образцы
     * 
     */
    @XmlEnumValue("\u041f\u0440\u043e\u0432\u0435\u0440\u043a\u0438")
    ПРОВЕРКИ("\u041f\u0440\u043e\u0432\u0435\u0440\u043a\u0438"),

    /**
     * Списание арестованной продукции, изъятой из оборота продукции, конфискованной продукции по решению суда
     * 
     */
    @XmlEnumValue("\u0410\u0440\u0435\u0441\u0442")
    АРЕСТ("\u0410\u0440\u0435\u0441\u0442");
    private final String value;

    TypeWriteOff2(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeWriteOff2 fromValue(String v) {
        for (TypeWriteOff2 c: TypeWriteOff2 .values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
