package RoborallySpringBoot.RoboAPI.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message ="Have to specify a name.")
    private String name;
    private Long boardId;
    @Min(value = 1, message = "maxPlayers must be at least 1")
    private int maxPlayers;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> playerIds;
}

