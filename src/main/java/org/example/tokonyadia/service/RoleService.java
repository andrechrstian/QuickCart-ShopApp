package org.example.tokonyadia.service;

import org.example.tokonyadia.entity.Role;

public interface RoleService {
    Role getOrSave(Role.ERole role);
}