package ua.training.service;

import ua.training.api.dto.OrderDto;
import ua.training.domain.order.Order;
import ua.training.domain.user.User;
import ua.training.exception.OrderCreateException;
import ua.training.exception.OrderNotFoundException;
import ua.training.exception.UserNotFoundException;

import java.util.List;

public interface OrderService {

    List<OrderDto> findAllUserOrders(Long userId);

    List<OrderDto> findAllNotPaidUserOrders(Long userId);

    List<OrderDto> findAllArchivedUserOrders(Long userId);

    List<OrderDto> findAllDeliveredUserOrders(Long userId);

    OrderDto createOrder(OrderDto orderDTO, User user) throws OrderCreateException, UserNotFoundException;

    OrderDto getOrderDtoById(Long id) throws OrderNotFoundException;

    OrderDto getOrderDtoByIdAndUserId(Long id, User userId) throws OrderNotFoundException;

    List<OrderDto> findAllPaidOrdersDTO();

    List<OrderDto> findAllShippedOrdersDTO();

    List<OrderDto> findAllDeliveredOrdersDto();

    Order findOrderById(Long orderId) throws OrderNotFoundException;

    OrderDto moveOrderToArchive(Long orderId) throws OrderNotFoundException;

    void deleteOrderById(Long orderId) throws OrderNotFoundException;

}
