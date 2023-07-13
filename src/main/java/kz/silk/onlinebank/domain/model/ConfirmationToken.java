package kz.silk.onlinebank.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Confirmation token entity class.<br>
 * Used in registration process.
 *
 * @author YermukhanJJ
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ConfirmationToken.TABLE_NAME)
public class ConfirmationToken implements Serializable {

    /**
     * Table name constant.
     */
    public static final String TABLE_NAME = "CONFIRMATION_TOKEN";

    /**
     * Confirmation token ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /**
     * Token value.
     */
    @Column(name = "TOKEN", nullable = false, unique = true)
    private String token;

    /**
     * Creation timestamp.
     */
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Expiration timestamp.
     */
    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    /**
     * Confirmation timestamp.
     */
    @Column(name = "CONFIRMED_AT")
    private LocalDateTime confirmedAt;

    /**
     * Profile that can be activated via token.
     */
    @ManyToOne
    @JoinColumn(name = "PROFILE_ID", nullable = false)
    private Profile profile;

    /**
     * Confirmation token constructor with all required args.
     *
     * @param token Token value
     * @param createdAt Creation timestamp
     * @param expiresAt Expiration timestamp
     * @param profile Profile that can be activated via token
     */
    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             Profile profile) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.profile = profile;
    }
}