package com.beta.limited.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }


    @RequestMapping("/error403")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
    return "login";
    }
}
