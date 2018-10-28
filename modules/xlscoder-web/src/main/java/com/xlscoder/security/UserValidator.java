package com.xlscoder.security;

import com.xlscoder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty");
        if (user.getLogin().length() < 3 || user.getLogin().length() > 32) {
            errors.rejectValue("login", "size");
        }

        User existingUser = userService.findByUsername(user.getLogin());
        if (existingUser != null && (!existingUser.getId().equals(user.getId()))) {
            errors.rejectValue("login", "duplicate");
        }

        if (!StringUtils.isEmptyOrWhitespace(user.getPassword())) {
            if (user.getPassword().length() < 3 || user.getPassword().length() > 32) {
                errors.rejectValue("password", "size");
            }

            if (!user.getPasswordConfirm().equals(user.getPassword())) {
                errors.rejectValue("passwordConfirm", "diff");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userSurname", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail", "NotEmpty");
    }
}