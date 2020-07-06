package ua.training.service;

import ua.training.api.dto.BankCardDto;
import ua.training.api.dto.ReceiptDto;
import ua.training.exception.OrderCheckNotFoundException;
import ua.training.exception.OrderNotFoundException;

import java.util.List;

public interface ReceiptService {

    List<ReceiptDto> showAllChecks();

    ReceiptDto showCheckById(Long checkId) throws OrderCheckNotFoundException;

    List<ReceiptDto> showChecksByUser(String login);

    ReceiptDto createCheckDto(Long orderDtoId, BankCardDto bankCardDtoId, Long userId) throws OrderNotFoundException;
}
