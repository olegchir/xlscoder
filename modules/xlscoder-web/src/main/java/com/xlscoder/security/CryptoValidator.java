package com.xlscoder.security;

import com.xlscoder.dto.FileEncryptionRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CryptoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return FileEncryptionRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        FileEncryptionRequest rq = (FileEncryptionRequest) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "key", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "file", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "filter", "NotEmpty");

        if (null == rq.getFile() || rq.getFile().getSize() <= 0) {
            errors.rejectValue("file", "NotEmpty");
        }
    }
}