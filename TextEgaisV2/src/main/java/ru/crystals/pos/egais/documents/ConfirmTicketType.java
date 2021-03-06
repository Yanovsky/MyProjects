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
 * Подтверждение акта разногласий для Товарно-Транспортной Накладной
 * 
 * <p>Java class for ConfirmTicketType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfirmTicketType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Identity" type="{http://fsrar.ru/WEGAIS/Common}IdentityType" minOccurs="0"/>
 *         &lt;element name="Header">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="IsConfirm" type="{http://fsrar.ru/WEGAIS/ConfirmTicket}ConclusionType"/>
 *                   &lt;element name="TicketNumber" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
 *                   &lt;element name="TicketDate" type="{http://fsrar.ru/WEGAIS/Common}DateNoTime"/>
 *                   &lt;element name="WBRegId" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
 *                   &lt;element name="Note" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="500"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfirmTicketType", namespace = "http://fsrar.ru/WEGAIS/ConfirmTicket", propOrder = {
    "identity",
    "header"
})
public class ConfirmTicketType {

    @XmlElement(name = "Identity", namespace = "http://fsrar.ru/WEGAIS/ConfirmTicket")
    protected String identity;
    @XmlElement(name = "Header", namespace = "http://fsrar.ru/WEGAIS/ConfirmTicket", required = true)
    protected ConfirmTicketType.Header header;

    /**
     * Gets the value of the identity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * Sets the value of the identity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentity(String value) {
        this.identity = value;
    }

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link ConfirmTicketType.Header }
     *     
     */
    public ConfirmTicketType.Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfirmTicketType.Header }
     *     
     */
    public void setHeader(ConfirmTicketType.Header value) {
        this.header = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;all>
     *         &lt;element name="IsConfirm" type="{http://fsrar.ru/WEGAIS/ConfirmTicket}ConclusionType"/>
     *         &lt;element name="TicketNumber" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
     *         &lt;element name="TicketDate" type="{http://fsrar.ru/WEGAIS/Common}DateNoTime"/>
     *         &lt;element name="WBRegId" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
     *         &lt;element name="Note" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="500"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class Header {

        @XmlElement(name = "IsConfirm", namespace = "http://fsrar.ru/WEGAIS/ConfirmTicket", required = true)
        @XmlSchemaType(name = "string")
        protected ConclusionType2 isConfirm;
        @XmlElement(name = "TicketNumber", namespace = "http://fsrar.ru/WEGAIS/ConfirmTicket", required = true)
        protected String ticketNumber;
        @XmlElement(name = "TicketDate", namespace = "http://fsrar.ru/WEGAIS/ConfirmTicket", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar ticketDate;
        @XmlElement(name = "WBRegId", namespace = "http://fsrar.ru/WEGAIS/ConfirmTicket", required = true)
        protected String wbRegId;
        @XmlElement(name = "Note", namespace = "http://fsrar.ru/WEGAIS/ConfirmTicket")
        protected String note;

        /**
         * Gets the value of the isConfirm property.
         * 
         * @return
         *     possible object is
         *     {@link ConclusionType2 }
         *     
         */
        public ConclusionType2 getIsConfirm() {
            return isConfirm;
        }

        /**
         * Sets the value of the isConfirm property.
         * 
         * @param value
         *     allowed object is
         *     {@link ConclusionType2 }
         *     
         */
        public void setIsConfirm(ConclusionType2 value) {
            this.isConfirm = value;
        }

        /**
         * Gets the value of the ticketNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTicketNumber() {
            return ticketNumber;
        }

        /**
         * Sets the value of the ticketNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTicketNumber(String value) {
            this.ticketNumber = value;
        }

        /**
         * Gets the value of the ticketDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTicketDate() {
            return ticketDate;
        }

        /**
         * Sets the value of the ticketDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTicketDate(XMLGregorianCalendar value) {
            this.ticketDate = value;
        }

        /**
         * Gets the value of the wbRegId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWBRegId() {
            return wbRegId;
        }

        /**
         * Sets the value of the wbRegId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWBRegId(String value) {
            this.wbRegId = value;
        }

        /**
         * Gets the value of the note property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNote() {
            return note;
        }

        /**
         * Sets the value of the note property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNote(String value) {
            this.note = value;
        }

    }

}
