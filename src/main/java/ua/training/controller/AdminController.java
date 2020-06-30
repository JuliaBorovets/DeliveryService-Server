package ua.training.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.training.api.dto.StatisticsDto;
import ua.training.exception.OrderNotFoundException;
import ua.training.service.AdminService;

@Slf4j
@RestController
@RequestMapping(AdminController.BASE_URL)
public class AdminController {

    public static final String BASE_URL = "/api/admin";

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PatchMapping("/to_ship/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void shipOneOrder(@PathVariable Long id) throws OrderNotFoundException {

        adminService.shipOrder(id);
    }

    @PatchMapping("/to_deliver/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deliverOneOrder(@PathVariable Long id) throws OrderNotFoundException {

        adminService.deliverOrder(id);
    }

    @PatchMapping("/to_receive/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void receiveOneOrder(@PathVariable Long id) throws OrderNotFoundException {

        adminService.receiveOrder(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StatisticsDto createGeneralStatistics(){

        return adminService.createStatisticsDto();
    }
}

