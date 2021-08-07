package com.roki.purchase.data_pagination;

import com.roki.purchase.entity.SupplierEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppliersPaginationData {

    private List<SupplierEntity> suppliers;
    private Map<String, Integer> page;

    public SuppliersPaginationData( List<SupplierEntity> suppliers, Map<String, Integer> page) {
        this.suppliers = new ArrayList<>(suppliers);
        this.page = new HashMap<>(page);
    }

    public static SuppliersPaginationData create( List<SupplierEntity> suppliers, Map<String, Integer> page) {
        return new SuppliersPaginationData(suppliers, page);
    }

    public List<SupplierEntity> getSuppliers() {
        return suppliers;
    }

    public Map<String, Integer> getPage() {
        return page;
    }

    @Override
    public String toString() {
        return "SuppliersPaginationData{" +
                "suppliers=" + suppliers +
                ", page=" + page +
                '}';
    }
}
