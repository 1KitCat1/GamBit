package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.ObjectAlreadyExistException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.model.User;
import com.gambit.GamBit.model.Wallet;
import com.gambit.GamBit.repository.UserRepository;
import com.gambit.GamBit.repository.WalletRepository;
import com.gambit.GamBit.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    @Override
    public void addWallet(Wallet wallet){
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
            throw new ObjectNotFoundException("Contract with such id has not been found");
        }
        updatedWallet.setId(id);
        walletRepository.save(updatedWallet);
    }

    @Override
    public List<Wallet> getByUsername(String name) throws UserNotFoundException {
        User user = userRepository.findByName(name);
        if(user == null){
            throw new UserNotFoundException("No user with such username");
        }
        return walletRepository.findByUser(user);
    }
}
