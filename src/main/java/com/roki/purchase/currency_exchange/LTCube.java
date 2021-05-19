package com.roki.purchase.currency_exchange;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LT_Cube", namespace = "http://www.bnr.ro/xsd", propOrder = {
        "rate"
})


public class LTCube {

    @XmlElement(name = "Rate", namespace = "http://www.bnr.ro/xsd" ,required = true)
    protected List<LTCube.Rate> rate;
    @XmlAttribute(name = "date",required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;

    public List<LTCube.Rate> getRate() {
        if(rate ==null) {
            rate = new ArrayList<LTCube.Rate>();
        }
        return this.rate;
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "" ,propOrder = {
            "value"
    })
    public static class Rate{

        @XmlValue
        protected BigDecimal value;
        @XmlAttribute(name = "currency",required = true)
        protected String currency;
        @XmlAttribute(name = "multiplier")
        protected BigInteger multiplier;


        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String value) {
            this.currency = value;
        }

        public BigInteger getMultiplier() {
            return multiplier;
        }

        public void setMultiplier(BigInteger value) {
            this.multiplier = value;
        }

        @Override
        public String toString() {
            return "Rate{" +
                    "value=" + value +
                    ", currency='" + currency + '\'' +
                    ", multiplier=" + multiplier +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LTCube{" +
                "rate=" + rate +
                ", date=" + date +
                '}';
    }
}
