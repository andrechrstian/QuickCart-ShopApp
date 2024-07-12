package org.example.tokonyadia.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.tokonyadia.entity.Role;
import org.example.tokonyadia.repository.RoleRepository;
import org.example.tokonyadia.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(Role.ERole role) {
        Optional<Role> optionalRole = roleRepository.findByName(role);
        if(optionalRole.isPresent()) {
            return optionalRole.get();
        }

        Role currentRole = Role.builder()
                .name(role)
                .build();


        return roleRepository.save(currentRole);
    }
}
