package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.domain.dao.BankAccountRepository;
import kz.silk.onlinebank.domain.dto.request.TransferDto;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.model.BankAccount;
import kz.silk.onlinebank.domain.model.Profile;
import kz.silk.onlinebank.domain.model.Role;
import kz.silk.onlinebank.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoneyTransferServiceImplTest {

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private MoneyTransferServiceImpl moneyTransferService;

    //Корректный сценарий
    @Test
    void transfer() {
        BankAccount toAccount = toAccount();
        BankAccount fromAccount = fromAccount();
        double toAccountSum = toAccount.getBalance();
        double fromAccountSum = fromAccount.getBalance();

        TransferDto transferDto = new TransferDto();
        transferDto.setSum(200);
        transferDto.setFromAccount(fromAccount.getAccountNumber());
        transferDto.setToAccount(toAccount.getAccountNumber());

        when(bankAccountService.getAccountByNumber(any())).thenReturn(toAccount);
        when(bankAccountService.getAccountByNumberAndProfile(any())).thenReturn(fromAccount);
        when(bankAccountRepository.save(toAccount)).thenReturn(toAccount);
        when(bankAccountRepository.save(fromAccount)).thenReturn(fromAccount);

        moneyTransferService.transfer(transferDto);

        verify(bankAccountService, times(1)).getAccountByNumber(any());
        verify(bankAccountService, times(1)).getAccountByNumberAndProfile(any());
        verify(bankAccountRepository, times(2)).save(any());

        assertThat(toAccount.getBalance() - transferDto.getSum() == toAccountSum)
                .isTrue();
        assertThat(fromAccount.getBalance() + transferDto.getSum() == fromAccountSum)
                .isTrue();

    }

    //Сценарий если перевод денег на один и тот же счет
    @Test
    void transferThrowBadRequestExceptionWithMessageSameAcc() {
        BankAccount toAccount = toAccount();
        BankAccount fromAccount = toAccount();

        TransferDto transferDto = new TransferDto();
        transferDto.setSum(200);
        transferDto.setFromAccount(fromAccount.getAccountNumber());
        transferDto.setToAccount(toAccount.getAccountNumber());

        when(bankAccountService.getAccountByNumber(any())).thenReturn(toAccount);
        when(bankAccountService.getAccountByNumberAndProfile(any())).thenReturn(fromAccount);

        assertThat(Objects.equals(assertThrows(BadRequestException.class, () -> moneyTransferService.transfer(transferDto))
                .getMessage(),
                new BadRequestException("It is not possible to make a transfer between the same accounts").getMessage()))
                .isTrue();
        verify(bankAccountService, times(1)).getAccountByNumber(any());
        verify(bankAccountService, times(1)).getAccountByNumberAndProfile(any());
    }

    //Сценарий если на счету не достаточна средств
    @Test
    void transferThrowsBadRequestExceptionInsufficientAmount() {
        BankAccount toAccount = toAccount();
        BankAccount fromAccount = fromAccount();

        TransferDto transferDto = new TransferDto();
        transferDto.setSum(600);
        transferDto.setFromAccount(fromAccount.getAccountNumber());
        transferDto.setToAccount(toAccount.getAccountNumber());

        when(bankAccountService.getAccountByNumber(any())).thenReturn(toAccount);
        when(bankAccountService.getAccountByNumberAndProfile(any())).thenReturn(fromAccount);

        assertThat(Objects.equals(assertThrows(BadRequestException.class, () -> moneyTransferService.transfer(transferDto))
                        .getMessage(),
                new BadRequestException("Insufficient amount of money").getMessage()))
                .isTrue();
        verify(bankAccountService, times(1)).getAccountByNumber(any());
        verify(bankAccountService, times(1)).getAccountByNumberAndProfile(any());
    }

    private BankAccount fromAccount() {
        BankAccount bankAccount = new BankAccount();
        Profile profile = fromProfile();
        bankAccount.setId(2L);
        bankAccount.setAccountNumber("4400430044004400");
        bankAccount.setBalance(500.0);
        bankAccount.setUpdateDate(null);
        bankAccount.setCreateDate(null);
        bankAccount.setProfile(profile);

        return bankAccount;
    }

    private BankAccount toAccount() {
        BankAccount bankAccount = new BankAccount();
        Profile profile = toProfile();
        bankAccount.setId(1L);
        bankAccount.setAccountNumber("4400440044004400");
        bankAccount.setBalance(500.0);
        bankAccount.setUpdateDate(null);
        bankAccount.setCreateDate(null);
        bankAccount.setProfile(profile);

        return bankAccount;
    }

    private Profile fromProfile() {
        Profile profile = new Profile();
        profile.setId(2L);
        profile.setRoles(roles());
        profile.setEmailConfirmed(true);
        profile.setEmail("test2@gmail.com");
        profile.setUsername("test2");
        profile.setLastName("lastname2");
        profile.setFirstName("firstname2");
        profile.setEmailConfirmed(true);
        profile.setPassword("password2");
        profile.setUpdateDate(null);
        profile.setCreateDate(null);

        return profile;
    }

    private Profile toProfile() {
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setRoles(roles());
        profile.setEmailConfirmed(true);
        profile.setEmail("test1@gmail.com");
        profile.setUsername("test1");
        profile.setLastName("lastname1");
        profile.setFirstName("firstname1");
        profile.setEmailConfirmed(true);
        profile.setPassword("password1");
        profile.setUpdateDate(null);
        profile.setCreateDate(null);

        return profile;
    }

    private List<Role> roles() {
        Role role = new Role();
        role.setId(1L);
        role.setTitle("USER");

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        return roles;
    }
}