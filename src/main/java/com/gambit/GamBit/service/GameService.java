package com.gambit.GamBit.service;

import com.gambit.GamBit.model.Game;

import java.util.List;

public interface GameService {
    void addGame(Game game);

    Game getById(Long id);

    List<Game> getByWallet(Long id);

    void deleteById(Long id);

    void updateById(Long id, Game updatedGame);
}
