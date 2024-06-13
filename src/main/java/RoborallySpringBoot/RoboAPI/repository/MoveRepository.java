package RoborallySpringBoot.RoboAPI.repository;

import RoborallySpringBoot.RoboAPI.model.Move;
import RoborallySpringBoot.RoboAPI.model.MoveKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, MoveKey> {
    List<Move> findByRegistry_GameID(Long gameID);
    List<Move> findByRegistry_PlayerID(Long playerID);
}
