//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.12 at 12:12:18 PM MSK 
//


package ru.crystals.pos.egais.documents;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Позиция
 * 
 * <p>Java class for PositionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PositionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Product" type="{http://fsrar.ru/WEGAIS/ProductRef_v2}ProductInfo_v2"/>
 *         &lt;element name="Pack_ID" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50" minOccurs="0"/>
 *         &lt;element name="Quantity" type="{http://fsrar.ru/WEGAIS/Common}PositiveDecimalType"/>
 *         &lt;element name="Price" type="{http://fsrar.ru/WEGAIS/Common}NoNegativeDecimalType"/>
 *         &lt;element name="Party" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50" minOccurs="0"/>
 *         &lt;element name="Identity" type="{http://fsrar.ru/WEGAIS/Common}IdentityType"/>
 *         &lt;element name="InformF1" type="{http://fsrar.ru/WEGAIS/ProductRef_v2}InformF1Type"/>
 *         &lt;element name="InformF2" type="{http://fsrar.ru/WEGAIS/ProductRef_v2}InformF2Type"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PositionType", namespace = "http://fsrar.ru/WEGAIS/TTNSingle_v2", propOrder = {

})
public class PositionType5 {

    @XmlElement(name = "Product", namespace = "http://fsrar.ru/WEGAIS/TTNSingle_v2", required = true)
    protected ProductInfoV2 product;
    @XmlElement(name = "Pack_ID", namespace = "http://fsrar.ru/WEGAIS/TTNSingle_v2")
    protected String packID;
    @XmlElement(name = "Quantity", namespace = "http://fsrar.ru/WEGAIS/TTNSingle_v2", required = true)
    protected BigDecimal quantity;
    @XmlElement(name = "Price", namespace = "http://fsrar.ru/WEGAIS/TTNSingle_v2", required = true)
    protected BigDecimal price;
    @XmlElement(name = "Party", namespace = "http://fsrar.ru/WEGAIS/TTNSingle_v2")
    protected String party;
    @XmlElement(name = "Identity", namespace = "http://fsrar.ru/WEGAIS/TTNSingle_v2", required = true)
    protected String identity;
    @XmlElement(name = "InformF1", namespace = "http://fsrar.ru/WEGAIS/TTNSingle_v2", required = true)
    protected InformF1Type informF1;
    @XmlElement(name = "InformF2", namespace = "http://fsrar.ru/WEGAIS/TTNSingle_v2", required = true)
    protected InformF2Type informF2;

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link ProductInfoV2 }
     *     
     */
    public ProductInfoV2 getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductInfoV2 }
     *     
     */
    public void setProduct(ProductInfoV2 value) {
        this.product = value;
    }

    /**
     * Gets the value of the packID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackID() {
        return packID;
    }

    /**
     * Sets the value of the packID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackID(String value) {
        this.packID = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantity(BigDecimal value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    /**
     * Gets the value of the party property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParty() {
        return party;
    }

    /**
     * Sets the value of the party property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParty(String value) {
        this.party = value;
    }

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
     * Gets the value of the informF1 property.
     * 
     * @return
     *     possible object is
     *     {@link InformF1Type }
     *     
     */
    public InformF1Type getInformF1() {
        return informF1;
    }

    /**
     * Sets the value of the informF1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformF1Type }
     *     
     */
    public void setInformF1(InformF1Type value) {
        this.informF1 = value;
    }

    /**
     * Gets the value of the informF2 property.
     * 
     * @return
     *     possible object is
     *     {@link InformF2Type }
     *     
     */
    public InformF2Type getInformF2() {
        return informF2;
    }

    /**
     * Sets the value of the informF2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformF2Type }
     *     
     */
    public void setInformF2(InformF2Type value) {
        this.informF2 = value;
    }

}
