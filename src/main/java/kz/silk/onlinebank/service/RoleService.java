package kz.silk.onlinebank.service;

import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.model.Role;
import org.springframework.lang.NonNull;

/**
 * Role entity service
 *
 * @author YermukhanJJ
 */
public interface RoleService {

    /**
     * getting role by title
     *
     * @param title role title
     * @return {@link Role} role entity
     * @throws NotFoundException not found (404) entity
     */
    Role getRoleByTitle(@NonNull String title) throws NotFoundException;

}
