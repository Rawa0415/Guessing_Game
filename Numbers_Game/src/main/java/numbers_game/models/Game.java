package numbers_game.models;

import java.util.Random;

public class Game {
	
	private int gameId;
	private String answer;
	private boolean inProgress = true;
	
	
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isInProgress() {
		return inProgress;
	}
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}
	public String genAnswer() {
		int[] randNums = new int[4];
		String strAnswer = "";
		boolean dupes = true;
		
		
		do {
		for(int i = 0; i < randNums.length; i++) {
			Random rand = new Random();
			randNums[i] = rand.nextInt(10);
			strAnswer = strAnswer + Integer.toString(randNums[i]);
		}
		
		String[] nums = strAnswer.split("");
			
			if(nums[0].equalsIgnoreCase("0")
					|| nums[0].equalsIgnoreCase(nums[1]) || nums[0].equalsIgnoreCase(nums[2]) || nums[0].equalsIgnoreCase(nums[3])
					|| nums[1].equalsIgnoreCase(nums[2]) || nums[1].equalsIgnoreCase(nums[3])
					|| nums[2].equalsIgnoreCase(nums[3])) {
				
				strAnswer = "";
			}
			else {
				dupes = false;
			}
		}while(dupes);
		return strAnswer;
		
	}

}
