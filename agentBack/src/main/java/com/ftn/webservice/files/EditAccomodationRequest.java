//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.26 at 08:54:57 AM CEST 
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
 *         &lt;element name="request" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="editAccomodationId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="editAccomodationData" type="{http://ftn.com/webservice}accomodationSoap"/&gt;
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
    "request",
    "editAccomodationId",
    "editAccomodationData"
})
@XmlRootElement(name = "EditAccomodationRequest")
public class EditAccomodationRequest {

    @XmlElement(required = true)
    protected String request;
    protected long editAccomodationId;
    @XmlElement(required = true)
    protected AccomodationSoap editAccomodationData;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequest(String value) {
        this.request = value;
    }

    /**
     * Gets the value of the editAccomodationId property.
     * 
     */
    public long getEditAccomodationId() {
        return editAccomodationId;
    }

    /**
     * Sets the value of the editAccomodationId property.
     * 
     */
    public void setEditAccomodationId(long value) {
        this.editAccomodationId = value;
    }

    /**
     * Gets the value of the editAccomodationData property.
     * 
     * @return
     *     possible object is
     *     {@link AccomodationSoap }
     *     
     */
    public AccomodationSoap getEditAccomodationData() {
        return editAccomodationData;
    }

    /**
     * Sets the value of the editAccomodationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccomodationSoap }
     *     
     */
    public void setEditAccomodationData(AccomodationSoap value) {
        this.editAccomodationData = value;
    }

}
