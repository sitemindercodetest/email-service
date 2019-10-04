package com.siteminder.emailservice.model;

import com.siteminder.emailservice.builders.EmailRequestBuilder;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class EmailRequestValidationTest {
    private Validator validator;
    private EmailRequestBuilder builder = new EmailRequestBuilder();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void Should_Be_Valid() {
        EmailRequest validEmailRequest = builder.withFrom("t1@test.com").withTo(Arrays.asList("t2@test.com")).build();
        Set<ConstraintViolation<EmailRequest>> errors = validator.validate(validEmailRequest);

        assertEquals(errors.size(), 0);
    }

    @Test
    public void Should_Validate_FromIsNotEmpty() {
        EmailRequest invalidEmailRequest = builder.withFrom("").build();
        Set<ConstraintViolation<EmailRequest>> errors = validator.validate(invalidEmailRequest);

        assertEquals(errors.size(), 1);
    }

    @Test
    public void Should_Validate_FromIsValidEmail() {
        EmailRequest invalidEmailRequest = builder.withFrom("asd").build();
        Set<ConstraintViolation<EmailRequest>> errors = validator.validate(invalidEmailRequest);

        assertEquals(errors.size(), 1);
    }

    @Test
    public void Should_Validate_ToIsNotEmpty() {
        EmailRequest invalidEmailRequest = builder.withTo(Arrays.asList()).build();
        Set<ConstraintViolation<EmailRequest>> errors = validator.validate(invalidEmailRequest);

        assertEquals(errors.size(), 1);
    }

    @Test
    public void Should_Validate_ToContainsValidEmails() {
        EmailRequest invalidEmailRequest = builder.withTo(Arrays.asList("asd")).build();
        Set<ConstraintViolation<EmailRequest>> errors = validator.validate(invalidEmailRequest);

        assertEquals(errors.size(), 1);
    }

    @Test
    public void Should_Validate_CcContainsValidEmails() {
        EmailRequest invalidEmailRequest = builder.withCC(Arrays.asList("asd")).build();
        Set<ConstraintViolation<EmailRequest>> errors = validator.validate(invalidEmailRequest);

        assertEquals(errors.size(), 1);
    }

    @Test
    public void Should_Validate_BccContainsValidEmails() {
        EmailRequest invalidEmailRequest = builder.withBcc(Arrays.asList("asd")).build();
        Set<ConstraintViolation<EmailRequest>> errors = validator.validate(invalidEmailRequest);

        assertEquals(errors.size(), 1);
    }
}