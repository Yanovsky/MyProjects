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
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Ответ на запрос о движении по форме Б
 * 
 * <p>Java class for ReplyHistFormB complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReplyHistFormB">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="InformBRegId" type="{http://fsrar.ru/WEGAIS/Common}NoEmptyString50"/>
 *         &lt;element name="HistFormBDate" type="{http://fsrar.ru/WEGAIS/Common}DateWTime"/>
 *         &lt;element name="HistoryB">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="OperationB" type="{http://fsrar.ru/WEGAIS/ReplyHistFormB}OperationBType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ReplyHistFormB", namespace = "http://fsrar.ru/WEGAIS/ReplyHistFormB", propOrder = {

})
public class ReplyHistFormB {

    @XmlElement(name = "InformBRegId", namespace = "http://fsrar.ru/WEGAIS/ReplyHistFormB", required = true)
    protected String informBRegId;
    @XmlElement(name = "HistFormBDate", namespace = "http://fsrar.ru/WEGAIS/ReplyHistFormB", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar histFormBDate;
    @XmlElement(name = "HistoryB", namespace = "http://fsrar.ru/WEGAIS/ReplyHistFormB", required = true)
    protected ReplyHistFormB.HistoryB historyB;

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

    /**
     * Gets the value of the histFormBDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHistFormBDate() {
        return histFormBDate;
    }

    /**
     * Sets the value of the histFormBDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHistFormBDate(XMLGregorianCalendar value) {
        this.histFormBDate = value;
    }

    /**
     * Gets the value of the historyB property.
     * 
     * @return
     *     possible object is
     *     {@link ReplyHistFormB.HistoryB }
     *     
     */
    public ReplyHistFormB.HistoryB getHistoryB() {
        return historyB;
    }

    /**
     * Sets the value of the historyB property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplyHistFormB.HistoryB }
     *     
     */
    public void setHistoryB(ReplyHistFormB.HistoryB value) {
        this.historyB = value;
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
     *         &lt;element name="OperationB" type="{http://fsrar.ru/WEGAIS/ReplyHistFormB}OperationBType" maxOccurs="unbounded" minOccurs="0"/>
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
        "operationB"
    })
    public static class HistoryB {

        @XmlElement(name = "OperationB", namespace = "http://fsrar.ru/WEGAIS/ReplyHistFormB")
        protected List<OperationBType> operationB;

        /**
         * Gets the value of the operationB property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the operationB property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOperationB().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OperationBType }
         * 
         * 
         */
        public List<OperationBType> getOperationB() {
            if (operationB == null) {
                operationB = new ArrayList<OperationBType>();
            }
            return this.operationB;
        }

    }

}
