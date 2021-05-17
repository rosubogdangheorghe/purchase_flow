package com.roki.purchase.component;


import com.roki.purchase.entity.UserEntity;
import com.roki.purchase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class PasswordExpirationFilter implements Filter {


    @Autowired
    private UserRepository userRepository;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if(isUrlExcluded(httpRequest)) {
            chain.doFilter(request,response);
            return;
        }
        Optional<User> user = getLoggedInUser();
        if(user.isPresent()) {
            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
            if(userEntity !=null && !userEntity.isCredentialsNonExpired()) {
                showChangePasswordPage(response, httpRequest,userEntity);
            } else {
                chain.doFilter(httpRequest,response);
            }
        } else {
            chain.doFilter(httpRequest,response);
        }

    }

    private boolean isUrlExcluded(HttpServletRequest httpServletRequest) throws IOException, ServletException  {

        String url = httpServletRequest.getRequestURL().toString();
        if(url.endsWith(".css") || url.endsWith(".png") || url.endsWith(".js") || url.endsWith("/change_password")) {
            return true;
        }
        return false;
    }

    private void showChangePasswordPage(ServletResponse response,
                                        HttpServletRequest httpRequest, UserEntity userEntity) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String redirectURL = httpRequest.getContextPath() + "/web/password/change_password";
        httpResponse.sendRedirect(redirectURL);
    }

    public static Optional<User> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication !=null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }



}
