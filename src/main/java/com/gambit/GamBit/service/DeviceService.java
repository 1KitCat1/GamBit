package com.gambit.GamBit.service;

import com.gambit.GamBit.model.dto.UserVerificationStatus;

public interface DeviceService {

    Boolean authorize(String securityKey, Long id);

    UserVerificationStatus checkStatus(Long id);
}
