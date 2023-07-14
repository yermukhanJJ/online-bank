package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.domain.dao.RoleRepository;
import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.model.Role;
import kz.silk.onlinebank.service.RoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    public static final String SERVICE_VALUE = "RoleServiceImpl";
    private final RoleRepository roleRepository;

    @Cacheable(cacheNames = "role", key = "#title")
    @Override
    public Role getRoleByTitle(@NonNull String title) throws NotFoundException {
        return roleRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException(String.format("Role with title %s not found", title)));
    }
}
