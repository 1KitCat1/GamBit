package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.RoleAlreadyExistException;
import com.gambit.GamBit.model.Role;
import com.gambit.GamBit.repository.RoleRepository;
import com.gambit.GamBit.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) throws RoleAlreadyExistException {
        if(roleRepository.findByName(role.getName()) != null){
            throw new RoleAlreadyExistException("Role with such name already exists");
        }
        return roleRepository.save(role);
    }
}
