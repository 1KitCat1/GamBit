package com.gambit.GamBit.service;

import com.gambit.GamBit.model.Wallet;
import java.util.List;

public interface WalletService {
    void addRole(Wallet wallet);

    Wallet getByAddress(String address);

    void deleteById(Long id);

    void updateById(Long id, Wallet updatedWallet);

    List<Wallet> getByUsername(String name);
}
