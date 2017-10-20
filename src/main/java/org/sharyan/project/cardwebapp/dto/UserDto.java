package org.sharyan.project.cardwebapp.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserDto {

    @NotNull
    private String username;

    @NotNull
    @Length()
    private String password;
}
