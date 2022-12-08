package com.gambit.GamBit.model.dto;

import lombok.Data;

@Data
public class UserVerificationStatus {
    Long id;
    String username;
    Boolean is2FAPending;
    public UserVerificationStatus(){
        is2FAPending = false;
    }

    public UserVerificationStatus(Long id, String username, Boolean is2FAPending) {
        this.id = id;
        this.username = username;
        this.is2FAPending = is2FAPending;
    }
}
