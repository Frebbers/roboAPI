package RoborallySpringBoot.RoboAPI.repository;

import RoborallySpringBoot.RoboAPI.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
