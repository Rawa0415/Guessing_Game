package numbers_game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import numbers_game.data.gameDao;
import numbers_game.models.Game;
import numbers_game.models.Round;


@RestController
@RequestMapping("/numbers_game")
public class gameController {
	
	@Autowired
	gameDao gDao;
	
	public gameController(gameDao gDao) {
		this.gDao = gDao;
	}
	
	
	@GetMapping("/allGames")
	public List<Game> allGames() {
		return gDao.getAllGames();
	}
	
	@GetMapping("/getGame/{gameId}")
	public ResponseEntity<Game> getGameById(@PathVariable int gameId) {
		Game result = gDao.getGameById(gameId);
		if(result == null) {
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(result);
		
	}
	
	@GetMapping("/allRounds/{gameId}")
	public List<Round> allRoundsForGame(@PathVariable int gameId) {
		return gDao.getAllRounds(gameId);
	}
	
	@PostMapping("/new")
	//@ResponseStatus(HttpStatus.CREATED)
	public Game create(Game game) {
		//game.setAnswer(game.genAnswer());
	    return gDao.addGame(game);
	}
	
	@PostMapping("/getGame/{gameId}/{guess}") 
	public List<Round> guess(@PathVariable String guess, @PathVariable int gameId) {
		if(guess.length() > 4 || guess.length() == 0) {
			throw new IllegalArgumentException(guess +" Is Not A Valid Input. Try Again");
		}
		else {
		return gDao.guess(guess, gameId);
		}
		
	}
	

}
