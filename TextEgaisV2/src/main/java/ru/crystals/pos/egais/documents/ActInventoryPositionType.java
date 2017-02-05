//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.12 at 12:12:18 PM MSK 
//


package ru.crystals.pos.egais.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Позиция
 * 
 * <p>Java class for ActInventoryPositionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ActInventoryPositionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Identity" type="{http://fsrar.ru/WEGAIS/Common}IdentityType"/>
 *         &lt;element name="Product" type="{http://fsrar.ru/WEGAIS/ProductRef}ProductInfo"/>
 *         &lt;element name="Quantity" type="{http://fsrar.ru/WEGAIS/Common}PositiveDecimalType"/>
 *         &lt;element name="InformA" type="{http://fsrar.ru/WEGAIS/ActInventoryABInfo}InformARegType"/>
 *         &lt;element name="InformB">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="InformBItem" type="{http://fsrar.ru/WEGAIS/ActInventoryABInfo}InformBTypeRegItem" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "ActInventoryPositionType", namespace = "http://fsrar.ru/WEGAIS/ActInventorySingle", propOrder = {

})
public class ActInventoryPositionType {

    @XmlElement(name = "Identity", namespace = "http://fsrar.ru/WEGAIS/ActInventorySingle", required = true)
    protected String identity;
    @XmlElement(name = "Product", namespace = "http://fsrar.ru/WEGAIS/ActInventorySingle", required = true)
    protected ProductInfo product;
    @XmlElement(name = "Quantity", namespace = "http://fsrar.ru/WEGAIS/ActInventorySingle", required = true)
    protected BigDecimal quantity;
    @XmlElement(name = "InformA", namespace = "http://fsrar.ru/WEGAIS/ActInventorySingle", required = true)
    protected InformARegType informA;
    @XmlElement(name = "InformB", namespace = "http://fsrar.ru/WEGAIS/ActInventorySingle", required = true)
    protected ActInventoryPositionType.InformB informB;

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
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link ProductInfo }
     *     
     */
    public ProductInfo getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductInfo }
     *     
     */
    public void setProduct(ProductInfo value) {
        this.product = value;
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
     * Gets the value of the informA property.
     * 
     * @return
     *     possible object is
     *     {@link InformARegType }
     *     
     */
    public InformARegType getInformA() {
        return informA;
    }

    /**
     * Sets the value of the informA property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformARegType }
     *     
     */
    public void setInformA(InformARegType value) {
        this.informA = value;
    }

    /**
     * Gets the value of the informB property.
     * 
     * @return
     *     possible object is
     *     {@link ActInventoryPositionType.InformB }
     *     
     */
    public ActInventoryPositionType.InformB getInformB() {
        return informB;
    }

    /**
     * Sets the value of the informB property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActInventoryPositionType.InformB }
     *     
     */
    public void setInformB(ActInventoryPositionType.InformB value) {
        this.informB = value;
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
     *         &lt;element name="InformBItem" type="{http://fsrar.ru/WEGAIS/ActInventoryABInfo}InformBTypeRegItem" maxOccurs="unbounded"/>
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
        "informBItem"
    })
    public static class InformB {

        @XmlElement(name = "InformBItem", namespace = "http://fsrar.ru/WEGAIS/ActInventorySingle", required = true)
        protected List<InformBTypeRegItem> informBItem;

        /**
         * Gets the value of the informBItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the informBItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInformBItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InformBTypeRegItem }
         * 
         * 
         */
        public List<InformBTypeRegItem> getInformBItem() {
            if (informBItem == null) {
                informBItem = new ArrayList<InformBTypeRegItem>();
            }
            return this.informBItem;
        }

    }

}
