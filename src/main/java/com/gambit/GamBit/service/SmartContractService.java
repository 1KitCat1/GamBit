package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.model.SmartContract;

import java.util.List;

public interface SmartContractService {
    SmartContract addContract(SmartContract contract);

    SmartContract getById(Long id) throws ObjectNotFoundException;

    List<SmartContract> getAll();

    void deleteById(long id);

    void updateById(long id, SmartContract updatedContract) throws ObjectNotFoundException;

    SmartContract setNetwork(Long networkId, Long contractId)  throws ObjectNotFoundException ;
}
