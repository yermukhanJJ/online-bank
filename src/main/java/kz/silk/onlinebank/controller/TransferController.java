package kz.silk.onlinebank.controller;

import kz.silk.onlinebank.domain.dto.request.TransferDto;
import kz.silk.onlinebank.service.MoneyTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(TransferController.CONTROLLER_PATH)
public class TransferController {

    public static final String CONTROLLER_PATH = "/transfer";

    private final MoneyTransferService moneyTransferService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferDto transferDto) {
        moneyTransferService.transfer(transferDto);
        return ResponseEntity.ok().build();
    }
}
