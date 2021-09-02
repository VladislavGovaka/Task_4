package com.Itransition.LaricSecurity.controllers;

import com.Itransition.LaricSecurity.helperСlasses.HelperUser;
import com.Itransition.LaricSecurity.entity.User;
import com.Itransition.LaricSecurity.repositories.UserRepository;
import com.Itransition.LaricSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class SpringController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public String greeting( Model model) {
        return "login";
    }

    @GetMapping("/main")
    public String main(Model model){

        User user = userRepository.findByUsername(userService.getAuthenticationUser().getUsername());


        if (!user.isBlock()){
            return "redirect:/login";
        }

        Iterable<User> users = userRepository.findAll();
        HelperUser helperUser = new HelperUser();
        model.addAttribute("users", users);
        model.addAttribute("helperUser", helperUser);
        return "main";
    }
    @GetMapping("/registration")
    public String newUser(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String create(@ModelAttribute("user") User user, Model model) {

        if (user.getPassword() != null) {
            model.addAttribute("passwordError", "Passwords are different!");
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/main";
    }

    @PostMapping(value = "/main", params = "action=delete")
    public String deleteForm(@ModelAttribute(value="foo") HelperUser helperUser) {
        List<User> checkedItems = helperUser.getCheckedItems();
        for(User user : checkedItems) {
            userRepository.deleteById(user.getId());
        }
        return "redirect:/main";
    }

    @PostMapping(value = "/main", params = "action=unblock")
    public String unblockForm(@ModelAttribute(value="foo") HelperUser helperUser) {

        List<User> checkedItems = helperUser.getCheckedItems();
        for (User u: checkedItems) {
            u.setBlock(true);
            userRepository.save(u);
        }
        return "redirect:/main";
    }

    @PostMapping(value = "/main", params = "action=block")
    public String blockForm(@ModelAttribute(value="foo") HelperUser helperUser) {
        List<User> checkedItems = helperUser.getCheckedItems();
        for (User u: checkedItems) {
            u.setBlock(false);
            userRepository.save(u);

        }
        return "redirect:/main";
    }
}