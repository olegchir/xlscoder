package com.xlscoder.controller;

import com.xlscoder.model.Role;
import com.xlscoder.model.User;
import com.xlscoder.repository.RoleRepository;
import com.xlscoder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

@Controller
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @InitBinder(value="users")
    protected void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(User.class, new UserPropertyEditor());
    }

    private class UserPropertyEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String partId) {
            final User role = userRepository.findById(Long.parseLong(partId));
            setValue(role);
        }

        @Override
        public String getAsText() {
            return ((User) getValue()).getId().toString();
        }
    }
}