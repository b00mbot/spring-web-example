//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.18 at 10:53:47 PM PDT 
//


package com.kshah.model.globalweather.wsdl;

import javax.annotation.Generated;
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
 *         &lt;element name="GetCitiesByCountryResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "getCitiesByCountryResult"
})
@XmlRootElement(name = "GetCitiesByCountryResponse", namespace = "http://www.webserviceX.NET")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2018-03-18T10:53:47-07:00", comments = "JAXB RI v2.2.11")
public class GetCitiesByCountryResponse {

    @XmlElement(name = "GetCitiesByCountryResult", namespace = "http://www.webserviceX.NET")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2018-03-18T10:53:47-07:00", comments = "JAXB RI v2.2.11")
    protected String getCitiesByCountryResult;

    /**
     * Gets the value of the getCitiesByCountryResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2018-03-18T10:53:47-07:00", comments = "JAXB RI v2.2.11")
    public String getGetCitiesByCountryResult() {
        return getCitiesByCountryResult;
    }

    /**
     * Sets the value of the getCitiesByCountryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2018-03-18T10:53:47-07:00", comments = "JAXB RI v2.2.11")
    public void setGetCitiesByCountryResult(String value) {
        this.getCitiesByCountryResult = value;
    }

}