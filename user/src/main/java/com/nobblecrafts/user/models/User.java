package com.nobblecrafts.user.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@With
public class User {

    @Id
    private String id;

    @NotNull(message = "Username is null")
    @NotEmpty(message = "Username is empty")
    private String name;

    @NotNull(message = "email is null")
    @NotEmpty(message = "email is empty")
    @Email(message = "E-mail is invalid")
    private String email;

}