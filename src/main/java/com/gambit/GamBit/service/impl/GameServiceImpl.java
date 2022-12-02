package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.GameNotFinishedException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.model.Game;
import com.gambit.GamBit.model.SmartContract;
import com.gambit.GamBit.model.dto.GameStatus;
import com.gambit.GamBit.repository.GameRepository;
import com.gambit.GamBit.repository.SmartContractRepository;
import com.gambit.GamBit.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

import static com.gambit.GamBit.service.common.CalculateTokensIncrease.tokensMultiplier;
import static com.gambit.GamBit.service.common.Hashing.getHash;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final SmartContractRepository contractRepository;

    private final Random random = new Random(LocalDateTime.now().getNano());

    private void loadConnectedEntitiesById(Game game){
        if(game.getSmartContract() != null && game.getSmartContract().getId() != null){
            Optional<SmartContract> contract = contractRepository.findById(game.getSmartContract().getId());
            contract.ifPresent(game::setSmartContract);
        }
    }

    private String generateResultMessage(Game game){
        return "Game id: " + game.getId() +
                " | RESULT: " + game.getGameScore() +
                " | protection: " + game.getRandomSalt();
    }

    @Override
    public void addGame(Game game) {
        final long MAX_GAME_LENGTH = 50_000L; // In milliseconds
        if(game.getGameScore() == null) {
            game.setGameScore(Math.abs(random.nextLong()) % MAX_GAME_LENGTH);
        }
        if(game.getRandomSalt() == null) {
            game.setRandomSalt(
                    ((Long)Math.abs(random.nextLong())).toString() +
                    ((Long)Math.abs(random.nextLong())).toString()
            );
        }
        loadConnectedEntitiesById(game);
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
        loadConnectedEntitiesById(updatedGame);
        gameRepository.save(updatedGame);
    }

    @Override
    public String getNotHashedResult(Long id) throws ObjectNotFoundException, GameNotFinishedException {
        Optional<Game> game = gameRepository.findById(id);
        if(!game.isPresent()){
            throw new ObjectNotFoundException("No game with such id");
        }
        GameStatus gameStatus = getStatus(id);
        if(!gameStatus.getGameStatus().equals(Stage.FINISHED.name())) {
            throw new GameNotFinishedException("Game not finished");
        }
        return generateResultMessage(game.get());
    }

    @Override
    public String startGame(Long id) throws ObjectNotFoundException {
        Optional<Game> game = gameRepository.findById(id);
        if(!game.isPresent()){
            throw new ObjectNotFoundException("No game with such id");
        }
        game.get().setStartTime(LocalDateTime.now());
        gameRepository.save(game.get());
        return game.get().getStartTime().toString();
    }

    private enum Stage {NOT_STARTED, STARTED, FINISHED}

    @Override
    public GameStatus getStatus(Long id) throws ObjectNotFoundException {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if(!gameOptional.isPresent()) {
            throw new ObjectNotFoundException("No game with such id");
        }
        Game game = gameOptional.get();
        LocalDateTime endTime = null;
        if(game.getStartTime() != null) {
            endTime = game.getStartTime().plus(game.getGameScore(), ChronoUnit.MILLIS);
        }
        Stage stage;
        if(game.getStartTime() == null || endTime == null) {
            stage = Stage.NOT_STARTED;
        } else if(LocalDateTime.now().isBefore(endTime)) {
            stage = Stage.STARTED;
        }
        else {
            stage = Stage.FINISHED;
        }
        double currentMultiplier = 1.0;
        if(stage == Stage.STARTED) {
            currentMultiplier = tokensMultiplier(
                    ChronoUnit.MILLIS.between(game.getStartTime(), LocalDateTime.now()));
        }
        if(stage == Stage.FINISHED){
            currentMultiplier = 0.0;
        }
        return new GameStatus(
                game.getId(),
                game.getStartTime(),
                endTime,
                currentMultiplier,
                tokensMultiplier(game.getGameScore()),
                stage.name()
                );
    }

    @Override
    public String getHashedResult(Long id) throws ObjectNotFoundException {
        Optional<Game> game = gameRepository.findById(id);
        if(!game.isPresent()){
            throw new ObjectNotFoundException("No game with such id");
        }
        String message = generateResultMessage(game.get());
        return getHash(message);
    }
}
