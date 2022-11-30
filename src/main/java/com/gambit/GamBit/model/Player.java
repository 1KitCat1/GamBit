package com.gambit.GamBit.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "player") @Data
public class Player {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="wallet_id", referencedColumnName = "id")
    Wallet wallet;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="game_id", referencedColumnName = "id")
    Game game;

    @Column(name = "tokens_input")
    Long tokensInput;

    @Column(name = "tokens_return")
    Long tokensReturn;

    @Column(name = "is_victory")
    Boolean isVictory;
}
