package com.gambit.GamBit.model.dto;

import lombok.Data;

@Data
public class PlayerResult {
    private Long playerId;
    private Long walletId;
    private Long gameId;
    private Long tokensInput;
    private Long tokensReturn;
    private Boolean isVictory;
    private String errorMessage;

    public PlayerResult(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public PlayerResult(Long playerId,
                        Long walletId,
                        Long gameId,
                        Long tokensInput,
                        Long tokensReturn,
                        Boolean isVictory) {
        this.playerId = playerId;
        this.walletId = walletId;
        this.gameId = gameId;
        this.tokensInput = tokensInput;
        this.tokensReturn = tokensReturn;
        this.isVictory = isVictory;
    }
}
