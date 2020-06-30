package ua.training.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.training.api.dto.UserDto;
import ua.training.exception.ControllerExceptionHandler;
import ua.training.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest extends AbstractRestControllerTest{

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

    }

    @Test
    void register() throws Exception {

        UserDto userDto = UserDto.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .login("loginnnnn")
                .password("3848password")
                .email("email@g.dd").build();

        mockMvc.perform(post(UserController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto))
        )
                .andExpect(status().isCreated());

        verify(userService).saveNewUserDto(any(UserDto.class));
    }

    @Test
    void login() throws Exception {

        mockMvc.perform(get(UserController.BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

    }

    @Test
    void changeRole() throws Exception {

        mockMvc.perform(patch(UserController.BASE_URL + "/change/1/ROLE_ADMIN")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(userService).changeRole(anyLong(), any());

    }

    @Test
    void findUserByLogin() throws Exception {

        List<UserDto> userListDto = Arrays.asList(
                UserDto.builder().login("login1").build(),
                UserDto.builder().login("login2").build());

        when(userService.findAllByLoginLike(any())).thenReturn(userListDto);

        UserDto userDto = UserDto.builder().login("find").build();

        mockMvc.perform(get(UserController.BASE_URL + "/find")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto))
        )
               // .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(userListDto.size())));

        verify(userService).findAllByLoginLike(any());
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserDto> userListDto = Arrays.asList(new UserDto(), new UserDto());
        when(userService.findAllUsers()).thenReturn(userListDto);

        mockMvc.perform(get(UserController.BASE_URL + "/all")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(userListDto.size())));

        verify(userService).findAllUsers();
    }
}
