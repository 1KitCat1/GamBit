package com.gambit.GamBit.repository;

import com.gambit.GamBit.model.Player;
import com.gambit.GamBit.model.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    List<Player> findByWallet(Wallet wallet);
}
