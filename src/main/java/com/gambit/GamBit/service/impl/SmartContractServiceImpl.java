package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.NetworkAlreadyExistException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.model.DecentralizedNetwork;
import com.gambit.GamBit.model.SmartContract;
import com.gambit.GamBit.repository.SmartContractRepository;
import com.gambit.GamBit.service.SmartContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class SmartContractServiceImpl implements SmartContractService {
    private final SmartContractRepository contractRepository;

    @Override
    public SmartContract addContract(SmartContract contract) {
        System.out.println("Adding new smart contract  " + contract.getAddress());
//        if(contractRepository.findByAddress(network.getName()) != null){
//            System.out.println("Network already exist " + network.getName());
//            throw new NetworkAlreadyExistException("Network with such name already exist");
//        }
        return contractRepository.save(contract);
    }

    @Override
    public SmartContract getById(Long id) throws ObjectNotFoundException {
        Optional<SmartContract> contract = contractRepository.findById(id);
        if(!contract.isPresent()){
            throw new ObjectNotFoundException("Contract with such name has not been found");
        }
        return contract.get();
    }

    @Override
    public List<SmartContract> getAll() {
        return (List<SmartContract>) contractRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        contractRepository.deleteById(id);
    }

    @Override
    public void updateById(long id, SmartContract updatedNetwork) throws ObjectNotFoundException {
        Optional<SmartContract> oldNetwork = contractRepository.findById(id);
        if(!oldNetwork.isPresent()){
            throw new ObjectNotFoundException("Contract with such id has not been found");
        }
        updatedNetwork.setId(id);
        contractRepository.save(updatedNetwork);
    }
}
