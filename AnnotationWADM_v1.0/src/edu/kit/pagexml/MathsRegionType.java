//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.24 at 10:01:57 AM CEST 
//


package edu.kit.pagexml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Regions containing equations and mathematical symbols
 * 				should be marked as maths regions.
 * 			
 * 
 * <p>Java class for MathsRegionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MathsRegionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2017-07-15}RegionType">
 *       &lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="bgColour" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2017-07-15}ColourSimpleType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MathsRegionType")
public class MathsRegionType
    extends RegionType
{

    @XmlAttribute(name = "orientation")
    protected Float orientation;
    @XmlAttribute(name = "bgColour")
    protected ColourSimpleType bgColour;

    /**
     * Gets the value of the orientation property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getOrientation() {
        return orientation;
    }

    /**
     * Sets the value of the orientation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setOrientation(Float value) {
        this.orientation = value;
    }

    /**
     * Gets the value of the bgColour property.
     * 
     * @return
     *     possible object is
     *     {@link ColourSimpleType }
     *     
     */
    public ColourSimpleType getBgColour() {
        return bgColour;
    }

    /**
     * Sets the value of the bgColour property.
     * 
     * @param value
     *     allowed object is
     *     {@link ColourSimpleType }
     *     
     */
    public void setBgColour(ColourSimpleType value) {
        this.bgColour = value;
    }

}
