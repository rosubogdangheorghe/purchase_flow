package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.AuthorityEntity;
import com.roki.purchase.entity.UserEntity;
import com.roki.purchase.repository.AuthorityRepository;
import com.roki.purchase.repository.UserRepository;
import com.roki.purchase.service.EmailBusinessService;
import com.roki.purchase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/web")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AuthorityController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private EmailBusinessService emailBusinessService;


    @GetMapping("/authority/edit/{userId}/{authorityId}")
    public ModelAndView addAuthority(@PathVariable Integer userId, @PathVariable Integer authorityId) {
        ModelAndView modelAndView = new ModelAndView("/dashboard/user/authority-form");
        UserEntity userEntity = userRepository.findById(userId).get();
        modelAndView.addObject("userObject",userEntity);
        List<String> authorityList = userService.getAuthorityList();

        modelAndView.addObject("authorityList",authorityList);
        AuthorityEntity authorityEntity = authorityRepository.findById(authorityId).get();
        modelAndView.addObject("authorityObject",authorityEntity);
        return modelAndView;
    }

    @GetMapping("/authority/add/{userId}")
    public ModelAndView addOtherAuthority(@PathVariable Integer userId){
        ModelAndView modelAndView = new ModelAndView("/dashboard/user/authority-form");
        UserEntity userEntity = userRepository.findById(userId).get();
        modelAndView.addObject("userObject",userEntity);
        List<String> authorityList = userService.getAuthorityList();
        modelAndView.addObject("authorityList",authorityList);

        AuthorityEntity authorityEntity =new AuthorityEntity();
        authorityEntity.setUsername(userEntity.getUsername());
        modelAndView.addObject("authorityObject",authorityEntity);

        return modelAndView;
    }



    @PostMapping("/authority/save")
    public ModelAndView saveAuthority(@ModelAttribute("authorityObject") AuthorityEntity authority) {
        UserEntity userEntity = userRepository.findByUsername(authority.getUsername());
        ModelAndView modelAndView = new ModelAndView("redirect:/web/user/list/"+userEntity.getUserId());

        authority.setUser(userEntity);
        authority = authorityRepository.save(authority);
        String action = "added to";
        emailBusinessService.userAuthorityChange(authority.getUsername(), authority.getAuthority(), userEntity.getEmail(),action,userEntity.getAuthorityList());

        return modelAndView;
    }
    @GetMapping("/authority/delete/{authorityId}")
    public ModelAndView deleteAuthority(@PathVariable Integer authorityId){
        AuthorityEntity authority = authorityRepository.findById(authorityId).get();
        UserEntity userEntity = userRepository.findByUsername(authority.getUsername());
        ModelAndView modelAndView = new ModelAndView("redirect:/web/user/list/"+userEntity.getUserId());

        authorityRepository.deleteById(authorityId);
        String action = "deleted from";
        emailBusinessService.userAuthorityChange(authority.getUsername(), authority.getAuthority(), userEntity.getEmail(),action,userEntity.getAuthorityList());
        return modelAndView;

    }
}
