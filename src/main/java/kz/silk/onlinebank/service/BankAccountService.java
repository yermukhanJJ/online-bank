package kz.silk.onlinebank.service;

import kz.silk.onlinebank.domain.dto.response.BankAccountDto;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.model.BankAccount;
import org.springframework.lang.NonNull;

import java.util.List;

public interface BankAccountService {

    BankAccount createAccount(double balance);

    List<BankAccountDto> getCurrentUserAccounts();

    BankAccount getAccountByNumber(@NonNull String accountNumber) throws BadRequestException;

    BankAccount getAccountByNumberAndProfile(@NonNull String accountNumber) throws NotFoundException;

}
