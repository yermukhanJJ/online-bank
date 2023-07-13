package kz.silk.onlinebank.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Base entity class
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    /**
     * Entity ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Data create time
     */
    @Column(name = "CREATE_AT")
    private OffsetDateTime createDate;

    /**
     * Data update time
     */
    @Column(name = "UPDATE_AT")
    private OffsetDateTime updateDate;

    @PrePersist
    void onCreate() {
        this.createDate = OffsetDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updateDate = OffsetDateTime.now();
    }
}