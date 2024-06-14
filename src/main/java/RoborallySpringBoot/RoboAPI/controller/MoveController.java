package RoborallySpringBoot.RoboAPI.controller;

import RoborallySpringBoot.RoboAPI.model.Move;
import RoborallySpringBoot.RoboAPI.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/moves")
public class MoveController {

    @Autowired
    private MoveRepository moveRepository;

    @PostMapping
    public ResponseEntity<Move> createMove(@RequestBody Move move) {
        Move createdMove = moveRepository.save(move);
        return ResponseEntity.ok(createdMove);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Move>> getMovesByGame(@PathVariable long gameId) {
        List<Move> moves = moveRepository.findByGameId(gameId);
        return ResponseEntity.ok(moves);
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Move>> getMovesByPlayer(@PathVariable Long playerId) {
        List<Move> moves = moveRepository.findByPlayerId(playerId);
        return ResponseEntity.ok(moves);
    }

    @GetMapping("/game/{gameId}/player/{playerId}")
    public ResponseEntity<List<Move>> getMovesByGameAndPlayer(@PathVariable Long gameId, @PathVariable Long playerId) {
        List<Move> moves = moveRepository.findByGameIdAndPlayerId(gameId, playerId);
        return ResponseEntity.ok(moves);
    }

    @GetMapping("/game/{gameId}/turn/{turn}")
    public ResponseEntity<List<Move>> getMovesByTurn(@PathVariable Long gameId, @PathVariable int turn) {
        List<Move> moves = moveRepository.findByGameIdAndTurn(gameId, turn);
        return ResponseEntity.ok(moves);
    }

    @GetMapping("/player/{playerId}/turn/{turn}")
    public ResponseEntity<List<Move>> getMovesByPlayerAndTurn(@PathVariable Long playerId, @PathVariable int turn) {
        List<Move> moves = moveRepository.findByPlayerIdAndTurn(playerId, turn);
        return ResponseEntity.ok(moves);
    }

    @GetMapping("/game/{gameId}/player/{playerId}/turn/{turn}")
    public ResponseEntity<List<Move>> getMovesByGamePlayerAndTurn(@PathVariable Long gameId, @PathVariable Long playerId, @PathVariable int turn) {
        List<Move> moves = moveRepository.findByGameIdAndPlayerIdAndTurn(gameId, playerId, turn);
        return ResponseEntity.ok(moves);
    }
}
