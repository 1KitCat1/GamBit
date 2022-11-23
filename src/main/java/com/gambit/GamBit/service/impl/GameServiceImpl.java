package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.RoleAlreadyExistException;
import com.gambit.GamBit.model.Game;
import com.gambit.GamBit.repository.GameRepository;
import com.gambit.GamBit.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final Random random = new Random(LocalDateTime.now().getNano());
    @Override
    public void addGame(Game game) {
        if(game.getDateTime() == null) { game.setDateTime(LocalDateTime.now()); }
        if(game.getGameScore() == null) {
            game.setGameScore(Math.abs(random.nextLong()) % 1000);
        }
        if(game.getRandomSalt() == null) {
            game.setRandomSalt(
                    ((Long)Math.abs(random.nextLong())).toString() +
                    ((Long)Math.abs(random.nextLong())).toString()
            );
        }

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
