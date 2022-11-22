package com.gambit.GamBit.repository;

import com.gambit.GamBit.model.User;
import com.gambit.GamBit.model.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
    Wallet findByAddress(String address);
    List<Wallet> findByUser(User user);
}
