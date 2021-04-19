package com.roki.purchase.dto;

import com.roki.purchase.entity.SupplierEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseDto {

    private final List<SupplierEntity> suppliers;
    private final Map<String, Integer> page;

    public ResponseDto(final List<SupplierEntity> suppliers, final Map<String, Integer> page) {
        this.suppliers = new ArrayList<>(suppliers);
        this.page = new HashMap<>(page);
    }

    public static ResponseDto create(final List<SupplierEntity> suppliers, final Map<String, Integer> page) {
        return new ResponseDto(suppliers, page);
    }

    public List<SupplierEntity> getSuppliers() {
        return suppliers;
    }

    public Map<String, Integer> getPage() {
        return page;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "suppliers=" + suppliers +
                ", page=" + page +
                '}';
    }
}
