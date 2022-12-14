package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.GameNotStartedException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.PlayEndedException;
import com.gambit.GamBit.model.Player;
import com.gambit.GamBit.model.dto.PlayerResult;

import java.util.List;

public interface PlayerService {
    void addPlayer(Player game);

    Player getById(Long id) throws ObjectNotFoundException;

    void deleteById(Long id) throws ObjectNotFoundException;

    void updateById(Long id, Player updatedGame) throws ObjectNotFoundException;

    List<Player> getByWallet(String address);

    PlayerResult endPlay(Long playerId) throws ObjectNotFoundException, GameNotStartedException, PlayEndedException;
}
