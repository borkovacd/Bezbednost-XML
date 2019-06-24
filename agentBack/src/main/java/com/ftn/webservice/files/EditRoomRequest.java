//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.24 at 01:13:03 PM CEST 
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
 *         &lt;element name="accomodationId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="roomId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="editRoomData" type="{http://ftn.com/webservice}roomSoap"/&gt;
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
    "accomodationId",
    "roomId",
    "editRoomData"
})
@XmlRootElement(name = "EditRoomRequest")
public class EditRoomRequest {

    @XmlElement(required = true)
    protected String request;
    protected long accomodationId;
    protected long roomId;
    @XmlElement(required = true)
    protected RoomSoap editRoomData;

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
     * Gets the value of the accomodationId property.
     * 
     */
    public long getAccomodationId() {
        return accomodationId;
    }

    /**
     * Sets the value of the accomodationId property.
     * 
     */
    public void setAccomodationId(long value) {
        this.accomodationId = value;
    }

    /**
     * Gets the value of the roomId property.
     * 
     */
    public long getRoomId() {
        return roomId;
    }

    /**
     * Sets the value of the roomId property.
     * 
     */
    public void setRoomId(long value) {
        this.roomId = value;
    }

    /**
     * Gets the value of the editRoomData property.
     * 
     * @return
     *     possible object is
     *     {@link RoomSoap }
     *     
     */
    public RoomSoap getEditRoomData() {
        return editRoomData;
    }

    /**
     * Sets the value of the editRoomData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomSoap }
     *     
     */
    public void setEditRoomData(RoomSoap value) {
        this.editRoomData = value;
    }

}
