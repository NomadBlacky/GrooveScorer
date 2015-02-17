package nomadblacky.GrooveScorer.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdAndDateTime {
	
	private final Integer _id;
	private final Date _date;

	public IdAndDateTime(int id, String dateTimeString) {
		
		this._id = id;
		try {
			this._date = DATE_FORMAT.parse(dateTimeString);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public Integer id() {
		return _id;
	}
	
	public Date date() {
		return _date;
	}
	
	public String formatDate() {
		return DATE_FORMAT.format(date());
	}
	
	public boolean before(Date date) {
		return date().compareTo(date) > 0;
	}
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	public static Date parse(String dateString) {
		try {
			return DATE_FORMAT.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
