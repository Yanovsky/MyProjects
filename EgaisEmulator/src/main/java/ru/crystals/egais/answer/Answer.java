//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.06.02 at 05:57:14 PM MSK
//


package ru.crystals.egais.answer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}sign"/>
 *         &lt;element name="ver" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "url",
    "error",
    "sign",
    "ver"
})
@XmlRootElement(name = "A")
public class Answer {

	@XmlElements({ @XmlElement(required = true, name = "url") })
	protected List<AnswerURL> url;
	@XmlElement(required = false)
	protected String error;
	@XmlElement(required = true)
    protected String sign;
    protected int ver;

    /**
     * Gets the value of the url property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
	public List<AnswerURL> getUrl() {
		if (url == null) {
			url = new ArrayList<AnswerURL>();
		}
		return url;
    }

	public String getFirstUrl() {
		return getUrl().isEmpty() ? "" : getUrl().get(0).getUrl();
	}

    /**
     * Sets the value of the url property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
	public void setUrl(AnswerURL value) {
		this.getUrl().add(value);
    }

    /**
     * Gets the value of the sign property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSign() {
        return sign;
    }

    /**
     * Sets the value of the sign property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSign(String value) {
        this.sign = value;
    }

    /**
     * Gets the value of the ver property.
     *
     */
    public int getVer() {
        return ver;
    }

    /**
     * Sets the value of the ver property.
     *
     */
    public void setVer(int value) {
        this.ver = value;
    }

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}