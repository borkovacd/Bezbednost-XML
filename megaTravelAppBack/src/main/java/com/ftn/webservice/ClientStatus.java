//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.29 at 12:41:45 PM CEST 
//


package com.ftn.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clientStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="clientStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AKTIVAN"/>
 *     &lt;enumeration value="NEAKTIVAN"/>
 *     &lt;enumeration value="BLOKIRAN"/>
 *     &lt;enumeration value="UKLONJEN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "clientStatus")
@XmlEnum
public enum ClientStatus {

    AKTIVAN,
    NEAKTIVAN,
    BLOKIRAN,
    UKLONJEN;

    public String value() {
        return name();
    }

    public static ClientStatus fromValue(String v) {
        return valueOf(v);
    }

}
