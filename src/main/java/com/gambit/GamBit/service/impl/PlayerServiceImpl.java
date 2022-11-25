package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.model.*;
import com.gambit.GamBit.model.Player;
import com.gambit.GamBit.repository.GameRepository;
import com.gambit.GamBit.repository.PlayerRepository;
import com.gambit.GamBit.repository.WalletRepository;
import com.gambit.GamBit.service.GameService;
import com.gambit.GamBit.service.PlayerService;
import com.gambit.GamBit.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
