package numbers_game.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import numbers_game.models.Game;
import numbers_game.models.Round;

@Repository
public class gameDBDao implements gameDao{
	
    private final JdbcTemplate jdbcTemplate;
    static String realAnswer;

    @Autowired
    public gameDBDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	@Override
	//@Transactional
	public Game addGame(Game game) {
     final String sql = "INSERT INTO Game(answer, inProgress) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, game.genAnswer());
            statement.setBoolean(2, game.isInProgress());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
	}
	
	@Override
	public List<Round> guess(String guess, int gameId) {
		Game game = getGameById(gameId);
		//final String getAns = "SELECT * FROM game WHERE gameId = ?";
		//Game g = jdbcTemplate.queryForObject(getAns, new gameMapper(), gameId);
		String gameAnswer = realAnswer;
		String guessResults = "";
		int e = 0, p = 0;
		
		for(int i = 0; i < guess.length(); i++ ) {
			if(gameAnswer.charAt(i) == guess.charAt(i)) {
				e++;
			}
			else if (guess.contains(gameAnswer.charAt(i) + "")) {
				p++;
			}
		}
		
		if(e == 4) {
			final String winSQL = "UPDATE game SET inProgress = false WHERE gameId = ?";
			jdbcTemplate.update(winSQL, gameId);
		}
		
		guessResults = "e = " + e + "p = " + p;
		final String insertSQL = "INSERT INTO round(gameId,guess,guessResults) VALUES(?,?,?)";
		jdbcTemplate.update(insertSQL, gameId, guess, guessResults);
		
		final String allRoundsSQL = "SELECT * FROM round WHERE gameId = ? ORDER BY roundId DESC";
		List<Round> rounds = jdbcTemplate.query(allRoundsSQL, new roundMapper(), gameId);
		
		return rounds;
	}

	@Override
	public List<Game> getAllGames() {
		final String sql = "SELECT gameId, answer, inProgress FROM Game";
		 return jdbcTemplate.query(sql, new gameMapper());
	}

	@Override
	public Game getGameById(int gameId) {
		final String sql = "SELECT * FROM Game WHERE gameId = ?";
		return jdbcTemplate.queryForObject(sql, new gameMapper(), gameId);
	}
	
	@Override
	public List<Round> getAllRounds(int gameId) {
		final String sql = "SELECT r.* FROM round r "
				+ " JOIN  game g ON g.gameId = r.gameId "
                + " WHERE r.gameId = ? "
                + "ORDER BY roundId DESC";
		List<Round> rounds = jdbcTemplate.query(sql, new roundMapper(), gameId);
		return rounds;
	}
	
   /* @Override
    public boolean updateGame(Game game) {

        final String sql = "UPDATE game SET "
                + "answer = ?, "
                + "inProgress = ? "
                + "WHERE gameId = ?;";

        return jdbcTemplate.update(sql,
                game.genAnswer(),
                game.isInProgress(),
                game.getGameId()) > 0;
    }*/
	
    private static final class gameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game g = new Game();
            g.setGameId(rs.getInt("gameId"));
            g.setAnswer(rs.getString("answer"));
            g.setInProgress(rs.getBoolean("inProgress"));
            realAnswer = rs.getString("answer");
            if(g.isInProgress()) {
            	g.setAnswer("****");
            }

            return g;
        }
    }
    
    private static final class roundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round r = new Round();
            r.setRoundId(rs.getInt("roundId"));
            r.setGuess(rs.getString("guess"));
            r.setGuessResults(rs.getString("guessResults"));
            return r;
        }
    }

}
