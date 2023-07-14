package kz.silk.onlinebank.service;

import kz.silk.onlinebank.domain.dto.request.TransferDto;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import org.springframework.lang.NonNull;

/**
 * Service for money transfer
 *
 * @author YermukhanJJ
 */
public interface MoneyTransferService {

    /**
     * Money transfer method
     *
     * @param transferDto {@link TransferDto} transfer data
     */
    void transfer(@NonNull TransferDto transferDto) throws BadRequestException;
}
