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
			gameController.createGame(newGame);

			// Create a new player
			Player newPlayer = new Player();
			playerController.createPlayer(newPlayer);
		};
	}
}
