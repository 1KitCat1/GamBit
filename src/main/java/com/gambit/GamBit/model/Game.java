package com.gambit.GamBit.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "game") @Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "start_time")
    LocalDateTime startTime;

    @Column(name = "game_score")
    Long gameScore;

//    @Column(name = "game_status")
//    GameStatus gameStatus;
//
    @Column(name = "random_salt")
    String randomSalt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="contract_id", referencedColumnName = "id")
    SmartContract smartContract;
}
//enum GameStatus {
//    NOT_STARTED,
//    IN_PROGRESS,
//    FINISHED
//}
