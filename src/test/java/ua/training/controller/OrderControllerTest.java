package ua.training.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.training.api.dto.OrderDto;
import ua.training.domain.user.Role;
import ua.training.domain.user.User;
import ua.training.service.OrderService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest extends AbstractRestControllerTest{

    @Mock
    OrderService orderService;

    @Mock
    Authentication mockPrincipal;

    @InjectMocks
    OrderController controller;

    MockMvc mockMvc;

    List<OrderDto> orderListDto;

    User user;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        OrderDto orderDto1 = OrderDto.builder().id(1L).build();
        OrderDto orderDto2 = OrderDto.builder().id(2L).build();
        OrderDto orderDto3 = OrderDto.builder().id(3L).build();

        orderListDto = Arrays.asList(orderDto1, orderDto2, orderDto3);

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
    void showAllOrders() throws Exception {

        when(orderService.findAllUserOrders(anyString())).thenReturn(orderListDto);
        when(mockPrincipal.getName()).thenReturn("login");

        mockMvc.perform(get(OrderController.BASE_URL + "/show/all")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orderListDto.size())));

        verify(orderService).findAllUserOrders(any());
    }

    @Test
    void showNotPaidOrders() throws Exception {

        when(orderService.findAllNotPaidUserOrders(anyString())).thenReturn(orderListDto);
        when(mockPrincipal.getName()).thenReturn("login");

        mockMvc.perform(get(OrderController.BASE_URL + "/show/not_paid")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orderListDto.size())));

        verify(orderService).findAllNotPaidUserOrders(anyString());
    }

    @Test
    void showDeliveredOrders() throws Exception {

        when(orderService.findAllDeliveredUserOrders(anyString())).thenReturn(orderListDto);
        when(mockPrincipal.getName()).thenReturn("login");

        mockMvc.perform(get(OrderController.BASE_URL + "/show/delivered")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orderListDto.size())));

        verify(orderService).findAllDeliveredUserOrders(anyString());
    }

    @Test
    void showArchivedOrders() throws Exception {

        when(orderService.findAllArchivedUserOrders(anyString())).thenReturn(orderListDto);
        when(mockPrincipal.getName()).thenReturn("login");

        mockMvc.perform(get(OrderController.BASE_URL + "/show/archived")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orderListDto.size())));

        verify(orderService).findAllArchivedUserOrders(anyString());
    }

    @Test
    void getAllUserOrders() throws Exception {

        when(orderService.findAllUserOrders(any())).thenReturn(orderListDto);
        when(mockPrincipal.getName()).thenReturn("login");

        mockMvc.perform(get(OrderController.BASE_URL + "/all_orders")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orderListDto.size())));

        verify(orderService).findAllUserOrders(any());
    }

    @Test
    void findOrderById() throws Exception {

        OrderDto orderDto = OrderDto.builder().id(1L).build();

        when(mockPrincipal.getName()).thenReturn("login");
        when(orderService.getOrderDtoByIdAndUserId(anyLong(), anyString())).thenReturn(orderDto);

        mockMvc.perform(get(OrderController.BASE_URL + "/find_order/1")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(orderService).getOrderDtoByIdAndUserId(anyLong(), anyString());
    }

    @Test
    void createNewOrder() throws Exception {
        when(mockPrincipal.getName()).thenReturn("login");

        OrderDto orderDto = OrderDto.builder()
                .id(2L)
                .description("description")
                .destinationCityFrom("destinationCityFrom")
                .destinationCityTo("to")
                .type("2")
                .weight(BigDecimal.valueOf(77)).build();

        when(orderService.createOrder(any(), any())).thenReturn(orderDto);

        mockMvc.perform(post(OrderController.BASE_URL)
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(orderDto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", equalTo(orderDto.getDescription())));

        verify(orderService).createOrder(any(), any());
    }

    @Test
    void archiveOrder() throws Exception {

        mockMvc.perform(patch(OrderController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(orderService).moveOrderToArchive(anyLong());
    }

    @Test
    void deleteOrder() throws Exception {
        mockMvc.perform(delete(OrderController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(orderService).deleteOrderById(anyLong());
    }

    @Test
    void findAllPaidOrders() throws Exception {

        when(orderService.findAllPaidOrdersDTO()).thenReturn(orderListDto);

        mockMvc.perform(get(OrderController.BASE_URL + "/paid")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orderListDto.size())));

        verify(orderService).findAllPaidOrdersDTO();
    }

    @Test
    void findAllShippedOrders() throws Exception {

        when(orderService.findAllShippedOrdersDTO()).thenReturn(orderListDto);

        mockMvc.perform(get(OrderController.BASE_URL + "/shipped")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orderListDto.size())));

        verify(orderService).findAllShippedOrdersDTO();
    }

    @Test
    void findAllDeliveredOrders() throws Exception {

        when(orderService.findAllDeliveredOrdersDto()).thenReturn(orderListDto);

        mockMvc.perform(get(OrderController.BASE_URL + "/delivered")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orderListDto.size())));

        verify(orderService).findAllDeliveredOrdersDto();
    }

}
