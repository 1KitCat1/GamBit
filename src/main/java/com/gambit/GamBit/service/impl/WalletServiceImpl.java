package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.GameAlreadyStartedException;
import com.gambit.GamBit.exception.ObjectAlreadyExistException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.model.*;
import com.gambit.GamBit.repository.*;
import com.gambit.GamBit.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final DecentralizedNetworkRepository networkRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;


    private void loadConnectedEntitiesById(Wallet wallet){
        if(wallet.getUser() != null && wallet.getUser().getId() != null){
            Optional<User> user = userRepository.findById(wallet.getUser().getId());
            user.ifPresent(wallet::setUser);
        }
        if(wallet.getNetwork() != null && wallet.getNetwork().getId() != null){
            Optional<DecentralizedNetwork> network =
                    networkRepository.findById(wallet.getNetwork().getId());
            network.ifPresent(wallet::setNetwork);
        }
    }

    @Override
    public void addWallet(Wallet wallet){
        loadConnectedEntitiesById(wallet);
        walletRepository.save(wallet);
    }

    @Override
    public Wallet getById(Long id) throws ObjectNotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(!wallet.isPresent()){
            throw new ObjectNotFoundException("No wallet with such id");
        }
        return wallet.get();
    }

    @Override
    public void deleteById(Long id) throws ObjectNotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(!wallet.isPresent()){
            throw new ObjectNotFoundException("No wallet with such id");
        }
        walletRepository.deleteById(id);
    }

    @Override
    public void updateById(Long id, Wallet updatedWallet) throws ObjectNotFoundException {
        Optional<Wallet> oldWallet = walletRepository.findById(id);
        if(!oldWallet.isPresent()){
            throw new ObjectNotFoundException("Wallet with such id has not been found");
        }

        loadConnectedEntitiesById(updatedWallet);
        System.out.println();
        updatedWallet.setId(id);
        walletRepository.save(updatedWallet);
    }

    @Override
    public List<Wallet> getByUserId(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("No user with such username");
        }
        return walletRepository.findByUser(user.get());
    }

    @Override
    public void joinGame(Long walletId, Long gameId) throws ObjectNotFoundException, GameAlreadyStartedException {
        Optional<Game> game = gameRepository.findById(gameId);
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if(!game.isPresent()) {
            throw new ObjectNotFoundException("No game with such id");
        }
        if(!wallet.isPresent()) {
            throw new ObjectNotFoundException("No wallet with such id");
        }
        if(game.get().getDateTime() != null) {
            throw new GameAlreadyStartedException("Game already started exception");
        }
        Player player = new Player();
        player.setGame(game.get());
        player.setWallet(wallet.get());
        playerRepository.save(player);
    }
}
