package com.jobconnect.auth.security;

import com.jobconnect.auth.entity.Role;
import com.jobconnect.auth.enums.RoleType;
import com.jobconnect.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DbInit {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles(){
        for(RoleType roleType : RoleType.values()){
            roleRepository.findByName(roleType)
                .orElseGet(() -> roleRepository.save(new Role(roleType)));
        }
    }
}
