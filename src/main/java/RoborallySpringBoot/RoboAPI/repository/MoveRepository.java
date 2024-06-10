package RoborallySpringBoot.RoboAPI.repository;


import RoborallySpringBoot.RoboAPI.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveRepository extends JpaRepository<Move, Long> {

}
