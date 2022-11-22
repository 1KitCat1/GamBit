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

    @Column(name = "tokens_amount")
    Long tokensAmount;

    @Column(name = "finish_score")
    Long finishScore;
}
