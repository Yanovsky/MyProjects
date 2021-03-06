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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Сведения о контракте на поставку продукции
 * 
 * <p>Java class for ProductContractType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductContractType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="number" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
 *         &lt;element name="date" type="{http://fsrar.ru/WEGAIS/Common}DateNoTime"/>
 *         &lt;element name="Supplier" type="{http://fsrar.ru/WEGAIS/ClientRef_v2}OrgInfo_v2"/>
 *         &lt;element name="Contragent" type="{http://fsrar.ru/WEGAIS/ClientRef_v2}OrgInfo_v2"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductContractType", namespace = "http://fsrar.ru/WEGAIS/ClientRef_v2", propOrder = {

})
public class ProductContractType2 {

    @XmlElement(namespace = "http://fsrar.ru/WEGAIS/ClientRef_v2", required = true)
    protected String number;
    @XmlElement(namespace = "http://fsrar.ru/WEGAIS/ClientRef_v2", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "Supplier", namespace = "http://fsrar.ru/WEGAIS/ClientRef_v2", required = true)
    protected OrgInfoV2 supplier;
    @XmlElement(name = "Contragent", namespace = "http://fsrar.ru/WEGAIS/ClientRef_v2", required = true)
    protected OrgInfoV2 contragent;

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the supplier property.
     * 
     * @return
     *     possible object is
     *     {@link OrgInfoV2 }
     *     
     */
    public OrgInfoV2 getSupplier() {
        return supplier;
    }

    /**
     * Sets the value of the supplier property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgInfoV2 }
     *     
     */
    public void setSupplier(OrgInfoV2 value) {
        this.supplier = value;
    }

    /**
     * Gets the value of the contragent property.
     * 
     * @return
     *     possible object is
     *     {@link OrgInfoV2 }
     *     
     */
    public OrgInfoV2 getContragent() {
        return contragent;
    }

    /**
     * Sets the value of the contragent property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgInfoV2 }
     *     
     */
    public void setContragent(OrgInfoV2 value) {
        this.contragent = value;
    }

}
