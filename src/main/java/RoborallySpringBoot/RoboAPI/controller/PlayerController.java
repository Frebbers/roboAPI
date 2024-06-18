package RoborallySpringBoot.RoboAPI.controller;

import RoborallySpringBoot.RoboAPI.model.Game;
import RoborallySpringBoot.RoboAPI.model.Player;
import RoborallySpringBoot.RoboAPI.repository.GameRepository;
import RoborallySpringBoot.RoboAPI.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Player> getPlayerById(@PathVariable Long id) {
        return playerRepository.findById(id);
    }

    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return playerRepository.save(player);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player updatedPlayer) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player != null) {
            player.setId(updatedPlayer.getId());
            player.setName(updatedPlayer.getName());
            player.setState(updatedPlayer.getState());
            player.setGameId(updatedPlayer.getGameId());
            return playerRepository.save(player);
        }
        return null;
    }


    @GetMapping("/{playerId}/leave")
    public ResponseEntity<String> leaveGame(@PathVariable Long playerId) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            Long gameId = player.getGameId();

            if (gameId != null) {
                Optional<Game> gameOptional = gameRepository.findById(gameId);
                if (gameOptional.isPresent()) {
                    Game game = gameOptional.get();
                    List<Long> playerIds = game.getPlayerIds();

                    if (playerIds.remove(playerId)) {
                        game.setPlayerIds(playerIds);
                        gameRepository.save(game);
                    }
                }
            }

            // Remove the game ID from the player
            player.setGameId(null);
            playerRepository.save(player);

            return ResponseEntity.ok("Player has left the game");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found");
        }
    }

}
