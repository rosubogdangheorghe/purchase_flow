package com.roki.purchase.service;

import com.roki.purchase.entity.SupplierEntity;
import com.roki.purchase.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Page findAllByPage(Integer pageNumber, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNumber-1,pageSize, Sort.by(sortBy).ascending());
        Page<SupplierEntity> pagedResult = supplierRepository.findAll(paging);
        return pagedResult;
    }

}
