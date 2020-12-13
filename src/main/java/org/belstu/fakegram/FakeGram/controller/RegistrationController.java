package org.belstu.fakegram.FakeGram.controller;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.belstu.fakegram.FakeGram.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/registration")
@AllArgsConstructor
public class RegistrationController {
    public UserService userService;


    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@Valid @RequestBody UserDto user) {
        userService.register(user);
    }

    @GetMapping("/activate/{code}")
    @ResponseStatus(value = HttpStatus.OK)
    public void activate(@PathVariable String code) throws IOException {
        userService.activateUser(code);
    }

}
