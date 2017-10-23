package org.sharyan.project.cardwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Username cannot be blank")
    @Size(min=3, max=30, message = "Username has to be between 3 and 30 characters")
    @Pattern(regexp = "[0-9A-Za-z]{3,30}", message = "Username must be made up of valid alphanumeric characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min=7, message = "Password must be greater than 6 characters")
    private String password;
}
