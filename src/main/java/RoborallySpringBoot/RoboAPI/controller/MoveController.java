package RoborallySpringBoot.RoboAPI.controller;

import RoborallySpringBoot.RoboAPI.model.Move;
import RoborallySpringBoot.RoboAPI.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/moves")
public class MoveController {

    @Autowired
    private MoveRepository moveRepository;

    @PostMapping
    public Move createMove(@RequestBody Move move) {
        return moveRepository.save(move);
    }

    @GetMapping("/games/{gameId}")
    public List<Move> getMovesByGameId(@PathVariable Long gameId) {
        return moveRepository.findByRegistry_GameID(gameId);
    }

    @GetMapping("/player/{playerId}")
    public List<Move> getMovesByPlayerId(@PathVariable Long playerId) {
        return moveRepository.findByRegistry_PlayerID(playerId);
    }
}
