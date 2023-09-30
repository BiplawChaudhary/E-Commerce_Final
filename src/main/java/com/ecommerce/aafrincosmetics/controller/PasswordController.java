package com.ecommerce.aafrincosmetics.controller;


import com.ecommerce.aafrincosmetics.dto.request.NewPasswordRequestDto;
import com.ecommerce.aafrincosmetics.dto.request.OtpRequestDto;
import com.ecommerce.aafrincosmetics.entity.User;
import com.ecommerce.aafrincosmetics.repo.UserRepo;
import com.ecommerce.aafrincosmetics.service.Others.EmailService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.Others.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PasswordController {
    private final MiscService miscService;
    private final UserRepo userRepo;
    private final EmailService emailService;
    private final OTPService otpService;


    private String otp;
    private User foundUser;



//    Get the forgot password form
    @GetMapping("/forgot-password")
    public String getPasswordchangeForm(Model model,
                                        @ModelAttribute("emailNotFound") String emailNotFound){
        model.addAttribute("emailNotFound",emailNotFound);
        return "main/forgotPassword/forgotPassword";
    }


    @PostMapping("/forgot-password/check-email")
    public String checkEmail(@RequestParam("email") String email,
                             RedirectAttributes redirectAttributes){
        foundUser = userRepo.findByEmail(email);

        //If user is found on the database
        if(foundUser !=null){
            //Generate the random OTP
            otp = miscService.generateRandomOtp();
            //Stroe the OTP
            otpService.storeOTP(otp);

            //Send the OPT to user in email
            String subject = "Password Reset OTP";
            String message = "Dear "+  foundUser.getUsername()+ ",\n\n"
                    + "You have requested a password reset. Please use the following One-Time Password (OTP) to reset your password:\n\n"
                    + "OTP: " + otp + "\n\n"
                    + "If you did not request a password reset, please ignore this email.\n\n"
                    + "Best regards,\n"
                    + "Aafrin Cosmetics";

            emailService.sendEmail(email,subject,message);
            return "redirect:/forgot-password/otp-page";

        }else{ //If not found
            redirectAttributes.addAttribute("emailNotFound", "Email is not regiested.");
            return "redirect:/forgot-password";
        }
    }

    //    Get OPT Page
    @GetMapping("/forgot-password/otp-page")
    public String getOtpPage(Model model,
                             @ModelAttribute("otpMismatch") String otpMismatch,
                            @ModelAttribute("otpMismatch") String otpMismatch1){
//        Add a blank dto
        model.addAttribute("otp", new OtpRequestDto());
//        Generate a random opt and send it to user through email

        model.addAttribute("otpMismatch",otpMismatch);
        model.addAttribute("otpMismatch1",otpMismatch1);
        return "main/forgotPassword/otppage";
    }



    @PostMapping("/forgot-password/opt-submit")
    public String checkOtp(@ModelAttribute OtpRequestDto otpRequestDto,
                           RedirectAttributes redirectAttributes){

        if((otpService.isOTPValid(otpRequestDto.getOtp().trim())) &&
                (otpRequestDto.getOtp().trim().equals(otp))){
            return "redirect:/forgot-password/new-password-form";
        }
        else if(!otpService.isOTPValid(otpRequestDto.getOtp().trim())){
            redirectAttributes.addAttribute("otpMismatch", "OTP is expired.");
        }
        else if (!(otpRequestDto.getOtp().trim().equals(otp))){
            redirectAttributes.addAttribute("otpMismatch1", "The OTP is you entered is wrong..");
        }
        return "redirect:/forgot-password/otp-page";
    }

    @GetMapping("/forgot-password/new-password-form")
    public String newPasswordPage(Model model,
                                  @ModelAttribute("passwordMismatch")String passwordMismatch){
        model.addAttribute("newPassword", new NewPasswordRequestDto());
        model.addAttribute("passwordMismatch",passwordMismatch);
        return "main/forgotPassword/changepassword";
    }

    @PostMapping("/forgot-password/set-new-password")
    public String updateThePassword(@ModelAttribute("newPassword") NewPasswordRequestDto dto,
                                    RedirectAttributes redirectAttributes){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


//        Check if both the passwords match
        if(dto.getPassword1().trim().equals(dto.getPassword2())){

//            If the match save the new passowrd
            foundUser.setPassword(bCryptPasswordEncoder.encode(dto.getPassword1()));
            userRepo.save(foundUser);

            return "redirect:/login";
        }
//        If they don't match'
        redirectAttributes.addAttribute("passwordMismatch", "Passwords do not match");
        return "redirect:/forgot-password/new-password-form";
    }




}
