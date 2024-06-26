package RoborallySpringBoot.RoboAPI.controller;

import RoborallySpringBoot.RoboAPI.model.Command;
import RoborallySpringBoot.RoboAPI.model.Move;
import RoborallySpringBoot.RoboAPI.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing move-related operations.
 */
@RestController
@RequestMapping("api/moves")
public class MoveController {

    @Autowired
    private MoveRepository moveRepository;

    /**
     * Creates a new move.
     *
     * @param move the move to create
     * @return the created move, or a bad request response if the commands are invalid
     */
    @PostMapping
    public ResponseEntity<Move> createMove(@RequestBody Move move) {
        if (Command.areValidCommands(move.getMoveTypes())) {
            Move createdMove = moveRepository.save(move);
            return ResponseEntity.ok(createdMove);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates an existing move.
     *
     * @param updatedMove the updated move data
     * @return the updated move, or a bad request or not found response if applicable
     */
    @PutMapping("/update")
    public ResponseEntity<Move> updateMove(@RequestBody Move updatedMove) {
        if (updatedMove.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Move> existingMoveOptional = moveRepository.findById(updatedMove.getId());
        if (!existingMoveOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Move existingMove = existingMoveOptional.get();
        existingMove.setGameId(updatedMove.getGameId());
        existingMove.setPlayerId(updatedMove.getPlayerId());
        existingMove.setTurnIndex(updatedMove.getTurnIndex());
        existingMove.setMoveTypes(updatedMove.getMoveTypes());

        moveRepository.save(existingMove);
        return ResponseEntity.ok(existingMove);
    }

    /**
     * Retrieves moves by game ID.
     *
     * @param gameId the ID of the game
     * @return a list of moves for the specified game
     */
    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Move>> getMovesByGame(@PathVariable long gameId) {
        List<Move> moves = moveRepository.findByGameId(gameId);
        return ResponseEntity.ok(moves);
    }

    /**
     * Retrieves moves by player ID.
     *
     * @param playerId the ID of the player
     * @return a list of moves for the specified player
     */
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Move>> getMovesByPlayer(@PathVariable Long playerId) {
        List<Move> moves = moveRepository.findByPlayerId(playerId);
        return ResponseEntity.ok(moves);
    }

    /**
     * Retrieves moves by game ID and player ID.
     *
     * @param gameId the ID of the game
     * @param playerId the ID of the player
     * @return a list of moves for the specified game and player
     */
    @GetMapping("/game/{gameId}/player/{playerId}")
    public ResponseEntity<List<Move>> getMovesByGameAndPlayer(@PathVariable Long gameId, @PathVariable Long playerId) {
        List<Move> moves = moveRepository.findByGameIdAndPlayerId(gameId, playerId);
        return ResponseEntity.ok(moves);
    }

    /**
     * Retrieves moves by game ID and turn index.
     *
     * @param gameId the ID of the game
     * @param turnIndex the index of the turn
     * @return a list of moves for the specified game and turn index
     */
    @GetMapping("/game/{gameId}/turn/{turnIndex}")
    public ResponseEntity<List<Move>> getMovesByTurn(@PathVariable Long gameId, @PathVariable int turnIndex) {
        List<Move> moves = moveRepository.findByGameIdAndTurnIndex(gameId, turnIndex);
        return ResponseEntity.ok(moves);
    }

    /**
     * Retrieves moves by player ID and turn index.
     *
     * @param playerId the ID of the player
     * @param turnIndex the index of the turn
     * @return a list of moves for the specified player and turn index
     */
    @GetMapping("/player/{playerId}/turn/{turnIndex}")
    public ResponseEntity<List<Move>> getMovesByPlayerAndTurn(@PathVariable Long playerId, @PathVariable int turnIndex) {
        List<Move> moves = moveRepository.findByPlayerIdAndTurnIndex(playerId, turnIndex);
        return ResponseEntity.ok(moves);
    }

    /**
     * Retrieves a move by game ID, player ID, and turn index.
     *
     * @param gameId the ID of the game
     * @param playerId the ID of the player
     * @param turnIndex the index of the turn
     * @return the move for the specified game, player, and turn index
     */
    @GetMapping("/game/{gameId}/player/{playerId}/turn/{turnIndex}")
    public ResponseEntity<Move> getMoveByGamePlayerAndTurn(@PathVariable Long gameId, @PathVariable Long playerId, @PathVariable int turnIndex) {
        Move move = moveRepository.findByGameIdAndPlayerIdAndTurnIndex(gameId, playerId, turnIndex);
        return ResponseEntity.ok(move);
    }

    /**
     * Retrieves the count of ready players for a specific game and turn index.
     *
     * @param gameId the ID of the game
     * @param turnIndex the index of the turn
     * @return the count of ready players for the specified game and turn index
     */
    @GetMapping("/game/{gameId}/turn/{turnIndex}/player-count")
    public ResponseEntity<Integer> getReadyPlayerCountByTurn(@PathVariable long gameId, @PathVariable int turnIndex) {
        Integer count = moveRepository.countReadyPlayersByGameIdAndTurnIndex(gameId, turnIndex);
        return ResponseEntity.ok(count);
    }
}
