package com.gambit.GamBit.model;

public class DecentralizedNetwork {
    private Long id;
    private String name;
    private String addressRPC;
    private int chainID;
    private String currencyToken;
    private String blockchainExplorer;

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
