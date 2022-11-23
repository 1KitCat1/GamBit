package com.gambit.GamBit.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "game") @Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "tokens_amount")
    LocalDateTime dateTime;

    @Column(name = "game_score")
    Long gameScore;

    @Column(name = "random_salt")
    String randomSalt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="contract_id", referencedColumnName = "id")
    SmartContract smartContract;
}
