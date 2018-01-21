package com.mm.common.util;

import com.mm.common.exception.BusinessException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author tanwenhai@gusoftware.com
 */
public class ValidationUtils {
    public static void check(Errors errors) throws BusinessException {
        if (errors.hasErrors()) {
            FieldError fe = errors.getFieldError();
            String err = fe.getDefaultMessage();

            throw new BusinessException(err);
        }
    }

    public static <T> void validate(T object, Class<?>... groups) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
        if (validate.size() > 0) {
            throw new BusinessException(validate.iterator().next().getMessage());
        }
    }
}

