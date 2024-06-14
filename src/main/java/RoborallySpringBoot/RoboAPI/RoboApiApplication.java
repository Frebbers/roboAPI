package RoborallySpringBoot.RoboAPI;

import RoborallySpringBoot.RoboAPI.controller.GameController;
import RoborallySpringBoot.RoboAPI.controller.MoveController;
import RoborallySpringBoot.RoboAPI.controller.PlayerController;
import RoborallySpringBoot.RoboAPI.model.Game;
import RoborallySpringBoot.RoboAPI.model.Move;
import RoborallySpringBoot.RoboAPI.model.Player;
import RoborallySpringBoot.RoboAPI.repository.GameRepository;
import RoborallySpringBoot.RoboAPI.repository.MoveRepository;
import RoborallySpringBoot.RoboAPI.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RoboApiApplication {

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private MoveRepository moveRepository;

	@Autowired
	private GameController gameController;
	@Autowired
	private PlayerController playerController;
	@Autowired
	private MoveController moveController;

	public static void main(String[] args) {
		SpringApplication.run(RoboApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			// Create a new game instance
			Game newGame = new Game();
			newGame.setMaxPlayers(2);
			newGame = gameController.createGame(newGame);

			// Create new players
			Player host = new Player();
			host.setName("Host");
			host = playerController.createPlayer(host);

			Player client = new Player();
			client.setName("Client");
			client = playerController.createPlayer(client);

			// Join the game with the host and client
			gameController.joinGame(newGame.getId(), host.getId());
			gameController.joinGame(newGame.getId(), client.getId());

			// Set the ready state for the players
			host.setState("READY");
			client.setState("READY");
			playerController.updatePlayer(host.getId(), host);
			playerController.updatePlayer(client.getId(), client);

			// Create moves for the Host
			createMove(newGame.getId(), host.getId(), 1, Arrays.asList("Fwd", "Fwd", "Turn Right", "Turn Left", "Forward"));
			createMove(newGame.getId(), host.getId(), 2, Arrays.asList("Fwd", "Back", "Back", "Turn Right", "Forward"));

			// Create moves for the Client
			createMove(newGame.getId(), client.getId(), 1, Arrays.asList("Fwd", "Back", "Back", "Turn Right", "Forward"));
			createMove(newGame.getId(), client.getId(), 2, Arrays.asList("Fwd", "Back", "Back", "Turn Right", "Forward"));
		};
	}

	private void createMove(Long gameId, Long playerId, int turn, List<String> moveTypes) {
		Move move = new Move();
		move.setGameId(gameId);
		move.setPlayerId(playerId);
		move.setTurn(turn);
		move.setMoveTypes(moveTypes);
		moveController.createMove(move);
	}
}
