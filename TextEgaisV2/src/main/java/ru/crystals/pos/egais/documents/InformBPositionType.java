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
 * <p>Java class for InformBPositionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InformBPositionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Identity" type="{http://fsrar.ru/WEGAIS/Common}IdentityType"/>
 *         &lt;element name="InformBRegId" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformBPositionType", namespace = "http://fsrar.ru/WEGAIS/TTNInformBReg", propOrder = {

})
public class InformBPositionType {

    @XmlElement(name = "Identity", namespace = "http://fsrar.ru/WEGAIS/TTNInformBReg", required = true)
    protected String identity;
    @XmlElement(name = "InformBRegId", namespace = "http://fsrar.ru/WEGAIS/TTNInformBReg", required = true)
    protected String informBRegId;

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
     * Gets the value of the informBRegId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInformBRegId() {
        return informBRegId;
    }

    /**
     * Sets the value of the informBRegId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInformBRegId(String value) {
        this.informBRegId = value;
    }

}
