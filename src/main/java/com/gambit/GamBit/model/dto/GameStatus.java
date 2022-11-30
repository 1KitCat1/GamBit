package com.gambit.GamBit.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameStatus {
    private Long gameId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double currentMultiplier;
    private Double maxMultiplier;
    private String gameStatus;
    private String errorMessage;

    public GameStatus(Long gameId,
                      LocalDateTime startTime,
                      LocalDateTime endTime,
                      Double currentMultiplier,
                      Double maxMultiplier,
                      String gameStatus) {
        this.gameId = gameId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentMultiplier = currentMultiplier;
        this.maxMultiplier = maxMultiplier;
        this.gameStatus = gameStatus;
    }

    public GameStatus(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
