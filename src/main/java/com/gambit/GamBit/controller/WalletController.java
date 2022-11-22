package com.gambit.GamBit.controller;

import com.gambit.GamBit.model.Wallet;
import com.gambit.GamBit.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gambit.GamBit.controller.AccessRolesController.ACCESS_USER;

@RestController
@RequiredArgsConstructor
public class WalletController {
    private WalletService walletService;
    private final String WALLETS = "/wallets";

    @PostMapping(ACCESS_USER + WALLETS + "/add")
    public ResponseEntity<String> addWallet(@RequestBody Wallet wallet){
        System.out.println(wallet);
        try {
            walletService.addWallet(wallet);
            return ResponseEntity.ok("Wallet " + wallet.getAddress() + " has been successfully added");
        } catch(Exception ex){
            return ResponseEntity.badRequest().body("Error occurred during adding wallet " + wallet.getAddress());
        }
    }

    @GetMapping(ACCESS_USER + WALLETS + "/getById")
    public ResponseEntity<Wallet> getWalletByAddress(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(walletService.getById(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(ACCESS_USER + WALLETS + "/getByUsername")
    public ResponseEntity<List<Wallet>> getWalletsByUsername(@RequestParam String name) {
        try {
            return ResponseEntity.ok(walletService.getByUsername(name));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(ACCESS_USER + WALLETS + "/delete")
    public ResponseEntity<String> deleteWallet(@RequestParam Long id) {
        try {
            walletService.deleteById(id);
            return ResponseEntity.ok("Wallet with id " + id + " has been deleted.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(ACCESS_USER + WALLETS + "/update")
    public ResponseEntity<String> updateWallet(@RequestParam Long id, @RequestBody Wallet updatedWallet){
        try {
            walletService.updateById(id, updatedWallet);
            return ResponseEntity.ok("Wallet with id " + id + " has been updated.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during wallet update");
        }
    }
}
