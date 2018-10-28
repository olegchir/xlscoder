package com.xlscoder.controller;

import com.xlscoder.model.Role;
import com.xlscoder.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

@Controller
public class RolesController {
    @Autowired
    private RoleRepository roleRepository;

    @InitBinder(value="roles")
    protected void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, new RolePropertyEditor());
    }

    private class RolePropertyEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String partId) {
            final Role role = roleRepository.findById(Long.parseLong(partId)); // Get part based on the id
            setValue(role);
        }

        /**
         * This is called when checking if an option is selected
         */
        @Override
        public String getAsText() {
            return ((Role)getValue()).getId().toString(); // don'first forget null checking
        }
    }
}