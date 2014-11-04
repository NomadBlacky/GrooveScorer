package nomadblacky.GrooveScorer.main;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

	public static JSONObject flatten(JSONObject object) {
		return flatten2(object, null, null);
	}
	
	private static JSONObject flatten2(JSONObject object, JSONObject flattened, String parent) {
		
		if(flattened == null) {
			flattened = new JSONObject();
		}
		if(parent == null) {
			parent = "";
		}
		Iterator<?> keys = object.keys();
		while (keys.hasNext()) {
			String key = (String)keys.next();
			try {
				if(object.get(key) instanceof JSONObject) {
					flatten2(object.getJSONObject(key), flattened, key + ".");
				}
				else {
					flattened.put(parent + key, object.get(key));
				}
			}
			catch(JSONException e){
				System.err.println(e);
			}
		}
		return flattened;
	}
}
