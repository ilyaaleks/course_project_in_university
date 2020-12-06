package org.belstu.fakegram.FakeGram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPasswordUser {
    @NotNull(message = "Username can't be null")
    private String username;
    @NotNull(message = "Passwrod can't be null")
    private String password;
}
