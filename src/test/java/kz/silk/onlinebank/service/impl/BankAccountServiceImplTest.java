package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.domain.dao.BankAccountRepository;
import kz.silk.onlinebank.domain.dto.response.BankAccountDto;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.InternalErrorException;
import kz.silk.onlinebank.domain.model.BankAccount;
import kz.silk.onlinebank.domain.model.Profile;
import kz.silk.onlinebank.domain.model.Role;
import kz.silk.onlinebank.service.ProfileService;
import kz.silk.onlinebank.service.RedisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.Redisson;
import org.redisson.api.RList;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private ProfileService profileService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Test
    void createAccount() {
        BankAccount bankAccount = getBankAccount();
        RList<String> accountNumbers = Redisson.create().getList("account_numbers_test");

        when(profileService.getCurrentProfile()).thenReturn(bankAccount.getProfile());
        when(redisService.getAccountNumbers()).thenReturn(accountNumbers);
        when(bankAccountRepository.save(any())).thenReturn(bankAccount);

        BankAccount result = bankAccountService.createAccount(0.0);

        accountNumbers.add(bankAccount.getAccountNumber());
        assertNotNull(result);
        assertEquals(bankAccount.getBalance(), result.getBalance());
        assertTrue(accountNumbers.contains(result.getAccountNumber()));
        assertEquals(bankAccount.getProfile(), result.getProfile());
        accountNumbers.clear();
    }

    @Test
    void createAccountThrowException() {
        BankAccount bankAccount = getBankAccount();
        RList<String> accountNumbers = Redisson.create().getList("account_numbers_test");

        accountNumbers.add("1111111111");

        when(profileService.getCurrentProfile()).thenReturn(bankAccount.getProfile());
        when(redisService.getAccountNumbers()).thenReturn(accountNumbers);
        when(bankAccountRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(InternalErrorException.class, () -> bankAccountService.createAccount(0.0));
        verify(profileService, times(1)).getCurrentProfile();
        verify(redisService, times(1)).getAccountNumbers();
    }

    @Test
    void getCurrentUserAccounts() {

        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(getBankAccount());
        when(profileService.getCurrentProfile()).thenReturn(getProfile());
        when(bankAccountRepository.findAllByProfile(any())).thenReturn(bankAccounts);

        List<BankAccountDto> result = bankAccountService.getCurrentUserAccounts();
        List<BankAccountDto> expected = getBankAccountDtoList();

        assertNotNull(result);
        assertThat(result.isEmpty())
                .isFalse();
        assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void getCurrentUserAccounts_isEmpty() {

        when(profileService.getCurrentProfile()).thenReturn(getProfile());
        when(bankAccountRepository.findAllByProfile(any())).thenReturn(new ArrayList<>());

        List<BankAccountDto> result = bankAccountService.getCurrentUserAccounts();

        assertNotNull(result);
        assertThat(result.isEmpty())
                .isTrue();
    }

    @Test
    void getAccountByNumber() {

        BankAccount bankAccount = getBankAccount();

        when(bankAccountRepository.findByAccountNumber(any())).thenReturn(Optional.of(bankAccount));

        BankAccount result = bankAccountService.getAccountByNumber(bankAccount.getAccountNumber());
        BankAccount expected = getBankAccount();

        assertThat(result)
                .isNotNull()
                .extracting("accountNumber")
                .isEqualTo(expected.getAccountNumber());
    }

    @Test
    void getAccountByNumber_ThrowBadRequestException() {

        BankAccount bankAccount = getBankAccount();

        when(bankAccountRepository.findByAccountNumber(any())).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> bankAccountService.getAccountByNumber(bankAccount.getAccountNumber()));
    }

    @Test
    void getAccountByNumberAndProfile() {
        BankAccount bankAccount = getBankAccount();
        when(bankAccountRepository.findByAccountNumberAndProfile(any(), any())).thenReturn(Optional.of(bankAccount));

        BankAccount result = bankAccountService.getAccountByNumberAndProfile("4400440044004400");
        BankAccount expected = getBankAccount();

        assertThat(result).isNotNull()
                .isEqualTo(expected)
                .extracting("accountNumber")
                .isEqualTo(expected.getAccountNumber());
    }

    private BankAccount getBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setAccountNumber("4400440044004400");
        bankAccount.setBalance(0.0D);
        bankAccount.setProfile(getProfile());
        bankAccount.setCreateDate(null);
        bankAccount.setUpdateDate(null);

        return bankAccount;
    }

    private Profile getProfile() {
        Profile profile = new Profile();
        profile.setLastName("testLastName");
        profile.setFirstName("testFirstName");
        profile.setUsername("testUsername");
        profile.setEmail("testEmail@example.com");
        profile.setBlocked(false);
        profile.setRoles(roles());
        profile.setEmailConfirmed(true);
        profile.setCreateDate(null);
        profile.setUpdateDate(null);

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

    private List<BankAccountDto> getBankAccountDtoList() {
        BankAccountDto bankAccountDto = BankAccountDto.fromEntity(getBankAccount());
        List<BankAccountDto> bankAccountDtos = new ArrayList<>();
        bankAccountDtos.add(bankAccountDto);

        return bankAccountDtos;
    }
}