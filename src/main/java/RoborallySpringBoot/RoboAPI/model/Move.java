package RoborallySpringBoot.RoboAPI.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "moves")
@Setter
@Getter
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MoveKey registry = new MoveKey();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> moves;

    public void setGameId(Long gameId) {
        registry.setGameID(gameId);
    }

    public void setPlayerId(Long playerId){
        registry.setPlayerID(playerId);
    }

    public void setTurnId(Integer turnId){
        registry.setTurnId(turnId);
    }
}


