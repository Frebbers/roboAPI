package RoborallySpringBoot.RoboAPI.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MoveKey implements Serializable {
    private Long gameID;
    private Long playerID;

    // Default constructor
    public MoveKey() {}

    // Constructor with parameters
    public MoveKey(Long gameID, Long playerID) {
        this.gameID = gameID;
        this.playerID = playerID;
    }

    // Getters and Setters
    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public Long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Long playerID) {
        this.playerID = playerID;
    }

    // hashCode and equals methods must be overridden for composite key
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gameID == null) ? 0 : gameID.hashCode());
        result = prime * result + ((playerID == null) ? 0 : playerID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        MoveKey other = (MoveKey) obj;
        if (gameID == null) {
            if (other.gameID != null)
                return false;
        } else if (!gameID.equals(other.gameID))
            return false;
        if (playerID == null) {
            if (other.playerID != null)
                return false;
        } else if (!playerID.equals(other.playerID))
            return false;
        return true;
    }
}
