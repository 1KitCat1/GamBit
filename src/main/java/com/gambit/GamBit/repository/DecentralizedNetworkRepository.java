package com.gambit.GamBit.repository;

import com.gambit.GamBit.model.DecentralizedNetwork;
import com.gambit.GamBit.model.User;
import org.springframework.data.repository.CrudRepository;

public interface DecentralizedNetworkRepository extends CrudRepository<DecentralizedNetwork, Long> {
    DecentralizedNetwork findByName(String name);
}
