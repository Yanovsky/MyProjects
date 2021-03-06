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
 *         &lt;element name="Identity" type="{http://fsrar.ru/WEGAIS/Common}IdentityType"/>
 *         &lt;element name="InformF2RegId" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
 *         &lt;element name="RealQuantity" type="{http://fsrar.ru/WEGAIS/Common}NoNegativeDecimalType"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PositionType", namespace = "http://fsrar.ru/WEGAIS/ActTTNSingle_v2", propOrder = {

})
public class PositionType6 {

    @XmlElement(name = "Identity", namespace = "http://fsrar.ru/WEGAIS/ActTTNSingle_v2", required = true)
    protected String identity;
    @XmlElement(name = "InformF2RegId", namespace = "http://fsrar.ru/WEGAIS/ActTTNSingle_v2", required = true)
    protected String informF2RegId;
    @XmlElement(name = "RealQuantity", namespace = "http://fsrar.ru/WEGAIS/ActTTNSingle_v2", required = true)
    protected BigDecimal realQuantity;

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
     * Gets the value of the informF2RegId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInformF2RegId() {
        return informF2RegId;
    }

    /**
     * Sets the value of the informF2RegId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInformF2RegId(String value) {
        this.informF2RegId = value;
    }

    /**
     * Gets the value of the realQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRealQuantity() {
        return realQuantity;
    }

    /**
     * Sets the value of the realQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRealQuantity(BigDecimal value) {
        this.realQuantity = value;
    }

}
