package RoborallySpringBoot.RoboAPI.controller;

import RoborallySpringBoot.RoboAPI.model.Game;
import RoborallySpringBoot.RoboAPI.model.Player;
import RoborallySpringBoot.RoboAPI.repository.GameRepository;
import RoborallySpringBoot.RoboAPI.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        Game game = gameRepository.findById(id).orElse(null);
        if (game != null) {
            game.setBoardId(gameDetails.getBoardId());
            game.setMaxPlayers(gameDetails.getMaxPlayers());

            return gameRepository.save(game);
        }
        return null;
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
                    .filter(player -> player.getGame().getId().equals(id))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @PostMapping("/{gameId}/join")
    public Game joinGame(@PathVariable Long gameId, @RequestBody Long playerId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        Optional<Player> playerOptional = playerRepository.findById(playerId);

        if (gameOptional.isPresent() && playerOptional.isPresent()) {
            Game game = gameOptional.get();
            Player player = playerOptional.get();

            if (game.getPlayerIds().size() < game.getMaxPlayers()) {
                game.getPlayerIds().add(playerId);
                player.setGame(game);
                playerRepository.save(player);
                return gameRepository.save(game);
            } else {
                throw new IllegalStateException("The game lobby is full.");
            }
        }
        return null;
    }
}
