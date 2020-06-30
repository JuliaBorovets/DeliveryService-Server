package ua.training.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.training.api.dto.UserDto;
import ua.training.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(AdminController.BASE_URL)
public class AdminController {

    public static final String BASE_URL = "/api/admin";

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers() {

        return userService.findAllUsers();
    }
}

