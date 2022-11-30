package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.model.Game;
import com.gambit.GamBit.model.dto.GameStatus;

public interface GameService {
    void addGame(Game game);

    Game getById(Long id) throws ObjectNotFoundException;

    void deleteById(Long id) throws ObjectNotFoundException;

    void updateById(Long id, Game updatedGame) throws ObjectNotFoundException;

    String getHashedResult(Long id) throws ObjectNotFoundException;

    String getNotHashedResult(Long id) throws ObjectNotFoundException;

    String startGame(Long id) throws ObjectNotFoundException;

    GameStatus getStatus(Long id) throws ObjectNotFoundException;
}
