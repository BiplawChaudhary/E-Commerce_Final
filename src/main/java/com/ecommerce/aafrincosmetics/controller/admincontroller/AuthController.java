package com.ecommerce.aafrincosmetics.controller.admincontroller;


import com.ecommerce.aafrincosmetics.dto.request.UserRequestDto;
import com.ecommerce.aafrincosmetics.service.Others.EmailService;
import com.ecommerce.aafrincosmetics.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/api/auth/v1/")
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/login")
    public String getLogInPage(){
        return "auth/Login";
    }

    @GetMapping("/sign-up")
    public String getSignUp(Model model, @ModelAttribute("errorMsg") String errorMsg){
        model.addAttribute("dto", new UserRequestDto());
        model.addAttribute("errorMsg", errorMsg);
        return "auth/signUp";
    }

    @PostMapping("/sign-up")
    public String signUpData(Model model, RedirectAttributes redirectAttributes, @ModelAttribute UserRequestDto userRequestDto){
        try{
            userService.saveUserToTable(userRequestDto);

//            Sending the email after successful sign up

            String subject = "Welcome to Aafrin Cosmetics.";

            String message = "Thank you for signing up. We are excited to have you as part of us.\n" +
                    "If you have any questions or need assistance, feel free to contact our support team.\n"+
                    "🎉🎉 Happy Shopping 🎉🎉";

            emailService.sendEmail(userRequestDto.getEmail(), subject, message);


        }catch (DataIntegrityViolationException e){
            redirectAttributes.addAttribute("errorMsg", "Username Taken.");
            return "redirect:/sign-up";
        }
        return "redirect:/login";
    }

}
