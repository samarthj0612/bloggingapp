package com.samdb.bloggingapp.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;

    @NotEmpty
    @Size(min = 3, message = "Username must contains atleast 3 characters")
    private  String name;

    @Email(message = "Email address should be valid")
    private  String email;

    @NotEmpty
    @Size(min = 6, max = 12, message = "Password must contains 6-12 characters")
    private  String password;

    private  String about;
}
