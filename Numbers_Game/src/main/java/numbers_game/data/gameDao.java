package numbers_game.data;

import java.util.List;

import org.springframework.http.ResponseEntity;

import numbers_game.models.Game;
import numbers_game.models.Round;



public interface gameDao {
	
	Game addGame(Game game);
	
	List<Game> getAllGames();

	Game getGameById(int gameId);
	
	List<Round> getAllRounds(int gameId);
	
	List<Round> guess(String guess, int gameId);
	
	
	//boolean updateGame(Game game);

}
