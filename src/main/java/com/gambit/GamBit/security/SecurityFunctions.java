package com.gambit.GamBit.security;

import com.gambit.GamBit.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityFunctions {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static String getUserNameByContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public static Boolean hasRoleByContext(String role) {
        SimpleGrantedAuthority auth =  new SimpleGrantedAuthority(role);
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(auth);
    }
}
