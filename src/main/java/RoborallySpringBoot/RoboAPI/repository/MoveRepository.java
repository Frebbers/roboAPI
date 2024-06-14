package RoborallySpringBoot.RoboAPI.repository;

import RoborallySpringBoot.RoboAPI.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
    List<Move> findByGame_IdAndPlayer_Id(Long gameId, Long playerId);
    List<Move> findByGame_IdAndTurn(Long gameId, int turn);
    List<Move> findByGame_IdAndPlayer_IdAndTurn(Long gameId, Long playerId, int turn);
    List<Move> findByGame_Id(Long gameId);
    List<Move> findByPlayer_Id(Long playerId);
    List<Move> findByPlayer_IdAndTurn(Long playerId, int turn);
}