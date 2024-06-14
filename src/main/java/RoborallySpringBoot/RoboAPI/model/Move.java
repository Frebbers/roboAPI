package RoborallySpringBoot.RoboAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "moves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_id")
    private long gameId; // Store only gameId as a primitive long

    @Column(name = "player_id")
    private long playerId; // Store only playerId as a primitive long

    private int turn;

    @ElementCollection
    @CollectionTable(name = "move_types", joinColumns = @JoinColumn(name = "move_id"))
    @Column(name = "move_type")
    private List<String> moveTypes;
}
