package com.gambit.GamBit.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity (name = "decentralized_network")
public class DecentralizedNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String addressRPC;
    private int chainID;
    private String currencyToken;
    private String blockchainExplorer;

    @OneToMany(mappedBy = "decentralizedNetwork")
    private Set<SmartContract> smartContracts= new HashSet<>();

    public DecentralizedNetwork() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressRPC() {
        return addressRPC;
    }

    public void setAddressRPC(String addressRPC) {
        this.addressRPC = addressRPC;
    }

    public int getChainID() {
        return chainID;
    }

    public void setChainID(int chainID) {
        this.chainID = chainID;
    }

    public String getCurrencyToken() {
        return currencyToken;
    }

    public void setCurrencyToken(String currencyToken) {
        this.currencyToken = currencyToken;
    }

    public String getBlockchainExplorer() {
        return blockchainExplorer;
    }

    public void setBlockchainExplorer(String blockchainExplorer) {
        this.blockchainExplorer = blockchainExplorer;
    }

    public Set<SmartContract> getSmartContracts() {
        return smartContracts;
    }

    public void setSmartContracts(Set<SmartContract> smartContracts) {
        this.smartContracts = smartContracts;
    }

    @Override
    public String toString() {
        return "DecentralizedNetwork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressRPC='" + addressRPC + '\'' +
                ", chainID=" + chainID +
                ", currencyToken='" + currencyToken + '\'' +
                ", blockchainExplorer='" + blockchainExplorer + '\'' +
                '}';
    }
}
