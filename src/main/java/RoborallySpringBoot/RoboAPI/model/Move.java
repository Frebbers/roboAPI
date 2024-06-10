package RoborallySpringBoot.RoboAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String register1;
    private String register2;
    private String register3;
    private String register4;
    private String register5;
}

