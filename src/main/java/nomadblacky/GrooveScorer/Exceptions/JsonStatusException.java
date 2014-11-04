package nomadblacky.GrooveScorer.Exceptions;

public class JsonStatusException extends MyPageClientException {

	public JsonStatusException() {
		super("JSONステータスが不正");
	}
}
