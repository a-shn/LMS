package com.company.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {
    @Size(min = 1)
    private String login;
    @Size(min = 1)
    @Email
    private String email;
    @Size(min = 1)
    private String password;
}
