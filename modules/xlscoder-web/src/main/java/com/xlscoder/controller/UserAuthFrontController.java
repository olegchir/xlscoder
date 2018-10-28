package com.xlscoder.controller;

import com.xlscoder.model.Role;
import com.xlscoder.model.User;
import com.xlscoder.security.RoleService;
import com.xlscoder.security.SecurityService;
import com.xlscoder.security.UserService;
import com.xlscoder.security.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserAuthFrontController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return "users/update";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "users/update";
        }

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findNoobRole());
        user.setRoles(userRoles);
        userService.save(user);

        securityService.autologin(user.getLogin(), user.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findLoggedInRoles());
        return "welcome";
    }
}
