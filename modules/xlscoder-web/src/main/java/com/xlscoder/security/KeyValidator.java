package com.xlscoder.security;

import com.xlscoder.model.Key;
import com.xlscoder.repository.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class KeyValidator implements Validator {
    @Autowired
    private KeyRepository keyRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return Key.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Key key = (Key) o;

        Key existingKey = keyRepository.findByKeyName(key.getKeyName());
        if (existingKey != null && (!existingKey.getId().equals(key.getId()))) {
            errors.rejectValue("keyName", "duplicate");
        }
        ValidationUtils.rejectIfEmpty(errors, "keyName", "NotEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "privateKey", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pgpPrivateKey", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pgpPublicKey", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pgpPassword", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pgpIdentity", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shaSalt", "NotEmpty");
    }
}