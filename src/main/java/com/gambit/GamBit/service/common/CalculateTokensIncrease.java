package com.gambit.GamBit.service.common;

public class CalculateTokensIncrease {
    public static Long tokensIncrease(Long tokens, Long milliseconds) {
        // formula that calculate increase of tokens in case of winning
        return (long)(tokens * tokensMultiplier(milliseconds));
    }
    public static double tokensMultiplier(Long milliseconds){
        return round(1 + (double)milliseconds / 8000.0, 2);
    }
    private static double round(double val, int decimals){
        double power10 = Math.pow(10, decimals);
        return Math.round(val * power10) / power10;
    }
}
