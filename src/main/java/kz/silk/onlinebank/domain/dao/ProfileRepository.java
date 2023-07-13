package kz.silk.onlinebank.domain.dao;

import kz.silk.onlinebank.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * Returns an optional of profile by username
     *
     * @param username User name
     * @return Optional of {@link Profile}
     */
    Optional<Profile> findByUsername(String username);

    /**
     * Returns an optional of profile by email
     *
     * @param email Email address
     * @return Optional of {@link Profile}
     */
    Optional<Profile> findByEmail(String email);

    /**
     * Checks if profile already exists
     * by email
     *
     * @param email Email address
     * @return true - profile exists
     */
    boolean existsByEmail(String email);

    /**
     * Checks if profile already exists
     * by username
     *
     * @param username User name
     * @return true - profile exists
     */
    boolean existsByUsername(String username);
}
