package kz.silk.onlinebank.domain.dao;

import kz.silk.onlinebank.domain.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * {@link ConfirmationToken} JPA repository interface.
 *
 * @author YermukhanJJ
 */
@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationToken, Long> {

    /**
     * Returns an optional of confirmation token by token value.
     *
     * @param token Confirmation token value
     * @return Optional of {@link ConfirmationToken}
     */
    Optional<ConfirmationToken> findByToken(String token);

    /**
     * Updates confirmation token by token value and sets new
     * confirmation datetime.
     *
     * @param token Confirmation token value
     * @param confirmedAt Confirmation datetime
     * @return Number of updated rows
     */
    @Transactional
    @Modifying
    @Query("update ConfirmationToken c set c.confirmedAt = :confirmedAt where c.token = :token")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
