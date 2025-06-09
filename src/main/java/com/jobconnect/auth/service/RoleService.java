package com.jobconnect.auth.service;

import com.jobconnect.auth.entity.Role;
import com.jobconnect.auth.enums.RoleType;

public interface RoleService {
    /**
     * Retrieves a role by its name.
     *
     * @param roleName the name of the role to retrieve
     * @return the role associated with the given name, or null if not found
     */
    Role getRoleByName(RoleType roleName);
}
