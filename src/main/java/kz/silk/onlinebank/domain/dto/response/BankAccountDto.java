package kz.silk.onlinebank.domain.dto.response;

import kz.silk.onlinebank.domain.model.BankAccount;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * Bank account dto
 *
 * @author YermukhanJJ
 */
@Data
public class BankAccountDto {

    /**
     * Account number
     */
    private String accountNumber;

    /**
     * account balance
     */
    private double balance;

    /**
     * Account creation date
     */
    private OffsetDateTime createAt;

    /**
     * Account updating date
     */
    private OffsetDateTime updateAt = null;

    /**
     * Return bank account dto from bank account entity
     *
     * @param bankAccount {@link BankAccount} entity
     * @return {@link BankAccountDto} object
     */
    public static BankAccountDto fromEntity(BankAccount bankAccount) {
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDto.setBalance(bankAccount.getBalance());
        bankAccountDto.setCreateAt(bankAccount.getCreateDate());
        if (bankAccount.getUpdateDate() != null) {
            bankAccountDto.setUpdateAt(bankAccount.getUpdateDate());
        }

        return bankAccountDto;
    }
}
