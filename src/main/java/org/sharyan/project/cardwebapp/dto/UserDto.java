package org.sharyan.project.cardwebapp.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


@Data
public class UserDto {

    @NotNull
    private String username;

    @NotNull
    @Length()
    private String password;
}
