package ua.training.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.training.api.dto.UserDto;
import ua.training.domain.Role;
import ua.training.domain.User;
import ua.training.exception.RegException;
import ua.training.exception.UserAlreadyExistsException;
import ua.training.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/api/user";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody @Valid UserDto user) throws RegException {

        if (userService.findByLogin(user.getLogin()) != null) {
            //Username should be unique.
            throw new UserAlreadyExistsException();
        }

        return userService.saveNewUserDto(user);
    }


    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User login(HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        if (principal == null || principal.getName() == null) {
            return new User();
        }

        return userService.findByLogin(principal.getName());
    }

    @PatchMapping("/change/{id}/{role}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto changeRole(@PathVariable Long id, @PathVariable Role role) {
        return userService.changeRole(id, role);
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findUserByLogin(@RequestBody UserDto userDto){

        return userService.findAllByLoginLike(userDto.getLogin());
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers() {

        return userService.findAllUsers();
    }
}
