package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.NetworkAlreadyExistException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.model.DecentralizedNetwork;
import com.gambit.GamBit.model.User;
import com.gambit.GamBit.repository.DecentralizedNetworkRepository;
import com.gambit.GamBit.service.DecentralizedNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public DecentralizedNetwork getById(Long id) throws ObjectNotFoundException {
        Optional<DecentralizedNetwork> network = networkRepository.findById(id);
        if(!network.isPresent()){
            throw new ObjectNotFoundException("Network with such name has not been found");
        }
        return network.get();
    }

    @Override
    public DecentralizedNetwork addNetwork(DecentralizedNetwork network) throws NetworkAlreadyExistException {
        System.out.println("Adding new network  " + network.getName());
        if(networkRepository.findByName(network.getName()) != null){
            System.out.println("Network already exist " + network.getName());
            throw new NetworkAlreadyExistException("Network with such name already exist");
        }
        return networkRepository.save(network);
    }
}
