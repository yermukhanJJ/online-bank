package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.domain.dao.BankAccountRepository;
import kz.silk.onlinebank.domain.dto.response.BankAccountDto;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.InternalErrorException;
import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.model.BankAccount;
import kz.silk.onlinebank.domain.model.Profile;
import kz.silk.onlinebank.service.BankAccountService;
import kz.silk.onlinebank.service.ProfileService;
import kz.silk.onlinebank.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements {@link BankAccountService} service
 *
 * @author YermukhanJJ
 */
@RequiredArgsConstructor
@Slf4j
@Service(value = BankAccountServiceImpl.SERVICE_VALUE)
public class BankAccountServiceImpl implements BankAccountService {

    public static final String SERVICE_VALUE = "BankAccountServiceImpl";

    private final ProfileService profileService;
    private final BankAccountRepository bankAccountRepository;
    private final RedisService redisService;

    @Transactional
    @Override
    public BankAccount createAccount(double balance) {
        log.info("createAccount({})", balance);
        Profile profile = profileService.getCurrentProfile();
        BankAccount bankAccount = new BankAccount();
        String accountNumber = RandomStringUtils.randomNumeric(16);
        while (redisService.getAccountNumbers().contains(accountNumber)) {
            accountNumber = RandomStringUtils.randomNumeric(16);
        }
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setBalance(balance);
        bankAccount.setProfile(profile);

        BankAccount result;
        try {
            result = bankAccountRepository.save(bankAccount);
        } catch (Exception e) {
            throw new InternalErrorException("Something wrong");
        }

        redisService.getAccountNumbers().add(accountNumber);

        return result;
    }

    @Override
    public List<BankAccountDto> getCurrentUserAccounts() {
        log.info("getCurrentAccounts()");
        Profile profile = profileService.getCurrentProfile();
        List<BankAccount> bankAccounts = bankAccountRepository.findAllByProfile(profile);
        if (!bankAccounts.isEmpty()) {
            return bankAccounts.stream().map(BankAccountDto::fromEntity).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public BankAccount getAccountByNumber(@NonNull String accountNumber) throws BadRequestException {
        log.info("getAccountNumber({})", accountNumber);
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BadRequestException("Invalid account number"));
    }

    @Override
    public BankAccount getAccountByNumberAndProfile(@NonNull String accountNumber) throws BadRequestException {
        log.info("getAccountByNumberAndProfile({})", accountNumber);
        BankAccount bankAccount = bankAccountRepository.findByAccountNumberAndProfile(accountNumber, profileService.getCurrentProfile())
                .orElseThrow(() -> new NotFoundException(String.format("Account with number %s not found", accountNumber)));
        return bankAccount;
    }

}
