package com.example.thymeleaf.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SafeTextValidator implements ConstraintValidator<SafeText, String> {

    private static final Pattern SAFE_TEXT = Pattern.compile("[^<>\"'&\\p{Cc}]*");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || SAFE_TEXT.matcher(value).matches();
    }
}
