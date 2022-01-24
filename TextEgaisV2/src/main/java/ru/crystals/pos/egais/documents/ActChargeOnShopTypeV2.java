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
 * Акт постановки на баланс
 *
 * <p>Java class for ActChargeOnShopType_v2 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ActChargeOnShopType_v2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Identity" type="{http://fsrar.ru/WEGAIS/Common}IdentityType" minOccurs="0"/>
 *         &lt;element name="Header">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="Number" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
 *                   &lt;element name="ActDate" type="{http://fsrar.ru/WEGAIS/Common}DateNoTime"/>
 *                   &lt;element name="TypeChargeOn" type="{http://fsrar.ru/WEGAIS/CommonEnum}TypeChargeOn"/>
 *                   &lt;element name="ActWriteOff" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50" minOccurs="0"/>
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
 *                   &lt;element name="Position" type="{http://fsrar.ru/WEGAIS/ActChargeOnShop_v2}ActChargeOnShopPositionType" maxOccurs="unbounded"/>
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
@XmlType(name = "ActChargeOnShopType_v2", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2", propOrder = {
    "identity",
    "header",
    "content"
})
public class ActChargeOnShopTypeV2 {

    @XmlElement(name = "Identity", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2")
    protected String identity;
    @XmlElement(name = "Header", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2", required = true)
    protected ActChargeOnShopTypeV2 .Header header;
    @XmlElement(name = "Content", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2", required = true)
    protected ActChargeOnShopTypeV2 .Content content;

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
     *     {@link ActChargeOnShopTypeV2 .Header }
     *
     */
    public ActChargeOnShopTypeV2 .Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     *
     * @param value
     *     allowed object is
     *     {@link ActChargeOnShopTypeV2 .Header }
     *
     */
    public void setHeader(ActChargeOnShopTypeV2 .Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the content property.
     *
     * @return
     *     possible object is
     *     {@link ActChargeOnShopTypeV2 .Content }
     *
     */
    public ActChargeOnShopTypeV2 .Content getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     *
     * @param value
     *     allowed object is
     *     {@link ActChargeOnShopTypeV2 .Content }
     *
     */
    public void setContent(ActChargeOnShopTypeV2 .Content value) {
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
     *         &lt;element name="Position" type="{http://fsrar.ru/WEGAIS/ActChargeOnShop_v2}ActChargeOnShopPositionType" maxOccurs="unbounded"/>
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

        @XmlElement(name = "Position", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2", required = true)
        protected List<ActChargeOnShopPositionType> position;

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
         * {@link ActChargeOnShopPositionType }
         *
         *
         */
        public List<ActChargeOnShopPositionType> getPosition() {
            if (position == null) {
                position = new ArrayList<ActChargeOnShopPositionType>();
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
     *         &lt;element name="Number" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
     *         &lt;element name="ActDate" type="{http://fsrar.ru/WEGAIS/Common}DateNoTime"/>
     *         &lt;element name="TypeChargeOn" type="{http://fsrar.ru/WEGAIS/CommonEnum}TypeChargeOn"/>
     *         &lt;element name="ActWriteOff" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50" minOccurs="0"/>
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

        @XmlElement(name = "Number", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2", required = true)
        protected String number;
        @XmlElement(name = "ActDate", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2", required = true)
        @XmlSchemaType(name = "date")
        @XmlJavaTypeAdapter(DateAdapter.class)
        protected XMLGregorianCalendar actDate;
        @XmlElement(name = "TypeChargeOn", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2", required = true)
        @XmlSchemaType(name = "string")
        protected TypeChargeOn typeChargeOn;
        @XmlElement(name = "ActWriteOff", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2")
        protected String actWriteOff;
        @XmlElement(name = "Note", namespace = "http://fsrar.ru/WEGAIS/ActChargeOnShop_v2")
        protected String note;

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
         * Gets the value of the actDate property.
         *
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public XMLGregorianCalendar getActDate() {
            return actDate;
        }

        /**
         * Sets the value of the actDate property.
         *
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public void setActDate(XMLGregorianCalendar value) {
            this.actDate = value;
        }

        /**
         * Gets the value of the typeChargeOn property.
         *
         * @return
         *     possible object is
         *     {@link TypeChargeOn }
         *
         */
        public TypeChargeOn getTypeChargeOn() {
            return typeChargeOn;
        }

        /**
         * Sets the value of the typeChargeOn property.
         *
         * @param value
         *     allowed object is
         *     {@link TypeChargeOn }
         *
         */
        public void setTypeChargeOn(TypeChargeOn value) {
            this.typeChargeOn = value;
        }

        /**
         * Gets the value of the actWriteOff property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getActWriteOff() {
            return actWriteOff;
        }

        /**
         * Sets the value of the actWriteOff property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setActWriteOff(String value) {
            this.actWriteOff = value;
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