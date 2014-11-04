package nomadblacky.GrooveScorer.main;

public abstract class Score {

	protected int score, chain, playCount, noMissCount, fullChainCount;
	protected String rate, status;

	public Score() {
		
		score = chain = playCount = noMissCount = fullChainCount = 0;
		rate = status = "-";
	}
	
	public int getScore() {
		return score;
	}
	public int getChain() {
		return chain;
	}
	public int getPlayCount() {
		return playCount;
	}
	public int getNoMissCount() {
		return noMissCount;
	}
	public int getFullChainCount() {
		return fullChainCount;
	}
	public String getRate() {
		return rate;
	}
	public String getStatus() {
		return status;
	}
	
	
	public void setScore(int score) {
		this.score = score;
	}
	public void setChain(int chain) {
		this.chain = chain;
	}
	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}
	public void setNoMissCount(int noMissCount) {
		this.noMissCount = noMissCount;
	}
	public void setFullChainCount(int fullChainCount) {
		this.fullChainCount = fullChainCount;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}

class SimpleScore extends Score {

	final private static String difficlity = "simple";
}

class NormalScore extends Score {
	
	final private static String difficlity = "normal";
}

class HardScore extends Score {
	
	final private static String difficlity = "hard";
}

class ExtraScore extends Score {
	
	final private static String difficlity = "extra";	
}