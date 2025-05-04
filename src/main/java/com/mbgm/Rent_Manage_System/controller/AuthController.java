package com.mbgm.Rent_Manage_System.controller;

import com.mbgm.Rent_Manage_System.entity.User;
import com.mbgm.Rent_Manage_System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {


        private final UserService userService;

        @GetMapping("/login")
        public String login() {
            return "login";
        }

        @GetMapping("/register")
        public String showRegisterForm(Model model) {
            model.addAttribute("user", new User());
            return "register";
        }

        @PostMapping("/register")
        public String register(@ModelAttribute User user, Model model) {
            try {
                userService.register(user);
                return "redirect:/login?success";
            } catch (RuntimeException e) {
                model.addAttribute("error", e.getMessage());
                return "register";
            }
        }

        @GetMapping("/home")
        public String home() {
            return "home";
        }
}

