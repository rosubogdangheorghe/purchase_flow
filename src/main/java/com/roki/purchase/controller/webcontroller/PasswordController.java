package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.UserEntity;
import com.roki.purchase.repository.UserRepository;
import com.roki.purchase.service.EmailBusinessService;
import com.roki.purchase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/web")
public class PasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailBusinessService emailBusinessService;

    @GetMapping("/password/change_password")
    public ModelAndView showChangePasswordForm() {
        ModelAndView modelAndView = new ModelAndView("/dashboard/user/password-change-form");

            modelAndView.addObject("message","Please change the expired password");
        return modelAndView;
    }

//    @PostMapping("/password/change_password")
//    public ModelAndView changePassword( HttpServletRequest request,
//                                       HttpServletResponse response, RedirectAttributes attributes
//                                       ) throws ServletException {
//
//        ModelAndView modelAndView = new ModelAndView();
//
//        Optional<User> user = PasswordExpirationFilter.getLoggedInUser();
//        if(user.isPresent()) {
//            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
//            modelAndView.addObject("passwordObject", userEntity);
//            System.out.println(userEntity);
//            System.out.println("din methoda save");
//            String oldPassword = request.getParameter("oldPassword");
//            String newPassword = request.getParameter("newPassword");
//            System.out.println(oldPassword);
//            System.out.println(newPassword);
//            if (oldPassword.equals(newPassword)) {
//                modelAndView.addObject("message", "Your new password must be different than the old one.");
//                modelAndView.addObject("/dashboard/user/password-change-form");
//                return modelAndView;
//            }
//            if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
//                modelAndView.addObject("message", "Your old password is incorrect.");
//                modelAndView.addObject("/dashboard/user/password-change-form");
//                return modelAndView;
//            } else {
//                newPassword = passwordEncoder.encode(newPassword);
//                userEntity.setPassword(newPassword);
//                userRepository.save(userEntity);
////            userService.changePassword(userEntity,newPassword);
//                request.logout();
//                attributes.addFlashAttribute("message", "You have changed your password successfully. Please login again");
//                return modelAndView.addObject("redirect:/web/login-form");
//            }
//
//        }
//        return modelAndView.addObject("redirect:/web/login-form");
//    }
//    @GetMapping("/password/change_password")
//    public String showChangePasswordForm(Model model) {
//        model.addAttribute("pageTitle", "Change Expired Password");
//        return "/dashboard/user/password-change-form";
//    }


    @PostMapping("/password/change_password")
    public String processChangePassword(Model model, HttpServletRequest request,
                                        HttpServletResponse response, RedirectAttributes attributes) throws ServletException {
        Optional<User> user = userService.getLoggedInUser();
        if(user.isPresent()) {
            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            model.addAttribute("pageTitle", "Change Expired Password");
            if (oldPassword.equals(newPassword)) {
                model.addAttribute("message", "Your new password must be different than the old one.");

                return "/dashboard/user/password-change-form";
            }

            if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
                model.addAttribute("message", "Your old password is incorrect.");
                return "/dashboard/user/password-change-form";

            } else {
                userService.changePassword(userEntity, newPassword);
                request.logout();
                attributes.addFlashAttribute("message", "You have changed your password successfully. "
                        + "Please login again.");

                return "redirect:/web/login-form";
            }
        }
        return "redirect:/web/login-form";
    }
    @GetMapping("/password/reset/{userId}")
    public ModelAndView resetPassword(@PathVariable Integer userId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/user/list");
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            userEntity.get().setPassword(passwordEncoder.encode("initinit2021"));
            userEntity.get().setPasswordChangeTime(null);
            emailBusinessService.passwordResetEmail("initinit2021",userEntity.get().getUsername(),userEntity.get().getEmail());
            userRepository.save(userEntity.get());

        }
        return modelAndView;
    }




}
