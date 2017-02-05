//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2016.09.12 at 12:12:18 PM MSK
//


package ru.crystals.pos.egais.documents;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.crystals.pos.egais.tools.DateAdapter;

/**
 * Передача продукции в торговый зал
 *
 * <p>Java class for TransferToShopType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TransferToShopType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Identity" type="{http://fsrar.ru/WEGAIS/Common}IdentityType" minOccurs="0"/>
 *         &lt;element name="Header">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="TransferNumber" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
 *                   &lt;element name="TransferDate" type="{http://fsrar.ru/WEGAIS/Common}DateNoTime"/>
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
 *         &lt;element name="Content">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Position" type="{http://fsrar.ru/WEGAIS/TransferToShop}TransferToShopPositionType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
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
@XmlType(name = "TransferToShopType", namespace = "http://fsrar.ru/WEGAIS/TransferToShop", propOrder = {
    "identity",
    "header",
    "content"
})
public class TransferToShopType {

    @XmlElement(name = "Identity", namespace = "http://fsrar.ru/WEGAIS/TransferToShop")
    protected String identity;
    @XmlElement(name = "Header", namespace = "http://fsrar.ru/WEGAIS/TransferToShop", required = true)
    protected TransferToShopType.Header header;
    @XmlElement(name = "Content", namespace = "http://fsrar.ru/WEGAIS/TransferToShop", required = true)
    protected TransferToShopType.Content content;

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
     *     {@link TransferToShopType.Header }
     *
     */
    public TransferToShopType.Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     *
     * @param value
     *     allowed object is
     *     {@link TransferToShopType.Header }
     *
     */
    public void setHeader(TransferToShopType.Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the content property.
     *
     * @return
     *     possible object is
     *     {@link TransferToShopType.Content }
     *
     */
    public TransferToShopType.Content getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     *
     * @param value
     *     allowed object is
     *     {@link TransferToShopType.Content }
     *
     */
    public void setContent(TransferToShopType.Content value) {
        this.content = value;
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
     *       &lt;sequence>
     *         &lt;element name="Position" type="{http://fsrar.ru/WEGAIS/TransferToShop}TransferToShopPositionType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "position"
    })
    public static class Content {

        @XmlElement(name = "Position", namespace = "http://fsrar.ru/WEGAIS/TransferToShop", required = true)
        protected List<TransferToShopPositionType> position;

        /**
         * Gets the value of the position property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the position property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPosition().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TransferToShopPositionType }
         *
         *
         */
        public List<TransferToShopPositionType> getPosition() {
            if (position == null) {
                position = new ArrayList<TransferToShopPositionType>();
            }
            return this.position;
        }

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
     *         &lt;element name="TransferNumber" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
     *         &lt;element name="TransferDate" type="{http://fsrar.ru/WEGAIS/Common}DateNoTime"/>
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

        @XmlElement(name = "TransferNumber", namespace = "http://fsrar.ru/WEGAIS/TransferToShop", required = true)
        protected String transferNumber;
        @XmlElement(name = "TransferDate", namespace = "http://fsrar.ru/WEGAIS/TransferToShop", required = true)
        @XmlSchemaType(name = "date")
        @XmlJavaTypeAdapter(DateAdapter.class)
        protected XMLGregorianCalendar transferDate;
        @XmlElement(name = "Note", namespace = "http://fsrar.ru/WEGAIS/TransferToShop")
        protected String note;

        /**
         * Gets the value of the transferNumber property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getTransferNumber() {
            return transferNumber;
        }

        /**
         * Sets the value of the transferNumber property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setTransferNumber(String value) {
            this.transferNumber = value;
        }

        /**
         * Gets the value of the transferDate property.
         *
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public XMLGregorianCalendar getTransferDate() {
            return transferDate;
        }

        /**
         * Sets the value of the transferDate property.
         *
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public void setTransferDate(XMLGregorianCalendar value) {
            this.transferDate = value;
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
