//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.15 at 07:23:30 PM CEST 
//


package com.ftn.webservice.files;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accomondation" type="{http://ftn.com/webservice}accomodationSoap"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "accomondation"
})
@XmlRootElement(name = "RegisterAccomodationRequest")
public class RegisterAccomodationRequest {

    @XmlElement(required = true)
    protected AccomodationSoap accomondation;

    /**
     * Gets the value of the accomondation property.
     * 
     * @return
     *     possible object is
     *     {@link AccomodationSoap }
     *     
     */
    public AccomodationSoap getAccomondation() {
        return accomondation;
    }

    /**
     * Sets the value of the accomondation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccomodationSoap }
     *     
     */
    public void setAccomondation(AccomodationSoap value) {
        this.accomondation = value;
    }

}
