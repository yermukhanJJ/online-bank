package kz.silk.onlinebank.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User profile entity class
 *
 * @author YermukhanJJ
 */
@Entity
@Table(name = Profile.TABLE_NAME)
@Getter
@Setter
public class Profile extends BaseEntity implements Serializable, UserDetails {

    public static final String TABLE_NAME = "PROFILE_TAB";

    /**
     * User first name
     */
    @Column(name = "FIRST_NAME")
    private String firstName;

    /**
     * User last name
     */
    @Column(name = "LAST_NAME")
    private String lastName;

    /**
     * User name
     */
    @Column(name = "USERNAME", unique = true)
    private String username;

    /**
     * User email address
     */
    @Column(name = "EMAIL", unique = true)
    private String email;

    /**
     * User password
     */
    @ToString.Exclude
    @Column(name = "PASSWORD")
    private String password;

    /**
     * Email confirmed flag
     */
    @Column(name = "EMAIL_CONFIRMED", nullable = false)
    private Boolean emailConfirmed = false;

    /**
     * Profile lock flag
     */
    @Column(name = "BLOCKED", nullable = false)
    private Boolean blocked = true;

    /**
     * User roles
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_profile",
            joinColumns = {@JoinColumn(name = "profile_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return emailConfirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Profile profile = (Profile) o;
        return getId() != null && Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}