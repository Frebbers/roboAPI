package RoborallySpringBoot.RoboAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "moves")
public class Move {
    @Id
    private MoveKey registry = new MoveKey();

    @Setter
    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> moves;

    public Long getGameId() {
        return registry.getGameID();
    }

    public void setGameId(Long gameId) {
        registry.setGameID(gameId);
    }

    public Long getPlayerId() {
        return registry.getPlayerID();
    }

    public void setPlayerId(Long playerId){
        registry.setPlayerID(playerId);
    }
}


