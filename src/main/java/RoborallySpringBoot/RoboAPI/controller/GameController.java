package RoborallySpringBoot.RoboAPI.controller;

import RoborallySpringBoot.RoboAPI.model.Game;
import RoborallySpringBoot.RoboAPI.model.Player;
import RoborallySpringBoot.RoboAPI.repository.GameRepository;
import RoborallySpringBoot.RoboAPI.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing game-related operations.
 */
@RestController
@RequestMapping("api/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Retrieves all games.
     *
     * @return a list of all games
     */
    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param id the ID of the game to retrieve
     * @return the game with the specified ID, if found
     */
    @GetMapping("/{id}")
    public Optional<Game> getGameById(@PathVariable Long id) {
        return gameRepository.findById(id);
    }

    /**
     * Creates a new game.
     *
     * @param game the game to create
     * @return the created game
     */
    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }

    /**
     * Updates an existing game.
     *
     * @param gameId the ID of the game to update
     * @param game the updated game data
     * @return the updated game
     * @throws ResponseStatusException if the game is not found
     */
    @PutMapping("/{gameId}")
    public Game updateGame(@PathVariable Long gameId, @RequestBody Game game) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            Game existingGame = gameOptional.get();
            existingGame.setPlayerIds(game.getPlayerIds());
            existingGame.setMaxPlayers(game.getMaxPlayers());
            existingGame.setGameState(game.getGameState());
            return gameRepository.save(existingGame);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    }

    /**
     * Deletes a game by its ID.
     *
     * @param id the ID of the game to delete
     */
    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameRepository.deleteById(id);
    }

    /**
     * Disposes of games that have no players, scheduled to run at fixed intervals.
     */
    @Scheduled(fixedRate = 60000L)
    void disposeEmptyGames() {
        List<Game> games = getAllGames();
        games = games.stream().filter(g -> g.getPlayerIds().isEmpty()).collect(Collectors.toList());
        for (Game game : games) {
            deleteGame(game.getId());
            System.out.println("Deleted game: " + game.getId());
        }
    }

    /**
     * Retrieves all players in a specific game lobby.
     *
     * @param id the ID of the game
     * @return a list of players in the game lobby, or null if the game is not found
     */
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

    /**
     * Allows a player to join a game.
     *
     * @param gameId the ID of the game to join
     * @param playerId the ID of the player joining the game
     * @return the updated game with the player added
     * @throws ResponseStatusException if the game or player is not found
     * @throws IllegalStateException if the game lobby is full
     */
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
