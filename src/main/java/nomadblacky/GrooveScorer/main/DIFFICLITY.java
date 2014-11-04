package nomadblacky.GrooveScorer.main;

public enum DIFFICLITY {

	SIMPLE,
	NORMAL,
	HARD,
	EXTRA;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
