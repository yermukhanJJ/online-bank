package kz.silk.onlinebank.domain.dao;

import kz.silk.onlinebank.domain.model.BankAccount;
import kz.silk.onlinebank.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Bank account entity repository
 *
 * @author YermukhanJJ
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    /**
     * Returns ban accounts by profile
     *
     * @param profile {@link Profile} entity
     * @return {@link List<BankAccount>}
     */
    List<BankAccount> findAllByProfile(Profile profile);

    /**
     * Return bank account by number
     *
     * @param accountNumber account number
     * @return {@link Optional<BankAccount>} bank account
     */
    Optional<BankAccount> findByAccountNumber(String accountNumber);

    /**
     * Return bank account by number and profile
     *
     * @param accountNumber account number
     * @param profile {@link Profile} account profile
     * @return {@link Optional<BankAccount>} bank account
     */
    Optional<BankAccount> findByAccountNumberAndProfile(String accountNumber, Profile profile);

}
