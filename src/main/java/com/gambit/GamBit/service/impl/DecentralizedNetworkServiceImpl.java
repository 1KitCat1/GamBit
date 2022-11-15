package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.NetworkAlreadyExistException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.model.DecentralizedNetwork;
import com.gambit.GamBit.repository.DecentralizedNetworkRepository;
import com.gambit.GamBit.service.DecentralizedNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DecentralizedNetworkServiceImpl implements DecentralizedNetworkService {
    private final DecentralizedNetworkRepository networkRepository;

    @Override
    public List<DecentralizedNetwork> getAll() {
        return (List<DecentralizedNetwork>) networkRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        networkRepository.deleteById(id);
    }

    @Override
    public void updateById(long id, DecentralizedNetwork updatedNetwork) throws ObjectNotFoundException {
        Optional<DecentralizedNetwork> oldNetwork = networkRepository.findById(id);
        if(!oldNetwork.isPresent()){
            throw new ObjectNotFoundException("Network with such id has not been found");
        }
        updatedNetwork.setId(id);
        networkRepository.save(updatedNetwork);
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
