package com.gambit.GamBit.repository;

import com.gambit.GamBit.model.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    
}
