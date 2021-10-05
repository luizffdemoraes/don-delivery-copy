package com.doncorleone.dondelivery.resources.exceptions;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

    private String REGEX_TELEPHONE = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})";

    @Override
    public void initialize(Telephone telephoneNumber) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext context) {
        return contactField.matches(REGEX_TELEPHONE);
    }


}
