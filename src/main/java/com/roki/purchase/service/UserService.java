package com.roki.purchase.service;

import com.roki.purchase.entity.UserEntity;
import com.roki.purchase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public static List<String> getAuthorityList() {
        List<String> authorityList = new ArrayList<>();
        authorityList.add("USER");
        authorityList.add("ADMIN");
        authorityList.add("ACCOUNTANT");
        authorityList.add("MANAGER");
        return authorityList;
    }

    public void changePassword(UserEntity user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setPasswordChangeTime(new Date());
        userRepository.save(user);
    }



}
