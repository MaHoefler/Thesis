//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.24 at 10:01:57 AM CEST 
//


package edu.kit.pagexml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 				Numbered group (contains ordered elements)
 * 			
 * 
 * <p>Java class for OrderedGroupType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderedGroupType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserDefined" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2017-07-15}UserDefinedType" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="RegionRefIndexed" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2017-07-15}RegionRefIndexedType"/>
 *           &lt;element name="OrderedGroupIndexed" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2017-07-15}OrderedGroupIndexedType"/>
 *           &lt;element name="UnorderedGroupIndexed" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2017-07-15}UnorderedGroupIndexedType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="regionRef" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="caption" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2017-07-15}GroupTypeSimpleType" />
 *       &lt;attribute name="continuation" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="custom" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="comments" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderedGroupType", propOrder = {
    "userDefined",
    "regionRefIndexedOrOrderedGroupIndexedOrUnorderedGroupIndexed"
})
public class OrderedGroupType {

    @XmlElement(name = "UserDefined")
    protected UserDefinedType userDefined;
    @XmlElements({
        @XmlElement(name = "RegionRefIndexed", type = RegionRefIndexedType.class),
        @XmlElement(name = "OrderedGroupIndexed", type = OrderedGroupIndexedType.class),
        @XmlElement(name = "UnorderedGroupIndexed", type = UnorderedGroupIndexedType.class)
    })
    protected List<Object> regionRefIndexedOrOrderedGroupIndexedOrUnorderedGroupIndexed;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "regionRef")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object regionRef;
    @XmlAttribute(name = "caption")
    protected String caption;
    @XmlAttribute(name = "type")
    protected GroupTypeSimpleType type;
    @XmlAttribute(name = "continuation")
    protected Boolean continuation;
    @XmlAttribute(name = "custom")
    protected String custom;
    @XmlAttribute(name = "comments")
    protected String comments;

    /**
     * Gets the value of the userDefined property.
     * 
     * @return
     *     possible object is
     *     {@link UserDefinedType }
     *     
     */
    public UserDefinedType getUserDefined() {
        return userDefined;
    }

    /**
     * Sets the value of the userDefined property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserDefinedType }
     *     
     */
    public void setUserDefined(UserDefinedType value) {
        this.userDefined = value;
    }

    /**
     * Gets the value of the regionRefIndexedOrOrderedGroupIndexedOrUnorderedGroupIndexed property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the regionRefIndexedOrOrderedGroupIndexedOrUnorderedGroupIndexed property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegionRefIndexedOrOrderedGroupIndexedOrUnorderedGroupIndexed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegionRefIndexedType }
     * {@link OrderedGroupIndexedType }
     * {@link UnorderedGroupIndexedType }
     * 
     * 
     */
    public List<Object> getRegionRefIndexedOrOrderedGroupIndexedOrUnorderedGroupIndexed() {
        if (regionRefIndexedOrOrderedGroupIndexedOrUnorderedGroupIndexed == null) {
            regionRefIndexedOrOrderedGroupIndexedOrUnorderedGroupIndexed = new ArrayList<Object>();
        }
        return this.regionRefIndexedOrOrderedGroupIndexedOrUnorderedGroupIndexed;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the regionRef property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getRegionRef() {
        return regionRef;
    }

    /**
     * Sets the value of the regionRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setRegionRef(Object value) {
        this.regionRef = value;
    }

    /**
     * Gets the value of the caption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the value of the caption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaption(String value) {
        this.caption = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link GroupTypeSimpleType }
     *     
     */
    public GroupTypeSimpleType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupTypeSimpleType }
     *     
     */
    public void setType(GroupTypeSimpleType value) {
        this.type = value;
    }

    /**
     * Gets the value of the continuation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isContinuation() {
        return continuation;
    }

    /**
     * Sets the value of the continuation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setContinuation(Boolean value) {
        this.continuation = value;
    }

    /**
     * Gets the value of the custom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustom() {
        return custom;
    }

    /**
     * Sets the value of the custom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustom(String value) {
        this.custom = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

}
