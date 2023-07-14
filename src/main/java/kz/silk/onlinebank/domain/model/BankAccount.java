package kz.silk.onlinebank.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Bank Account
 *
 * @author 21 Savage
 */
@Entity
@Table(name = BankAccount.TABLE_NAME)
@Getter
@Setter
public class BankAccount extends BaseEntity {

    /**
     * Table name
     */
    public static final String TABLE_NAME = "BANK_ACCOUNT_TAB";

    /**
     * Account number
     */
    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    /**
     * Account balance
     */
    @Column(name = "BALANCE")
    private double balance = 0.0;

    /**
     * Profile
     */
    @ManyToOne
    private Profile profile;

    /**
     * Not @preUpdate action
     */
    @Override
    void onUpdate() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BankAccount that = (BankAccount) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
