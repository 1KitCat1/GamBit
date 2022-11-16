package com.gambit.GamBit.controller;

import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.model.DecentralizedNetwork;
import com.gambit.GamBit.model.SmartContract;
import com.gambit.GamBit.service.DecentralizedNetworkService;
import com.gambit.GamBit.service.SmartContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.gambit.GamBit.controller.AccessRolesController.ACCESS_ADMIN;
import static com.gambit.GamBit.controller.AccessRolesController.ACCESS_USER;

@RestController
@RequiredArgsConstructor
public class SmartContractController {
    private final SmartContractService smartContractService;
    private final String CONTRACTS = "/contracts";

    @PostMapping(ACCESS_ADMIN + CONTRACTS + "/add")
    public ResponseEntity<String> addContract(@RequestBody SmartContract contract){
        System.out.println(contract);
        try {
            smartContractService.addContract(contract);
            return ResponseEntity.ok("Contract " + contract.getAddress() + " has been successfully added");
        } catch(Exception ex){
            return ResponseEntity.badRequest().body("Error occurred during adding smart contract " + contract.getAddress());
        }
    }

    @PostMapping(ACCESS_ADMIN + CONTRACTS + "/addNetwork")
    public ResponseEntity<String> setNetwork(@RequestParam Long contractId, @RequestParam Long networkId){
        System.out.println("Adding network to smart contract");
        try {
            SmartContract contract = smartContractService.setNetwork(contractId, networkId);
            return ResponseEntity.ok("Set network at contract " + contract.getAddress());
        } catch(Exception ex){
            return ResponseEntity.badRequest().body("Error occurred during adding network to smart contract ");
        }
    }

    @GetMapping(ACCESS_USER + CONTRACTS + "/getAll")
    public ResponseEntity<List<SmartContract>> getAllContracts(){
        try{
            return ResponseEntity.ok(smartContractService.getAll());
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(ACCESS_USER + CONTRACTS + "/getById")
    public ResponseEntity<SmartContract> getContractById(@RequestParam Long id){
        try{
            return ResponseEntity.ok(smartContractService.getById(id));
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(ACCESS_ADMIN + CONTRACTS + "/delete")
    public ResponseEntity<String> deleteContract(@RequestParam long id) {
        try {
            smartContractService.deleteById(id);
            return ResponseEntity.ok("Contract with id " + id + " has been deleted.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(ACCESS_ADMIN + CONTRACTS + "/update")
    public ResponseEntity<String> updateContract(@RequestParam long id, @RequestBody SmartContract updatedContract){
        try {
            smartContractService.updateById(id, updatedContract);
            return ResponseEntity.ok("Contract with id " + id + " has been updated.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during network update");
        }
    }
}
