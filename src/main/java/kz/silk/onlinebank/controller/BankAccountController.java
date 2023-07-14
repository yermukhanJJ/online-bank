package kz.silk.onlinebank.controller;

import kz.silk.onlinebank.domain.dto.response.BankAccountDto;
import kz.silk.onlinebank.domain.model.BankAccount;
import kz.silk.onlinebank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(BankAccountController.CONTROLLER_PATH)
public class BankAccountController {

    public static final String CONTROLLER_PATH = "/account";

    private final BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<BankAccountDto> getAccount(@RequestParam(value = "number") String accountNumber) {
        return ResponseEntity.ok(BankAccountDto.fromEntity(bankAccountService.getAccountByNumberAndProfile(accountNumber)));
    }

    @PostMapping(value = "/new")
    public ResponseEntity<BankAccount> createAccount(@RequestParam(value = "balance", defaultValue = "0.0") double balance) {
        return ResponseEntity.ok(bankAccountService.createAccount(balance));
    }

    @GetMapping(value = "/my-accounts")
    public ResponseEntity<List<BankAccountDto>> getAccounts() {
        return ResponseEntity.ok(bankAccountService.getCurrentUserAccounts());
    }

}
