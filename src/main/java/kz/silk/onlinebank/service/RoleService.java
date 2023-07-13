package kz.silk.onlinebank.service;

import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.model.Role;
import org.springframework.lang.NonNull;

public interface RoleService {

    Role getRoleByTitle(@NonNull String title) throws NotFoundException;

}
