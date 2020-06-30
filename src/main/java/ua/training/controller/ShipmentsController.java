package ua.training.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.training.api.dto.OrderDto;
import ua.training.domain.user.User;
import ua.training.exception.OrderCreateException;
import ua.training.exception.OrderNotFoundException;
import ua.training.exception.UserNotFoundException;
import ua.training.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(ShipmentsController.BASE_URL)
public class ShipmentsController {

    public static final String BASE_URL = "/api/shipments";

    private final OrderService orderService;

    public ShipmentsController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/show/{filter}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> showOrders(@PathVariable String filter, @AuthenticationPrincipal User user){

        //todo
        List<OrderDto> orderDtoList = new ArrayList<>();
        switch (filter){
            case "all":
                orderDtoList = orderService.findAllUserOrders(user.getId());
                break;
            case "not_paid":
                orderDtoList =  orderService.findAllNotPaidUserOrders(user.getId());
                break;
            case "delivered":
                orderDtoList =  orderService.findAllDeliveredUserOrders(user.getId());
                break;
            case "archived":
                orderDtoList =  orderService.findAllArchivedUserOrders(user.getId());
                break;
        }
        return orderDtoList;
    }

    @GetMapping("/all_orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllUserOrders(@AuthenticationPrincipal User user){

        //todo

        return orderService.findAllUserOrders(user.getId());
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto findOrderById(@PathVariable Long id) throws OrderNotFoundException {

        //todo

        return orderService.getOrderDtoById(id);
    }

    @GetMapping("/find_user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto findOrderByIdAndUserId(@PathVariable Long id, @AuthenticationPrincipal User user)
            throws OrderNotFoundException {

        //todo
        return orderService.getOrderDtoByIdAndUserId(id, user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createNewOrder(@RequestBody OrderDto orderDto, @AuthenticationPrincipal User user)
            throws OrderCreateException, UserNotFoundException {

        //todo

        return orderService.createOrder(orderDto, user);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto archiveOrder(@PathVariable Long id) throws OrderNotFoundException {

        //todo

        return orderService.moveOrderToArchive(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable Long id) throws OrderNotFoundException {

        //todo

        orderService.deleteOrderById(id);
    }

    @GetMapping("/paid")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllPaidOrders(){

        //todo

        return orderService.findAllPaidOrdersDTO();
    }

    @GetMapping("/shipped")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllShippedOrders(){

        //todo

        return orderService.findAllShippedOrdersDTO();
    }

    @GetMapping("/delivered")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllDeliveredOrders(){

        //todo

        return orderService.findAllDeliveredOrdersDto();
    }
}
