package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.PurchaseHeaderEntity;
import com.roki.purchase.entity.PurchaseLineEntity;
import com.roki.purchase.repository.PurchaseHeaderRepository;
import com.roki.purchase.repository.PurchaseLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/web")
public class PurchaseLineController {

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private PurchaseHeaderRepository purchaseHeaderRepository;


    @GetMapping("/purchase-request/{purchaseHeaderId}")
    public ModelAndView getAllPurchaseLines(@PathVariable Integer purchaseHeaderId) {
        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-line/purchase-request-template");
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        modelAndView.addObject("purchaseHeaderObject",purchaseHeader);
        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeader(purchaseHeader);
        modelAndView.addObject("purchaseLines",purchaseLines);
        List<Double> purchaseLinesValue = new ArrayList<>();
        for(PurchaseLineEntity purchaseLine : purchaseLines) {
            purchaseLinesValue.add((double) Math.round(purchaseLine.getQuantity()*purchaseLine.getUnitPrice()*100)/100);
        }
        modelAndView.addObject("addPurchaseLine",false);
        modelAndView.addObject("purchaseLineObject",new PurchaseLineEntity());
        return modelAndView;


    }

    @GetMapping("/purchase-line/add/{purchaseHeaderId}")
    public ModelAndView addPurchaseLine(@PathVariable Integer purchaseHeaderId){
        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-line/purchase-request-template");

        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        modelAndView.addObject("purchaseHeaderObject",purchaseHeader);

        modelAndView.addObject("addPurchaseLine",true);
        modelAndView.addObject("purchaseLineObject",new PurchaseLineEntity());

        return modelAndView;
    }
    @PostMapping("/purchase-line/save")
    public ModelAndView savePurchaseLine(@ModelAttribute("purchaseLineObject") PurchaseLineEntity purchaseLine, HttpServletRequest request) {

        String string =   request.getParameter("purchaseHeaderId");
        int purchaseHeaderId = Integer.parseInt(string);
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findByPurchaseHeaderId(purchaseHeaderId);
        purchaseLine.setPurchaseHeader(purchaseHeader);
        purchaseLineRepository.save(purchaseLine);
        ModelAndView modelAndView =new ModelAndView("redirect:/web/purchase-request/"+purchaseHeader.getPurchaseHeaderId());

        return modelAndView;
    }

    @GetMapping("/purchase-line/edit/{purchaseHeaderId}/{purchaseLineId}")
    ModelAndView editPurchaseLine(@PathVariable Integer purchaseLineId, @PathVariable Integer purchaseHeaderId) {

        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-line/purchase-request-template");
        modelAndView.addObject("addPurchaseLine",true);
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        modelAndView.addObject("purchaseHeaderObject",purchaseHeader);

        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeader(purchaseHeader);
        modelAndView.addObject("purchaseLines",purchaseLines);

        PurchaseLineEntity purchaseLineEntity = purchaseLineRepository.findById(purchaseLineId).get();
        modelAndView.addObject("purchaseLineObject",purchaseLineEntity);
        return modelAndView;

    }
    @GetMapping("/purchase-line/delete/{purchaseHeaderId}/{purchaseLineId}")
    ModelAndView deletePurchaseLine(@PathVariable Integer purchaseLineId, @PathVariable Integer purchaseHeaderId) {

        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        ModelAndView modelAndView = new ModelAndView("redirect:/web/purchase-request/"+purchaseHeader.getPurchaseHeaderId());
        modelAndView.addObject("purchaseHeaderObject",purchaseHeader);
        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeader(purchaseHeader);
        modelAndView.addObject("purchaseLines",purchaseLines);
        PurchaseLineEntity purchaseLineEntity = purchaseLineRepository.findById(purchaseLineId).get();
        purchaseLineRepository.delete(purchaseLineEntity);
        return modelAndView;
    }

}
