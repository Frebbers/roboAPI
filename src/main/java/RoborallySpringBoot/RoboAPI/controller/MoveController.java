package RoborallySpringBoot.RoboAPI.controller;

import RoborallySpringBoot.RoboAPI.model.Command;
import RoborallySpringBoot.RoboAPI.model.Move;
import RoborallySpringBoot.RoboAPI.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/moves")
public class MoveController {

    @Autowired
    private MoveRepository moveRepository;

    @PostMapping
    public ResponseEntity<Move> createMove(@RequestBody Move move) {
        if (Command.areValidCommands(move.getMoveTypes())) {
            Move createdMove = moveRepository.save(move);
            return ResponseEntity.ok(createdMove);
        }
        //TODO check if this breaks the client application
        return ResponseEntity.badRequest().build();
    }

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

    @GetMapping("/game/{gameId}/turn/{turnIndex}")
    public ResponseEntity<List<Move>> getMovesByTurn(@PathVariable Long gameId, @PathVariable int turnIndex) {
        List<Move> moves = moveRepository.findByGameIdAndTurnIndex(gameId, turnIndex);
        return ResponseEntity.ok(moves);
    }

    @GetMapping("/player/{playerId}/turn/{turnIndex}")
    public ResponseEntity<List<Move>> getMovesByPlayerAndTurn(@PathVariable Long playerId, @PathVariable int turnIndex) {
        List<Move> moves = moveRepository.findByPlayerIdAndTurnIndex(playerId, turnIndex);
        return ResponseEntity.ok(moves);
    }

    @GetMapping("/game/{gameId}/player/{playerId}/turn/{turnIndex}")
    public ResponseEntity<Move> getMoveByGamePlayerAndTurn(@PathVariable Long gameId, @PathVariable Long playerId, @PathVariable int turnIndex) {
        Move move = moveRepository.findByGameIdAndPlayerIdAndTurnIndex(gameId, playerId, turnIndex);
        return ResponseEntity.ok(move);
    }

    @GetMapping("/game/{gameId}/turn/{turnIndex}/player-count")
    public ResponseEntity<Integer> getReadyPlayerCountByTurn(@PathVariable long gameId, @PathVariable int turnIndex) {
        Integer count = moveRepository.countReadyPlayersByGameIdAndTurnIndex(gameId, turnIndex);
        return ResponseEntity.ok(count);
    }

}


