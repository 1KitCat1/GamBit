package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.GameNotStartedException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.PlayEndedException;
import com.gambit.GamBit.model.*;
import com.gambit.GamBit.model.Player;
import com.gambit.GamBit.model.dto.PlayerResult;
import com.gambit.GamBit.repository.GameRepository;
import com.gambit.GamBit.repository.PlayerRepository;
import com.gambit.GamBit.repository.WalletRepository;
import com.gambit.GamBit.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.gambit.GamBit.service.common.CalculateTokensIncrease.tokensIncrease;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final WalletRepository walletRepository;

    private void loadConnectedEntitiesById(Player player){
        if(player.getWallet() != null && player.getWallet().getId() != null){
            Optional<Wallet> wallet = walletRepository.findById(player.getWallet().getId());
            wallet.ifPresent(player::setWallet);
        }
        if(player.getGame() != null && player.getGame().getId() != null){
            Optional<Game> game =
                    gameRepository.findById(player.getGame().getId());
            game.ifPresent(player::setGame);
        }
    }
    @Override
    public void addPlayer(Player player) {
        loadConnectedEntitiesById(player);
        playerRepository.save(player);
    }

    @Override
    public Player getById(Long id) throws ObjectNotFoundException {
        Optional<Player> player = playerRepository.findById(id);
        if(!player.isPresent()){
            throw new ObjectNotFoundException("No player with such id");
        }
        return player.get();
    }

    @Override
    public void deleteById(Long id) throws ObjectNotFoundException {
        Optional<Player> player = playerRepository.findById(id);
        if(!player.isPresent()){
            throw new ObjectNotFoundException("No player with such id");
        }
        playerRepository.deleteById(id);
    }

    @Override
    public void updateById(Long id, Player updatedGame) throws ObjectNotFoundException {
        Optional<Player> player = playerRepository.findById(id);
        if(!player.isPresent()){
            throw new ObjectNotFoundException("No player with such id");
        }

        loadConnectedEntitiesById(updatedGame);
        updatedGame.setId(id);
        playerRepository.save(updatedGame);
    }

    @Override
    public List<Player> getByWallet(String address) {
        Wallet wallet = walletRepository.findByAddress(address);
        return playerRepository.findByWallet(wallet);
    }

    @Override
    public PlayerResult endPlay(Long playerId)
            throws ObjectNotFoundException,
            GameNotStartedException,
            PlayEndedException
    {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if(!playerOptional.isPresent()) {
            throw new ObjectNotFoundException("Player with id " + playerId + " not found");
        }
        Player player = playerOptional.get();
        if(player.getTokensReturn() != null) {
            throw new PlayEndedException("Player has already ended game");
        }

        Game game = player.getGame();
        LocalDateTime startGameTime = game.getStartTime();
        if(startGameTime == null) {
            throw new GameNotStartedException("Game is not started");
        }

        final Long NANO_IN_MILLISECONDS = 1_000_000L;
        LocalDateTime endGameTime =
                startGameTime.plusNanos(game.getGameScore() * NANO_IN_MILLISECONDS);

        LocalDateTime endOfPlayTime = LocalDateTime.now();

        if(endGameTime.isAfter(endOfPlayTime)){
            player.setIsVictory(true);
            Long millisecondsInGame = ChronoUnit.MILLIS.between(startGameTime, endOfPlayTime);
            player.setTokensReturn(
                    tokensIncrease(player.getTokensInput(), millisecondsInGame));
        }
        else  {
            player.setIsVictory(false);
            player.setTokensReturn(0L);
        }
        playerRepository.save(playerOptional.get());
        return new PlayerResult(
                player.getId(),
                player.getWallet().getId(),
                player.getGame().getId(),
                player.getTokensInput(),
                player.getTokensReturn(),
                player.getIsVictory()
                );
    }
}
