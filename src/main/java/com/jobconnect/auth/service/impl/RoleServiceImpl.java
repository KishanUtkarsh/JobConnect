package com.jobconnect.auth.service.impl;

import com.jobconnect.auth.entity.Role;
import com.jobconnect.auth.enums.RoleType;
import com.jobconnect.auth.service.RoleService;
import com.jobconnect.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(RoleType role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role));
    }
}
