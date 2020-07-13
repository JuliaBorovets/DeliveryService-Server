package ua.training.service.impl;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.training.api.dto.OrderDto;
import ua.training.api.mapper.OrderMapper;
import ua.training.domain.order.Order;
import ua.training.domain.order.Status;
import ua.training.domain.user.User;
import ua.training.exception.OrderNotFoundException;
import ua.training.repository.OrderRepository;
import ua.training.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    OrderMapper orderMapper;

    @Mock
    OrderTypeServiceImpl orderTypeService;

    @Mock
    DestinationServiceImpl destinationService;

    BigDecimal BASE_PRICE = BigDecimal.valueOf(6);

    BigDecimal WEIGHT_COEFFICIENT = BigDecimal.valueOf(7);

    @InjectMocks
    OrderServiceImpl service;

    @Ignore
    @Test
    void findAllUserOrders() {

        List<Order> orderList = Arrays.asList(
                Order.builder().status(Status.NOT_PAID).build(),
                Order.builder().status(Status.NOT_PAID).build()
        );

        when(orderRepository.findOrderByOwner_Login(anyString()))
                .thenReturn(orderList);

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(OrderDto.builder().status(Status.NOT_PAID).build());

        List<OrderDto> result = service.findAllUserOrders("login");

        assertEquals(result.size(), orderList.size());
        verify(orderRepository).findOrderByOwner_Login(anyString());
        verify(orderMapper, times(orderList.size())).orderToOrderDto(any(Order.class));
    }

    @Test
    void findAllNotPaidUserOrders() {
        List<Order> orderList = Arrays.asList(
                Order.builder().status(Status.NOT_PAID).build(),
                Order.builder().status(Status.NOT_PAID).build()
        );

        when(orderRepository.findByStatusAndOwner_Login(any(Status.class), anyString()))
                .thenReturn(orderList);

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(OrderDto.builder().status(Status.NOT_PAID).build());

        List<OrderDto> result = service.findAllNotPaidUserOrders("login");

        assertEquals(result.size(), orderList.size());
        verify(orderRepository).findByStatusAndOwner_Login(any(Status.class), anyString());
        verify(orderMapper, times(orderList.size())).orderToOrderDto(any(Order.class));
    }

    @Test
    void findAllArchivedUserOrders() {
        final Long ID = 1L;
        User user = User.builder().id(ID).build();

        List<Order> orderList = Arrays.asList(
                Order.builder().status(Status.ARCHIVED).owner(user).build(),
                Order.builder().status(Status.ARCHIVED).owner(user).build()
        );

        when(orderRepository.findByStatusAndOwner_Login(any(Status.class), anyString()))
                .thenReturn(orderList);

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(OrderDto.builder().status(Status.ARCHIVED).build());

        List<OrderDto> result = service.findAllArchivedUserOrders("login");

        assertEquals(result.size(), orderList.size());
        verify(orderRepository).findByStatusAndOwner_Login(any(Status.class), anyString());
        verify(orderMapper, times(orderList.size())).orderToOrderDto(any(Order.class));
    }

    @Test
    void findAllDeliveredUserOrders() {
        final Long ID = 1L;
        User user = User.builder().id(ID).build();

        List<Order> orderList = Arrays.asList(
                Order.builder().status(Status.DELIVERED).owner(user).build(),
                Order.builder().status(Status.DELIVERED).owner(user).build()
        );

        when(orderRepository.findByStatusAndOwner_Login(any(Status.class), anyString()))
                .thenReturn(orderList);

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(OrderDto.builder().status(Status.DELIVERED).build());

        List<OrderDto> result = service.findAllDeliveredUserOrders("login");

        assertEquals(result.size(), orderList.size());
        verify(orderRepository).findByStatusAndOwner_Login(any(Status.class), anyString());
        verify(orderMapper, times(orderList.size())).orderToOrderDto(any(Order.class));
    }

    @Test
    void findAllPaidOrdersDTO() {

        List<Order> orderList = Arrays.asList(
                Order.builder().status(Status.PAID).build(),
                Order.builder().status(Status.PAID).build()
        );

        when(orderRepository.findOrderByStatus(any(Status.class)))
                .thenReturn(orderList);

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(OrderDto.builder().status(Status.PAID).build());

        List<OrderDto> result = service.findAllPaidOrdersDTO();

        assertEquals(result.size(), orderList.size());
        verify(orderRepository).findOrderByStatus(any(Status.class));
        verify(orderMapper, times(orderList.size())).orderToOrderDto(any(Order.class));
    }

    @Test
    void findAllShippedOrdersDTO() {
        List<Order> orderList = Arrays.asList(
                Order.builder().status(Status.SHIPPED).build(),
                Order.builder().status(Status.SHIPPED).build()
        );

        when(orderRepository.findOrderByStatus(any(Status.class)))
                .thenReturn(orderList);

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(OrderDto.builder().status(Status.SHIPPED).build());

        List<OrderDto> result = service.findAllShippedOrdersDTO();

        assertEquals(result.size(), orderList.size());
        verify(orderRepository).findOrderByStatus(any(Status.class));
        verify(orderMapper, times(orderList.size())).orderToOrderDto(any(Order.class));
    }

    @Test
    void findAllDeliveredOrdersDto() {
        List<Order> orderList = Arrays.asList(
                Order.builder().status(Status.DELIVERED).build(),
                Order.builder().status(Status.DELIVERED).build()
        );

        when(orderRepository.findOrderByStatus(any(Status.class)))
                .thenReturn(orderList);

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(OrderDto.builder().status(Status.DELIVERED).build());

        List<OrderDto> result = service.findAllDeliveredOrdersDto();

        assertEquals(result.size(), orderList.size());
        verify(orderRepository).findOrderByStatus(any(Status.class));
        verify(orderMapper, times(orderList.size())).orderToOrderDto(any(Order.class));
    }

    @Test
    void getOrderDtoById() throws OrderNotFoundException {

        final Long ID = 1L;

        Order order = Order.builder().id(ID).build();
        OrderDto orderDto = OrderDto.builder().id(ID).build();

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(orderDto);

        OrderDto result = service.getOrderDtoById(ID);
        assertEquals(ID, result.getId());
        verify(orderRepository).findById(anyLong());
        verify(orderMapper).orderToOrderDto(any(Order.class));
    }

    @Test
    void getOrderDtoByIdAndUserId() throws OrderNotFoundException {

        final Long ID = 1L;
        final Long USER_ID = 2L;
        final String LOGIN = "login";

        User user = User.builder().id(USER_ID).login(LOGIN).build();

        Order order = Order.builder().id(ID).owner(user).build();
        OrderDto orderDto = OrderDto.builder().id(ID).build();

        when(orderRepository.findByIdAndOwner_Login(anyLong(), anyString()))
                .thenReturn(Optional.of(order));

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(orderDto);

        OrderDto result = service.getOrderDtoByIdAndUserId(ID, LOGIN);
        assertEquals(ID, result.getId());
        verify(orderRepository).findByIdAndOwner_Login(anyLong(), anyString());
        verify(orderMapper).orderToOrderDto(any(Order.class));
    }

    @Test
    void findOrderById() throws OrderNotFoundException {

        final Long ID = 1L;

        Order order = Order.builder().id(ID).build();

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        Order result = service.findOrderById(ID);

        assertEquals(ID, result.getId());
        verify(orderRepository).findById(anyLong());
    }

    @Test
    void moveOrderToArchive() throws OrderNotFoundException {
        final Long ID = 1L;

        Order order = Order.builder().id(ID).status(Status.RECEIVED).build();
        OrderDto orderDto = OrderDto.builder().id(order.getId()).status(Status.ARCHIVED).build();

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        when(orderMapper.orderToOrderDto(any(Order.class)))
                .thenReturn(orderDto);

        OrderDto result = service.moveOrderToArchive(ID);

        assertEquals(ID, result.getId());
        verify(orderRepository).findById(anyLong());
        verify(orderRepository).save(order);
        assertEquals(Status.ARCHIVED, result.getStatus());

    }

    @Test
    void deleteOrderById() throws OrderNotFoundException {

        final Long ID = 1L;

        Order order = Order.builder().id(ID).status(Status.NOT_PAID).build();

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        service.deleteOrderById(ID);
        verify(orderRepository).delete(any(Order.class));
    }

    @Test
    void deleteOrderByIdPaid() throws OrderNotFoundException {

        final Long ID = 1L;

        Order order = Order.builder().id(ID).status(Status.PAID).build();

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        service.deleteOrderById(ID);
        verify(orderRepository).findById(anyLong());
        verify(orderRepository, never()).deleteById(anyLong());
    }

    @Test
    void orderFindException() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> {
                    service.findOrderById(1L);
                });

        verify(orderRepository).findById(anyLong());
    }
}
