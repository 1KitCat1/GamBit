package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.RoleAlreadyExistException;
import com.gambit.GamBit.model.Game;
import com.gambit.GamBit.model.Player;
import com.gambit.GamBit.model.SmartContract;
import com.gambit.GamBit.model.Wallet;
import com.gambit.GamBit.repository.GameRepository;
import com.gambit.GamBit.repository.SmartContractRepository;
import com.gambit.GamBit.service.GameService;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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
                " | timestamp: " + game.getDateTime() +
                " | RESULT: " + game.getGameScore() +
                " | protection: " + game.getRandomSalt();
    }

    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b: bytes)
            stringBuilder.append(String.format("%02x", b));
        return stringBuilder.toString();
    }

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
    public String getHashedResult(Long id) throws ObjectNotFoundException {
        Optional<Game> game = gameRepository.findById(id);
        if(!game.isPresent()){
            throw new ObjectNotFoundException("No game with such id");
        }
        String message = generateResultMessage(game.get());
        try{
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            byte[] hashedMessage = digester.digest(message.getBytes());
             return byteArrayToHex(hashedMessage);
        } catch (Exception ex){
            return "Error occurred during hashing result";
            // TODO : maybe do smth here (there should be no exception actually)
        }
    }

}
