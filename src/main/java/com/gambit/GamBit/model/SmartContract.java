package com.gambit.GamBit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "smart_contract")
public class SmartContract {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long decentralizedNetworkID;
    private String address;
    private String ABI;

    public SmartContract() { }

    public SmartContract(Long id, Long decentralizedNetworkID, String address, String ABI) {
        this.id = id;
        this.decentralizedNetworkID = decentralizedNetworkID;
        this.address = address;
        this.ABI = ABI;
    }

    public SmartContract(Long decentralizedNetworkID, String address, String ABI) {
        this.decentralizedNetworkID = decentralizedNetworkID;
        this.address = address;
        this.ABI = ABI;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDecentralizedNetworkID() {
        return decentralizedNetworkID;
    }

    public void setDecentralizedNetworkID(Long decentralizedNetworkID) {
        this.decentralizedNetworkID = decentralizedNetworkID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getABI() {
        return ABI;
    }

    public void setABI(String ABI) {
        this.ABI = ABI;
    }

    @Override
    public String toString() {
        return "SmartContract{" +
                "id=" + id +
                ", decentralizedNetworkID=" + decentralizedNetworkID +
                ", address='" + address + '\'' +
                ", ABI='" + ABI + '\'' +
                '}';
    }
}
