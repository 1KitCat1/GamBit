package com.gambit.GamBit.model;

import javax.persistence.*;

@Entity(name = "smart_contract")
public class SmartContract {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

//    @Column(columnDefinition="varchar(25500)")
    // TODO: change db column type to handle larger data
    private String ABI;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="decentralized_network_id", referencedColumnName = "id")
    private DecentralizedNetwork decentralizedNetwork;

    public SmartContract() { }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DecentralizedNetwork getDecentralizedNetwork() {
        return decentralizedNetwork;
    }

    public void setDecentralizedNetwork(DecentralizedNetwork decentralizedNetwork) {
        this.decentralizedNetwork = decentralizedNetwork;
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
                ", address='" + address + '\'' +
                ", ABI='" + ABI + '\'' +
                ", decentralizedNetwork=" + decentralizedNetwork +
                '}';
    }
}
