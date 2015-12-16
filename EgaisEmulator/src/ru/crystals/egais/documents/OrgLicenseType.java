//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.09 at 11:00:14 AM MSK 
//


package ru.crystals.egais.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Лицензия организации
 * 
 * <p>Java class for OrgLicenseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrgLicenseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="datefrom" type="{http://fsrar.ru/WEGAIS/Common}DateNoTime"/>
 *         &lt;element name="dateto" type="{http://fsrar.ru/WEGAIS/Common}DateNoTime"/>
 *         &lt;element name="orgdistribute" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgLicenseType", propOrder = {
    "number",
    "datefrom",
    "dateto",
    "orgdistribute"
})
public class OrgLicenseType {

    @XmlElement(required = true)
    protected String number;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datefrom;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateto;
    protected String orgdistribute;

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
     * Gets the value of the datefrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatefrom() {
        return datefrom;
    }

    /**
     * Sets the value of the datefrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatefrom(XMLGregorianCalendar value) {
        this.datefrom = value;
    }

    /**
     * Gets the value of the dateto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateto() {
        return dateto;
    }

    /**
     * Sets the value of the dateto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateto(XMLGregorianCalendar value) {
        this.dateto = value;
    }

    /**
     * Gets the value of the orgdistribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgdistribute() {
        return orgdistribute;
    }

    /**
     * Sets the value of the orgdistribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgdistribute(String value) {
        this.orgdistribute = value;
    }

}
