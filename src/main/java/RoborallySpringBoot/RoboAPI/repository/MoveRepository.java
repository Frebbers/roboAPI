package RoborallySpringBoot.RoboAPI.repository;

import RoborallySpringBoot.RoboAPI.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
    List<Move> findByGameIdAndPlayerId(long gameId, long playerId);
    List<Move> findByGameIdAndTurn(long gameId, int turn);
    List<Move> findByGameIdAndPlayerIdAndTurn(long gameId, long playerId, int turn);
    List<Move> findByGameId(long gameId);
    List<Move> findByPlayerId(long playerId);
    List<Move> findByPlayerIdAndTurn(long playerId, int turn);
}
