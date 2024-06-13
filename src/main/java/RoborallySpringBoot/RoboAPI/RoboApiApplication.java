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
	public CommandLineRunner demo(){
		return (args) -> {
			// Create a new game instance
			Game newGame = new Game();
			newGame.setMaxPlayers(2);
			gameController.createGame(newGame);

			// Create a new player
			Player host = new Player();
			host.setName("Host");
			Player client = new Player();
			client.setName("client");
			playerController.createPlayer(host);
			playerController.createPlayer(client);

			// Join the game with the host and client
			gameController.joinGame(newGame.getId(), host.getId());
			gameController.joinGame(newGame.getId(), client.getId());

			// Set the ready state for the player
			host.setState("READY");
			client.setState("READY");
			playerController.updatePlayer(host.getId(), host);
			playerController.updatePlayer(client.getId(), client);

			// Create a move for the Host
			Move hostMove = new Move();
			hostMove.setGameId(newGame.getId());
			hostMove.setPlayerId(host.getId());

			List<String> hostMoves = Arrays.asList("Fwd,", "Fwd", "Turn Right", "Turn Left", "Forward" );
			hostMove.setMoves(hostMoves);
			moveController.createMove(hostMove);

			List<String> hostMoves1 = Arrays.asList("Fwd,", "Back", "Back", "Turn Right", "Forward" );
			hostMove.setMoves(hostMoves1);
			moveController.createMove(hostMove);
			List<String> hostMoves2 = Arrays.asList("Back", "Back", "Back", "Back", "Back" );
			hostMove.setMoves(hostMoves2);
			moveController.createMove(hostMove);

			/*
			// Create a move for the Client
			Move clientMove = new Move();
			clientMove.setGameId(newGame.getId());
			clientMove.setPlayerId(client.getId());

			List<String> clientMoves = Arrays.asList("Fwd,", "Back", "Back", "Turn Right", "Forward" );
			clientMove.setMoves(clientMoves);
			moveController.createMove(clientMove);

			 */

		};
	}
}
