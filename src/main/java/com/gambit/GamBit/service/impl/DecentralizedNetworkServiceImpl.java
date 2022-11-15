package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.NetworkAlreadyExistException;
import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.model.DecentralizedNetwork;
import com.gambit.GamBit.model.User;
import com.gambit.GamBit.repository.DecentralizedNetworkRepository;
import com.gambit.GamBit.service.DecentralizedNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.channels.NetworkChannel;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DecentralizedNetworkServiceImpl implements DecentralizedNetworkService {
    private final DecentralizedNetworkRepository networkRepository;

    @Override
    public List<DecentralizedNetwork> getAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void updateById(long id, User updatedUser) {

    }

    @Override
    public DecentralizedNetwork getById(Long id) {
        return null;
    }

    @Override
    public DecentralizedNetwork addNetwork(DecentralizedNetwork network) throws NetworkAlreadyExistException {
        System.out.println("Registration user " + network.getName());
        if(networkRepository.findByName(network.getName()) != null){
            System.out.println("User already exist " + network.getName());
            throw new NetworkAlreadyExistException("Network with such name already exist");
        }
        return networkRepository.save(network);
    }
}
