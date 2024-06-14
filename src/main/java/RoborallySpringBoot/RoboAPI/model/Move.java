package RoborallySpringBoot.RoboAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "game_id") //name is the field they will join on.
    @JsonBackReference
    private Game game;

    //for defining a many-to-one relation to ensure fields maps
    //to the correct columns in the database
    @ManyToOne
    @JoinColumn(name = "player_id")
    // Bidirectional relationship. Tables got messed up without
    //and kept nesting the object inside itself. This avoids the occuring recursion.
    @JsonBackReference
    private Player player;

    private int turn;

    @ElementCollection
    @CollectionTable(name = "move_types", joinColumns = @JoinColumn(name = "move_id"))
    @Column(name = "move_type")
    private List<String> moveTypes;
}
