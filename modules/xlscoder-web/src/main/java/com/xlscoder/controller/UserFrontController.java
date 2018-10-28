package com.xlscoder.controller;

import com.xlscoder.model.User;
import com.xlscoder.repository.RoleRepository;
import com.xlscoder.repository.UserRepository;
import com.xlscoder.security.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserFrontController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(path = "/users/add", method = RequestMethod.GET)
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "users/update";
    }

    @RequestMapping(path = "users", method = RequestMethod.POST)
    @Transactional
    public String saveUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        model.addAttribute("allRoles", roleRepository.findAll());
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/update";
        }

        User userInDatabase = userRepository.findById(user.getId());
        userInDatabase.setLogin(user.getLogin());
        userInDatabase.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userInDatabase.setUserName(user.getUserName());
        userInDatabase.setUserSurname(user.getUserSurname());
        userInDatabase.setUserEmail(user.getUserEmail());
        userInDatabase.setRoles(user.getRoles());
        userRepository.save(userInDatabase);

        return "redirect:/users";
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @RequestMapping(path = "/users/edit/{id}", method = RequestMethod.GET)
    public String editUser(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("user", userRepository.findOne(id));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "users/update";
    }

    @RequestMapping(path = "/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userRepository.delete(id);
        return "redirect:/users";
    }
}
