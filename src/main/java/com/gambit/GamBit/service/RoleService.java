package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.RoleAlreadyExistException;
import com.gambit.GamBit.model.Role;

import java.util.List;

public interface RoleService {
    public Role addRole(Role role) throws RoleAlreadyExistException;

    List<Role> getAll();
}
