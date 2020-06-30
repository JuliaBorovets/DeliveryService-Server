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
import ua.training.api.dto.ReceiptDto;
import ua.training.domain.user.Role;
import ua.training.domain.user.User;
import ua.training.exception.ControllerExceptionHandler;
import ua.training.service.impl.ReceiptServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReceiptControllerTest extends AbstractRestControllerTest{

    @Mock
    ReceiptServiceImpl receiptService;

    @InjectMocks
    ReceiptController controller;

    MockMvc mockMvc;

    User user;

    List<ReceiptDto> receiptDtoList;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

        receiptDtoList = Arrays.asList(ReceiptDto.builder().build(), ReceiptDto.builder().build());

        user = User.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .login("loginnnnn")
                .role(Role.ROLE_ADMIN)
                .password("3848password")
                .email("email@g.dd").build();

    }

    @Test
    void showAllUserCheck() throws Exception {

        when(receiptService.showChecksByUser(anyLong())).thenReturn(receiptDtoList);

        mockMvc.perform(get(ReceiptController.BASE_URL)
                .flashAttr("user", user)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(receiptDtoList.size())));

        verify(receiptService).showChecksByUser(anyLong());
    }

    @Test
    void showAllCheck() throws Exception {

        when(receiptService.showAllChecks()).thenReturn(receiptDtoList);

        mockMvc.perform(get(ReceiptController.BASE_URL + "/all")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(receiptDtoList.size())));

        verify(receiptService).showAllChecks();
    }

    @Test
    void showCheck() throws Exception {

        when(receiptService.showCheckById(anyLong())).thenReturn(ReceiptDto.builder().id(1L).build());

        mockMvc.perform(get(ReceiptController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));

        verify(receiptService).showCheckById(anyLong());
    }
}
