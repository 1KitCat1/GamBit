package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.RoleAlreadyExistException;
import com.gambit.GamBit.model.Game;
import com.gambit.GamBit.repository.GameRepository;
import com.gambit.GamBit.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    @Override
    public void addGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public Game getById(Long id) throws ObjectNotFoundException {
        Optional<Game> game = gameRepository.findById(id);
        if(!game.isPresent()){
            throw new ObjectNotFoundException("No game with such id");
        }
        return game.get();
    }

    @Override
    public void deleteById(Long id) throws ObjectNotFoundException {
        Optional<Game> game = gameRepository.findById(id);
        if(!game.isPresent()){
            throw new ObjectNotFoundException("No game with such id");
        }
        gameRepository.deleteById(id);
    }

    @Override
    public void updateById(Long id, Game updatedGame) throws ObjectNotFoundException {
        Optional<Game> game = gameRepository.findById(id);
        if(!game.isPresent()){
            throw new ObjectNotFoundException("No game with such id");
        }
        updatedGame.setId(id);
        gameRepository.save(updatedGame);
    }
}
