package com.siteminder.emailservice.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailsValidator.class)
@Documented
public @interface Emails {
    String message() default "must be valid email addresses";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}