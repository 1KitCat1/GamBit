package com.gambit.GamBit.controller;

import com.gambit.GamBit.model.dto.UserVerificationStatus;
import com.gambit.GamBit.service.DeviceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class DeviceController {
    private final String DEVICES = "api/device";
    private final DeviceService deviceService;

    @PostMapping(DEVICES + "/authenticateWithBody")
    public ResponseEntity<Boolean> twoFactorAuthenticate(@RequestBody twoFactorAuthenticationInput input
    ) {
        try {
            Boolean isOk = deviceService.authenticate(input.securityKey, input.userId);
            if(isOk){
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.badRequest().body(false);
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping(DEVICES + "/authenticate")
    public ResponseEntity<Boolean> twoFactorAuthenticateParams(@RequestParam Long userId,
                                                               @RequestParam String securityKey
    ) {
        try {
            Boolean isOk = deviceService.authenticate(securityKey, userId);
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

    @PostMapping(DEVICES + "/decline")
    public ResponseEntity<Boolean> declineAuthentication(@RequestParam Long userId) {
        return ResponseEntity.ok(deviceService.declineAuthentication(userId));
    }

    @Data
    private static class twoFactorAuthenticationInput {
        private Long userId;
        private String securityKey;
    }
}
