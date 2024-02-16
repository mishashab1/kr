package com.example.back.service;

import com.example.back.model.Role;
import com.example.back.model.enums.RoleType;
import com.example.back.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRole(RoleType name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public List<SimpleGrantedAuthority> getAuthorities(Long userId) {
        Role role = roleRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // ? Ќепосредственно добавл€ет роль пользовател€
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

        return authorities;
    }
}
