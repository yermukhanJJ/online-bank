package kz.silk.onlinebank.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Money transfer data
 *
 * @author YermukhanJJ
 */
@Data
public class TransferDto {

    /**
     * Account number where the transfer starts from
     */
    @NotBlank
    private String fromAccount;

    /**
     * The account number that received the money
     */
    @NotBlank
    private String toAccount;

    /**
     * Money sum
     */
    @Positive
    private double sum;
}
