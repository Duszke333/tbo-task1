package com.example.thymeleaf.dto;

import com.example.thymeleaf.validation.SafeText;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class CreateStudentDTO {

    @NotEmpty(message = "{NotEmpty.name}")
    @SafeText(message = "{SafeText.name}")
    private String name;

    @Email
    @NotEmpty(message = "{NotEmpty.email}")
    @SafeText(message = "{SafeText.email}")
    private String email;

    @NotNull(message = "{NotNull.birthday}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotEmpty(message = "{NotEmpty.zipCode}")
    @SafeText(message = "{SafeText.zipCode}")
    private String zipCode;

    @NotEmpty(message = "{NotEmpty.street}")
    @SafeText(message = "{SafeText.street}")
    private String street;

    @NotEmpty(message = "{NotEmpty.number}")
    @SafeText(message = "{SafeText.number}")
    private String number;

    @SafeText(message = "{SafeText.complement}")
    private String complement;

    @NotEmpty(message = "{NotEmpty.district}")
    @SafeText(message = "{SafeText.district}")
    private String district;

    @NotEmpty(message = "{NotEmpty.city}")
    @SafeText(message = "{SafeText.city}")
    private String city;

    @NotEmpty(message = "{NotEmpty.state}")
    @SafeText(message = "{SafeText.state}")
    private String state;

}
