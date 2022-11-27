package com.gambit.GamBit.service.common;

import com.gambit.GamBit.exception.ObjectNotFoundException;

import java.security.MessageDigest;

public class Hashing {
    private static String byteArrayToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b: bytes)
            stringBuilder.append(String.format("%02x", b));
        return stringBuilder.toString();
    }

    public static String getHash(String message) {
        try{
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            byte[] hashedMessage = digester.digest(message.getBytes());
            return byteArrayToHex(hashedMessage);
        } catch (Exception ex){
            return "Error occurred during hashing result";
            // TODO : maybe do smth here (there should be no exception actually)
        }
    }
}
