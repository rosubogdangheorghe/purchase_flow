package com.roki.purchase.currency_exchange;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",propOrder = {
        "header",
        "body"
})

@XmlRootElement(name = "DataSet", namespace = "http://www.bnr.ro/xsd")
public class DataSet {

    @XmlElement(name = "header",namespace = "http://www.bnr.ro/xsd",required = true)
    protected LTHeader header;

    @XmlElement(name = "Body", namespace = "http://www.bnr.ro/xsd",required = true)
    protected DataSet.Body body;

    public LTHeader getHeader() {
        return header;
    }

    public void setHeader(LTHeader value) {
        this.header = value;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "",propOrder = {
            "subject",
            "description",
            "origCurrency",
            "cube"
    })
    public static class Body{


        @XmlElement(name = "Subject",namespace = "http://www.bnr.ro/xsd",required = true)
        protected String subject;
        @XmlElement(name = "Description",namespace = "http://www.bnr.ro/xsd")
        protected String description;
        @XmlElement(name = "OrigCurrency",namespace = "http://www.bnr.ro/xsd",required = true )
        protected String origCurrency;
        @XmlElement(name = "Cube",namespace = "http://www.bnr.ro/xsd",required = true )
        protected List<LTCube> cube;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String value) {
            this.subject = value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String value) {
            this.description = value;
        }

        public String getOrigCurrency() {
            return origCurrency;
        }

        public void setOrigCurrency(String value) {
            this.origCurrency = value;
        }

        public List<LTCube> getCube() {
            if(cube == null) {
                cube = new ArrayList<LTCube>();
            }
            return this.cube;
        }

        @Override
        public String toString() {
            return "Body{" +
                    "subject='" + subject + '\'' +
                    ", description='" + description + '\'' +
                    ", origCurrency='" + origCurrency + '\'' +
                    ", cube=" + cube +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
