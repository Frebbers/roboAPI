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
    private Long gameID;
    @Column(unique = true)
    private Long playerID;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> moves;
}

