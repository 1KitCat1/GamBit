package com.gambit.GamBit.model.dto;

import lombok.Data;

@Data
public class JoinGame {
    private Long walletId;
    private Long gameId;
    private Long tokensInput;
    private Long playerId;
    private String errorMessage;

    public JoinGame(Long walletId, Long gameId, Long tokensInput) {
        this.walletId = walletId;
        this.gameId = gameId;
        this.tokensInput = tokensInput;
    }

    public JoinGame(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
