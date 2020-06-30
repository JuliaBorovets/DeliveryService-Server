package ua.training.service.impl;

import org.springframework.stereotype.Service;
import ua.training.api.dto.OrderDto;
import ua.training.domain.order.Order;
import ua.training.domain.user.User;
import ua.training.exception.OrderCreateException;
import ua.training.exception.OrderNotFoundException;
import ua.training.exception.UserNotFoundException;
import ua.training.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public List<OrderDto> findAllUserOrders(Long userId) {
        return null;
    }

    @Override
    public List<OrderDto> findAllNotPaidUserOrders(Long userId) {
        return null;
    }

    @Override
    public List<OrderDto> findAllArchivedUserOrders(Long userId) {
        return null;
    }

    @Override
    public List<OrderDto> findAllDeliveredUserOrders(Long userId) {
        return null;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDTO, User user) throws OrderCreateException, UserNotFoundException {
        return null;
    }

    @Override
    public OrderDto getOrderDtoById(Long id) throws OrderNotFoundException {
        return null;
    }

    @Override
    public OrderDto getOrderDtoByIdAndUserId(Long id, Long userId) throws OrderNotFoundException {
        return null;
    }

    @Override
    public List<OrderDto> findAllPaidOrdersDTO() {
        return null;
    }

    @Override
    public List<OrderDto> findAllShippedOrdersDTO() {
        return null;
    }

    @Override
    public List<OrderDto> findAllDeliveredOrdersDto() {
        return null;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderNotFoundException {
        return null;
    }

    @Override
    public OrderDto moveOrderToArchive(Long orderId) throws OrderNotFoundException {
        return null;
    }

    @Override
    public void deleteOrderById(Long orderId) throws OrderNotFoundException {

    }
}
