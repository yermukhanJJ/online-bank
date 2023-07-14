package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.domain.dao.BankAccountRepository;
import kz.silk.onlinebank.domain.dto.request.TransferDto;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.model.BankAccount;
import kz.silk.onlinebank.service.BankAccountService;
import kz.silk.onlinebank.service.MoneyTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implements of {@link MoneyTransferService}
 *
 * @author YermukhanJJ
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class MoneyTransferServiceImpl implements MoneyTransferService {

    private final BankAccountService bankAccountService;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    @Override
    public void transfer(@NonNull TransferDto transferDto) throws BadRequestException {

        BankAccount toAccount = bankAccountService.getAccountByNumber(transferDto.getToAccount());
        BankAccount fromAccount = bankAccountService.getAccountByNumberAndProfile(transferDto.getFromAccount());

        if (transferDto.getFromAccount().equals(transferDto.getToAccount())) {
            throw new BadRequestException("It is not possible to make a transfer between the same accounts");
        } else {
            if (isSufficientSum(transferDto.getSum(), fromAccount.getBalance())) {
                toAccount.setBalance(toAccount.getBalance() + transferDto.getSum());
                fromAccount.setBalance(fromAccount.getBalance() - transferDto.getSum());
            } else {
                throw new BadRequestException("Insufficient amount of money");
            }
        }

        bankAccountRepository.save(toAccount);
        bankAccountRepository.save(fromAccount);

    }

    private boolean isSufficientSum(double sum, double balance) {
        return !(sum > balance);
    }
}
