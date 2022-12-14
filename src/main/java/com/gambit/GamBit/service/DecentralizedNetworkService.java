package com.gambit.GamBit.service;


import com.gambit.GamBit.exception.NetworkAlreadyExistException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.model.DecentralizedNetwork;
import com.gambit.GamBit.model.User;

import java.util.List;

public interface DecentralizedNetworkService {
    List<DecentralizedNetwork> getAll();

    void deleteById(long id);

    void updateById(long id, DecentralizedNetwork updatedNetwork) throws ObjectNotFoundException;

    DecentralizedNetwork getById(Long id) throws ObjectNotFoundException;

    DecentralizedNetwork addNetwork(DecentralizedNetwork network) throws NetworkAlreadyExistException;
}
