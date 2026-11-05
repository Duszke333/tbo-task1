package com.example.thymeleaf.validation;

import com.example.thymeleaf.dto.CreateStudentDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SafeTextValidatorTest {

    private static Validator validator;
    private final SafeTextValidator safeTextValidator = new SafeTextValidator();

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"John Smith", "José da Silva", "Rua 7 de Setembro, 10/2", "São Paulo", "12345-678", "Apt. 4-B"})
    void acceptsPlainText(String value) {
        assertTrue(safeTextValidator.isValid(value, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "<img src=x onerror=alert('NAME')>",
            "<script>alert(1)</script>",
            "a > b",
            "say \"hi\"",
            "O'Brien",
            "Tom & Jerry",
            "line\nbreak",
            "\"<img src=x onerror=alert('EMAIL')>\"@x.com"
    })
    void rejectsHtmlSignificantAndControlCharacters(String value) {
        assertFalse(safeTextValidator.isValid(value, null));
    }

    @Test
    void validStudentHasNoViolations() {
        assertTrue(validator.validate(student("John Smith", "john@example.com")).isEmpty());
    }

    @Test
    void xssPayloadsInRenderedFieldsAreRejected() {
        CreateStudentDTO dto = student("<img src=x onerror=alert('NAME')>", "\"<img src=x onerror=alert('EMAIL')>\"@x.com");
        dto.setStreet("<script>alert(1)</script>");
        dto.setDistrict("<b>d</b>");
        dto.setCity("<i>c</i>");
        dto.setState("<u>s</u>");

        Set<String> invalidFields = validator.validate(dto).stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Object::toString)
                .collect(Collectors.toSet());

        assertEquals(Set.of("name", "email", "street", "district", "city", "state"), invalidFields);
    }

    private CreateStudentDTO student(String name, String email) {
        CreateStudentDTO dto = new CreateStudentDTO();
        dto.setName(name);
        dto.setEmail(email);
        dto.setBirthday(LocalDate.of(2000, 1, 1));
        dto.setZipCode("00000-000");
        dto.setStreet("Main Street");
        dto.setNumber("1");
        dto.setDistrict("Center");
        dto.setCity("Warsaw");
        dto.setState("MZ");
        return dto;
    }

}
