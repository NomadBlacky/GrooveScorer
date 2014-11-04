package nomadblacky.GrooveScorer.Exceptions;

public class MyPageAccessFailedException extends MyPageClientException {

	public MyPageAccessFailedException(int httpStatusCode) {
		super("マイページへのアクセスに失敗(HTTPStatusCode : " + httpStatusCode + ")");
	}
}
