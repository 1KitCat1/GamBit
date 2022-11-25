package com.gambit.GamBit.controller;

import com.gambit.GamBit.model.Game;
import com.gambit.GamBit.service.GameService;
import com.gambit.GamBit.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gambit.GamBit.controller.AccessRolesController.ACCESS_USER;

@RestController
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final String GAMES = "/games";

    @PostMapping(ACCESS_USER + GAMES + "/add")
    public ResponseEntity<String> addGame(@RequestBody Game game){
        System.out.println(game);
        try {
            gameService.addGame(game);
            return ResponseEntity.ok("Game " + game.getId() + " has been successfully added");
        } catch(Exception ex){
            System.out.println(ex.getMessage() + ex.toString());
            return ResponseEntity.badRequest().body("Error occurred during adding game " + game.getId());
        }
    }

    @GetMapping(ACCESS_USER + GAMES + "/getById")
    public ResponseEntity<Game> getGameById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(gameService.getById(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(ACCESS_USER + GAMES + "/delete")
    public ResponseEntity<String> deleteGame(@RequestParam Long id) {
        try {
            gameService.deleteById(id);
            return ResponseEntity.ok("Game with id " + id + " has been deleted.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(ACCESS_USER + GAMES + "/update")
    public ResponseEntity<String> updateGame(@RequestParam Long id, @RequestBody Game updatedGame){
        try {
            gameService.updateById(id, updatedGame);
            return ResponseEntity.ok("Game with id " + id + " has been updated.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during game update");
        }
    }

    @GetMapping(ACCESS_USER + GAMES + "/getHashedResult")
    public ResponseEntity<String> getHashedResult(@RequestParam Long id){
        try {
            return ResponseEntity.ok(gameService.getHashedResult(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during getting game result");
        }
    }

    @GetMapping(ACCESS_USER + GAMES + "/getResult")
    public ResponseEntity<String> getNotHashedResult(@RequestParam Long id){
        try {
            return ResponseEntity.ok(gameService.getNotHashedResult(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during getting game result");
        }
    }
}
