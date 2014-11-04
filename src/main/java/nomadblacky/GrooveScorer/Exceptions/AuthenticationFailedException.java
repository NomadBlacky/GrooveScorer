package nomadblacky.GrooveScorer.Exceptions;

public class AuthenticationFailedException extends MyPageClientException {
	
	private String nesicaID;
	
	private String playerName;

	public AuthenticationFailedException() {
		super("認証に失敗しました。入力された情報が間違っているか、認証を止められている可能性があります。");
	}
	
	public AuthenticationFailedException(String nesicaID, String playerName) {
		super("認証に失敗しました。入力された情報が間違っているか、認証を止められている可能性があります。");
		this.nesicaID = nesicaID;
		this.playerName = playerName;
	}
	
	public void setNesicaID(String nesicaID) {
		this.nesicaID = nesicaID;
	}
	
	public String getNesicaID() {
		return nesicaID;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getPlayerName() {
		return playerName;
	}
}
