package ua.training.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.training.api.dto.ReceiptDto;
import ua.training.domain.user.User;
import ua.training.exception.OrderCheckNotFoundException;
import ua.training.service.ReceiptService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(ReceiptController.BASE_URL)
public class ReceiptController {

    public static final String BASE_URL = "/api/receipt";

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReceiptDto> showAllUserCheck(@AuthenticationPrincipal User user){

        return receiptService.showChecksByUser(user.getId());
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ReceiptDto> showAllCheck(){

        return receiptService.showAllChecks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReceiptDto showCheck(@PathVariable Long id) throws OrderCheckNotFoundException {

        return receiptService.showCheckById(id);
    }
}
