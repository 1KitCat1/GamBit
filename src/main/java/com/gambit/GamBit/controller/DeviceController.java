package com.gambit.GamBit.controller;

import com.gambit.GamBit.model.User;
import com.gambit.GamBit.model.dto.UserVerificationStatus;
import com.gambit.GamBit.service.DeviceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class DeviceController {
    private final String DEVICES = "api/device";
    private final DeviceService deviceService;

    @PostMapping(DEVICES + "/authorize")
    public ResponseEntity<Boolean> twoFactorAuthorize(@RequestBody twoFactorAuthorizationInput input
    ) {
        try {
            Boolean isOk = deviceService.authorize(input.securityKey, input.userId);
            if(isOk){
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.badRequest().body(false);
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping(DEVICES + "/getStatus")
    public  ResponseEntity<UserVerificationStatus> getStatus(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(deviceService.checkStatus(userId));
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(new UserVerificationStatus());
        }
    }

    @Data
    private static class twoFactorAuthorizationInput{
        private Long userId;
        private String securityKey;
    }
}
