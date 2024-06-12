package RoborallySpringBoot.RoboAPI;

import RoborallySpringBoot.RoboAPI.controller.GameController;
import RoborallySpringBoot.RoboAPI.controller.MoveController;
import RoborallySpringBoot.RoboAPI.controller.PlayerController;
import RoborallySpringBoot.RoboAPI.model.Game;
import RoborallySpringBoot.RoboAPI.model.Player;
import RoborallySpringBoot.RoboAPI.repository.GameRepository;
import RoborallySpringBoot.RoboAPI.repository.MoveRepository;
import RoborallySpringBoot.RoboAPI.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
			newGame.setBoardId(1L);
			newGame.setMaxPlayers(4);
			gameController.createGame(newGame);

			// Create a new player
			Player newPlayer = new Player();
			newPlayer.setName("Rekt");
			playerController.createPlayer(newPlayer);

			// Join the game with the new player
			gameController.joinGame(newGame.getId(), newPlayer.getId());

			System.out.println("Players in lobby: " + gameController.getPlayersInLobby(1L).size());

			// Set the ready state for the player
			playerController.updateReadyState(newPlayer.getId(), "not_ready");
		};
	}
}
