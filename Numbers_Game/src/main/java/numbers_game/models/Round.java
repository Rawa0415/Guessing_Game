package numbers_game.models;

public class Round {
	
	private int roundId;
	//private int gameId;
	private String guess;
	private String guessResults;
	
	
	public int getRoundId() {
		return roundId;
	}
	
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	
	public String getGuess() {
		return guess;
	}
	
	public void setGuess(String guess) {
		this.guess = guess;
	}
	
	public String getGuessResults() {
		return guessResults;
	}
	
	public void setGuessResults(String guessResults) {
		this.guessResults = guessResults;
	}
	
	/*public int getGameId() {
		return gameId;
	}
	
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}*/

}
