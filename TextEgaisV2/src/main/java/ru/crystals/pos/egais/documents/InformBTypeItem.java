//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.12 at 12:12:18 PM MSK 
//


package ru.crystals.pos.egais.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Запись в справке Б к ТТН, Информация о предыдущих отгрузках
 * 
 * <p>Java class for InformBTypeItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InformBTypeItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BRegId" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
 *         &lt;element name="MarkInfo" type="{http://fsrar.ru/WEGAIS/ProductRef}MarkInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformBTypeItem", namespace = "http://fsrar.ru/WEGAIS/ProductRef", propOrder = {
    "bRegId",
    "markInfo"
})
public class InformBTypeItem {

    @XmlElement(name = "BRegId", namespace = "http://fsrar.ru/WEGAIS/ProductRef", required = true)
    protected String bRegId;
    @XmlElement(name = "MarkInfo", namespace = "http://fsrar.ru/WEGAIS/ProductRef")
    protected MarkInfoType markInfo;

    /**
     * Gets the value of the bRegId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBRegId() {
        return bRegId;
    }

    /**
     * Sets the value of the bRegId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBRegId(String value) {
        this.bRegId = value;
    }

    /**
     * Gets the value of the markInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MarkInfoType }
     *     
     */
    public MarkInfoType getMarkInfo() {
        return markInfo;
    }

    /**
     * Sets the value of the markInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkInfoType }
     *     
     */
    public void setMarkInfo(MarkInfoType value) {
        this.markInfo = value;
    }

}
