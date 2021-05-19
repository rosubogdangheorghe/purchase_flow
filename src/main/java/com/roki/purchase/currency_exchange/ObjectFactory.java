package com.roki.purchase.currency_exchange;


import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {


    public ObjectFactory() {

    }
    public DataSet createDataSet() {
        return new DataSet();
    }

    public LTCube createLTCube() {
        return new LTCube();
    }

    public LTHeader createLTHeader() {
        return new LTHeader();
    }
    public DataSet.Body createDataSetBody() {
        return new DataSet.Body();
    }
    public LTCube.Rate createLTCubeRate() {
        return new LTCube.Rate();
    }

}
