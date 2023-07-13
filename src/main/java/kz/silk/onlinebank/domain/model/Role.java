package kz.silk.onlinebank.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Roles entity class
 *
 * @author YermukhanJJ
 */
@Entity
@Table(name = Role.TABLE_NAME)
@Getter
@Setter
public class Role {

    /**
     * Table name constant
     */
    public static final String TABLE_NAME = "ROLE_TAB";

    /**
     * Role ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    /**
     * Role title
     */
    @Column(name = "TITLE", unique = true)
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
