package com.gambit.GamBit.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "wallet") @Data
public class Wallet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="decentralized_network_id", referencedColumnName = "id")
    private DecentralizedNetwork network;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
}
