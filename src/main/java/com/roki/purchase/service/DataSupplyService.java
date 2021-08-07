package com.roki.purchase.service;


import com.roki.purchase.entity.*;
import com.roki.purchase.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class DataSupplyService {

    @Autowired
    private PurchaseHeaderRepository purchaseHeaderRepository;

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private BudgetLineRepository budgetLineRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public void dataSupplyForPurchaseRequest(@PathVariable Integer purchaseHeaderId, ModelAndView modelAndView) {
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        modelAndView.addObject("purchaseHeaderObject", purchaseHeader);

        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeader(purchaseHeader);
        modelAndView.addObject("purchaseLines", purchaseLines);

    }

    public void dataSupplyForPurchaseLine(Integer purchaseLineId, ModelAndView modelAndView) {
        PurchaseLineEntity purchaseLine = purchaseLineRepository.findById(purchaseLineId).get();
        modelAndView.addObject("purchaseLineObject", purchaseLine);

        List<BudgetLineEntity> budgetLines = budgetLineRepository.findAll();
        modelAndView.addObject("budgetLines", budgetLines);
    }

    public void purchaseHeaderDataByLoggedUserIdAndStatus(ModelAndView modelAndView, String status) {
        Optional<User> user = userService.getLoggedInUser();
        if(user.isPresent()) {
            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
            List<PurchaseHeaderEntity> purchaseHeadersList = purchaseHeaderRepository.findAllByUserIdAndStatusEquals
                    (userEntity.getUserId(),statusRepository.findByStatus(status));
            modelAndView.addObject("purchaseHeadersList", purchaseHeadersList);
        }
    }

    public void dataSupplyForPurchaseHeader(ModelAndView modelAndView) {

        List<SupplierEntity> suppliersList = supplierRepository.findAllByIsBlockedIsFalse();
        modelAndView.addObject("suppliersList",suppliersList);

        List<DepartmentEntity> departmentsList = departmentRepository.findAll();
        modelAndView.addObject("departmentsList",departmentsList);

        List<ExchangeRateEntity> currenciesList = exchangeRateRepository.findAll();
        modelAndView.addObject("currenciesList",currenciesList);

        Optional<User> user = userService.getLoggedInUser();
        if(user.isPresent()) {
            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
            modelAndView.addObject("userEntity",userEntity);
        }

        List<StatusEntity> statusesList = statusRepository.findAllByStatus("in progress");
        modelAndView.addObject("statusesList",statusesList);
    }
}
