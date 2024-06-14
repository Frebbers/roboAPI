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
    private long gameId;

    @Column(name = "player_id")
    private long playerId;

    private int turn;

    @ElementCollection
    @CollectionTable(name = "move_types", joinColumns = @JoinColumn(name = "move_id"))
    @Column(name = "move_type")
    private List<String> moveTypes;
}
