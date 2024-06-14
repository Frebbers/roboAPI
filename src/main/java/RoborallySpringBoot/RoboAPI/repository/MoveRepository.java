package RoborallySpringBoot.RoboAPI.repository;

import RoborallySpringBoot.RoboAPI.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
    List<Move> findByGameIdAndPlayerId(long gameId, long playerId);
    List<Move> findByGameIdAndTurnIndex(long gameId, int turnIndex);
    List<Move> findByGameIdAndPlayerIdAndTurnIndex(long gameId, long playerId, int turnIndex);
    List<Move> findByGameId(long gameId);
    List<Move> findByPlayerId(long playerId);
    List<Move> findByPlayerIdAndTurnIndex(long playerId, int turnIndex);
    Integer countReadyPlayersByGameIdAndTurnIndex(long gameId, int turnIndex);
}
