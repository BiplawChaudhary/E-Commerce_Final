package com.ecommerce.aafrincosmetics.service.Others;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MiscService {

    //Function to check if the user is logged in or not
    //If the user is logged in return true, else return false
    public boolean isUserLoggedIn() {
        // Get the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        return authentication != null && authentication.isAuthenticated();
    }
}
