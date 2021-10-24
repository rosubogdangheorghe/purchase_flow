package com.roki.purchase.currency_exchange;


import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LT_Header",namespace = "http://www.bnr.ro/xsd", propOrder = {
        "publisher",
        "publishingDate",
        "messageType"
})
public class LTHeader {

    @XmlElement(name = "Publisher",namespace = "http://www.bnr.ro/xsd",required = true)
    protected String publisher;

    @XmlElement(name = "PublishingDate",namespace = "http://www.bnr.ro/xsd",required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar publishingDate;

    @XmlElement(name = "MessageType",namespace = "http://www.bnr.ro/xsd",required = true)
    protected String messageType;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String value) {
        this.publisher = value;
    }

    public XMLGregorianCalendar getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(XMLGregorianCalendar value) {
        this.publishingDate = value;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String value) {
        this.messageType = value;
    }
}
