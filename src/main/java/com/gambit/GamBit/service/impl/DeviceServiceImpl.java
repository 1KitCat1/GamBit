package com.gambit.GamBit.service.impl;


import com.gambit.GamBit.model.User;
import com.gambit.GamBit.model.dto.UserVerificationStatus;
import com.gambit.GamBit.repository.UserRepository;
import com.gambit.GamBit.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

//import static com.gambit.GamBit.GamBitApplication;
import static com.gambit.GamBit.GamBitApplication.staticEntities;
import static com.gambit.GamBit.service.common.Hashing.getHash;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final UserRepository userRepository;

    public Boolean authenticate(String securityKey, Long userId){
        String hashedKey = getHash(securityKey);
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) return false;
        if(user.get().getSecurityKey() == null) return false;
//        System.out.println(hashedKey + " | " + user.get().getSecurityKey());
        if(!hashedKey.equals(user.get().getSecurityKey())) return false;
        staticEntities.passedVerificationUsers.add(user.get().getName());
        staticEntities.notPassedVerificationUsers.remove(user.get().getName());
        return hashedKey.equals(user.get().getSecurityKey());
    }

    @Override
    public UserVerificationStatus checkStatus(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()) return new UserVerificationStatus();
        User user = userOptional.get();
        Boolean is2FAPending =
                staticEntities.notPassedVerificationUsers.contains(user.getName());

        return new UserVerificationStatus(user.getId(), user.getName(), is2FAPending);
    }

    @Override
    public Boolean declineAuthentication(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()) return false;

        return staticEntities.notPassedVerificationUsers.remove(userOptional.get().getName());
    }
}
