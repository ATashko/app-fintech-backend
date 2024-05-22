package com.msuser.msuser.dto;

import com.msuser.msuser.util.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.Set;


@Builder
public record UserRegistrationDTO(

        @NotBlank(message = "Username is mandatory")
        @Length(min = 3,max = 15,message = "username must have between 3 and 15 characters")
        String username,

        @Email(flags = Pattern.Flag.CASE_INSENSITIVE)
        String email,

        @NotBlank(message = "first name is mandatory")
        @Length(min = 3,max = 25,message = "first name must have between 3 and 35 characters")
        String firstName,

        @NotBlank(message = "last name is mandatory")
        @Length(min = 3,max = 25,message = "last name must have between 3 and 35 characters")
        String lastName,

        @NotNull
        @ValidPassword
        String pass,

        @Size(min = 1, max = 5)
        Set<String> roles) {


}
