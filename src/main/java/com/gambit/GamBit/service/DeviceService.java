package com.gambit.GamBit.service;

import com.gambit.GamBit.model.dto.UserVerificationStatus;

public interface DeviceService {

    Boolean authenticate(String securityKey, Long id);

    UserVerificationStatus checkStatus(Long id);

    Boolean declineAuthentication(Long userId);
}
