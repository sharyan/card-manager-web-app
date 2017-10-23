package org.sharyan.project.cardwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min=3, max=30)
    private String username;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min=6, max=30)
    private String password;
}
