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
import ua.training.api.dto.StatisticsDto;
import ua.training.service.AdminService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    AdminService adminService;

    @InjectMocks
    AdminController controller;

    MockMvc mockMvc;

    final Long ID = 1L;


    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

    }

    @Test
    void shipOneOrder() throws Exception {

        mockMvc.perform(patch(AdminController.BASE_URL + "/to_ship/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(adminService).shipOrder(anyLong());
    }

    @Test
    void deliverOneOrder() throws Exception {

        mockMvc.perform(patch(AdminController.BASE_URL + "/to_deliver/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(adminService).deliverOrder(anyLong());
    }

    @Test
    void receiveOneOrder() throws Exception {

        mockMvc.perform(patch(AdminController.BASE_URL + "/to_receive/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(adminService).receiveOrder(anyLong());
    }

    @Test
    void createGeneralStatistics() throws Exception {

        when(adminService.createStatisticsDto()).thenReturn(StatisticsDto.builder().build());

        mockMvc.perform(get(AdminController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(adminService).createStatisticsDto();
    }
}
