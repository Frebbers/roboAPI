package RoborallySpringBoot.RoboAPI.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
public class MoveKey implements Serializable {
    private Long gameID;
    private Long playerID;
    private Integer turnIndex;

    // Default constructor
    public MoveKey() {}

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
