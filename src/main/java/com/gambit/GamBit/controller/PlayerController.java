package com.gambit.GamBit.controller;

import com.gambit.GamBit.exception.GameNotStartedException;
import com.gambit.GamBit.exception.ObjectNotFoundException;
import com.gambit.GamBit.exception.PlayEndedException;
import com.gambit.GamBit.model.Game;
import com.gambit.GamBit.model.Player;
import com.gambit.GamBit.model.dto.PlayerResult;
import com.gambit.GamBit.service.GameService;
import com.gambit.GamBit.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gambit.GamBit.controller.AccessRolesController.ACCESS_ADMIN;
import static com.gambit.GamBit.controller.AccessRolesController.ACCESS_USER;

@RestController
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final String PLAYERS = "/players";


    @PostMapping(ACCESS_USER + PLAYERS + "/add")
    public ResponseEntity<String> addPlayer(@RequestBody Player player){
        System.out.println(player);
        try {
            playerService.addPlayer(player);
            return ResponseEntity.ok("Player " + player.getId() + " has been successfully added");
        } catch(Exception ex){
            System.out.println(ex.getMessage() + ex.toString());
            return ResponseEntity.badRequest().body("Error occurred during adding player " + player.getId());
        }
    }

    @GetMapping(ACCESS_ADMIN + PLAYERS + "/getById")
    public ResponseEntity<Player> getPlayerById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(playerService.getById(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(ACCESS_USER + PLAYERS + "/getByWallet")
    public ResponseEntity<List<Player>> getPlayerById(@RequestParam String address) {
        try {
            return ResponseEntity.ok(playerService.getByWallet(address));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(ACCESS_ADMIN + PLAYERS + "/delete")
    public ResponseEntity<String> deletePlayer(@RequestParam Long id) {
        try {
            playerService.deleteById(id);
            return ResponseEntity.ok("Player with id " + id + " has been deleted.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(ACCESS_USER + PLAYERS + "/update")
    public ResponseEntity<String> updatePlayer(@RequestParam Long id, @RequestBody Player updatedPlayer){
        try {
            playerService.updateById(id, updatedPlayer);
            return ResponseEntity.ok("Game with id " + id + " has been updated.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during game update");
        }
    }

    @PostMapping(ACCESS_USER + PLAYERS + "/endPlay")
    public ResponseEntity<PlayerResult> endPlay(@RequestParam Long playerId) {
        try {
            return ResponseEntity.ok(playerService.endPlay(playerId));
        } catch (ObjectNotFoundException | PlayEndedException | GameNotStartedException ex) {
            return ResponseEntity.badRequest().body(new PlayerResult(ex.getMessage()));
        }
    }
}
