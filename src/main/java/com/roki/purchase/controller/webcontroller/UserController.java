package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.UserEntity;
import com.roki.purchase.repository.UserRepository;
import com.roki.purchase.service.EmailBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailBusinessService emailBusinessService;

    @GetMapping("/user/list")
    public ModelAndView getAllUsers(){
        ModelAndView modelAndView = new ModelAndView("/dashboard/user/users");
        List<UserEntity> userEntityList = userRepository.findAll();

        modelAndView.addObject("usersList",userRepository.findAll());
        for (UserEntity userEntity:userEntityList) {
            modelAndView.addObject("userAuthorities",userEntity.getAuthorityList());
        }

        return modelAndView;
    }

    @GetMapping("/user/list/{userId}")
    public ModelAndView getOneUser(@PathVariable Integer userId) {
        ModelAndView modelAndView = new ModelAndView("/dashboard/user/user-details");
        UserEntity userEntity = userRepository.findById(userId).get();
        modelAndView.addObject("userDetails",userEntity);
        modelAndView.addObject("userAuthorities",userEntity.getAuthorityList());
        return modelAndView;

    }
    

    @GetMapping("/user/add")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView("/dashboard/user/user-form");
        modelAndView.addObject("userObject", new UserEntity());

        return modelAndView;
    }

    @PostMapping("/user/save")
    public ModelAndView saveUser(@ModelAttribute("userObject") UserEntity user){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/user/list");
        user.setEnabled(true);
        Boolean isNewUser = user.getUserId() == null?true:false;
        if(isNewUser) {
            user.setPassword(passwordEncoder.encode("init"));
//            user.setPasswordChangeTime(new Date());
        } else {
            UserEntity oldUSer = userRepository.getOne(user.getUserId());
            user.setPassword(oldUSer.getPassword());
        }

        user = userRepository.save(user);
        emailBusinessService.userCreationEmail("init", user.getUsername(), user.getEmail());
        return modelAndView;
    }

    @GetMapping("/user/edit/{userId}")
    public ModelAndView editUser(@PathVariable Integer userId) {
        ModelAndView modelAndView = new ModelAndView("/dashboard/user/user-form");
        modelAndView.addObject("userObject",userRepository.findById(userId).get());
        return modelAndView;
    }

    @GetMapping("/user/disable/{userId}")
    public ModelAndView disableUser(@PathVariable Integer userId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/user/list");
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isPresent()) {
            user.get().setEnabled(false);
            userRepository.save(user.get());
        }
    return modelAndView;
    }

    @GetMapping("/user/enable/{userId}")
    public ModelAndView enableUser(@PathVariable Integer userId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/user/list");
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setEnabled(true);
            userRepository.save(user.get());
        }
        return modelAndView;
    }
}
