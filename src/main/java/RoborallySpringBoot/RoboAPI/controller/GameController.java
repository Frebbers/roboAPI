package RoborallySpringBoot.RoboAPI.controller;

import RoborallySpringBoot.RoboAPI.model.Game;
import RoborallySpringBoot.RoboAPI.model.Player;
import RoborallySpringBoot.RoboAPI.repository.GameRepository;
import RoborallySpringBoot.RoboAPI.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Game> getGameById(@PathVariable Long id) {
        return gameRepository.findById(id);
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }

    @PutMapping("/{gameId}")
    public Game updateGame(@PathVariable Long gameId, @RequestBody Game game) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            Game existingGame = gameOptional.get();
            existingGame.setPlayerIds(game.getPlayerIds());
            existingGame.setMaxPlayers(game.getMaxPlayers());
            return gameRepository.save(existingGame);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameRepository.deleteById(id);
    }

    @GetMapping("/{id}/players")
    public List<Player> getPlayersInLobby(@PathVariable Long id) {
        Game game = gameRepository.findById(id).orElse(null);
        if (game != null) {
            List<Player> players = playerRepository.findAll();
            return players.stream()
                    .filter(player -> player.getGameId() != null && player.getGameId().equals(id))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @PutMapping("/{gameId}/join")
    public Game joinGame(@PathVariable Long gameId, @RequestBody Long playerId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        Optional<Player> playerOptional = playerRepository.findById(playerId);

        if (gameOptional.isPresent() && playerOptional.isPresent()) {
            Game game = gameOptional.get();
            Player player = playerOptional.get();

            if (game.getPlayerIds().size() < game.getMaxPlayers()) {
                game.getPlayerIds().add(playerId);
                player.setGameId(game.getId());
                playerRepository.save(player);
                return gameRepository.save(game);
            } else {
                throw new IllegalStateException("The game lobby is full.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game or Player not found");
        }
    }
}
