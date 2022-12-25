package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.GameAlreadyStartedException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.model.Player;
import com.gambit.GamBit.model.Wallet;
import com.gambit.GamBit.model.dto.JoinGame;
import org.hibernate.mapping.Join;

import java.util.List;

public interface WalletService {

    void addWallet(Wallet wallet);

    Wallet getById(Long id) throws ObjectNotFoundException;

    void deleteById(Long id) throws ObjectNotFoundException;

    void updateById(Long id, Wallet updatedWallet) throws ObjectNotFoundException;

    List<Wallet> getByUserId(Long id) throws UserNotFoundException;

    JoinGame joinGame(JoinGame joinGameData) throws ObjectNotFoundException, GameAlreadyStartedException;

    List<Wallet> getByUsername(String name) throws UserNotFoundException;
}
