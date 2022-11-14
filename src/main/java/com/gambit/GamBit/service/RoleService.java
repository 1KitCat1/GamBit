package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.RoleAlreadyExistException;
import com.gambit.GamBit.model.Role;

public interface RoleService {
    public Role addRole(Role role) throws RoleAlreadyExistException;
}
