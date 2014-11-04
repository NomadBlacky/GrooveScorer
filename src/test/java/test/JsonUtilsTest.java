package test;

import static org.junit.Assert.*;
import nomadblacky.GrooveScorer.main.JsonUtils;

import org.json.JSONObject;
import org.junit.Test;

public class JsonUtilsTest {

	@Test
	public void flattenTest() {
		
		JSONObject object = new JSONObject("{ 'hoge' : { 'foo':1, 'bar':2 }, 'fuga':3 }");
		JSONObject flattened = JsonUtils.flatten(object);
		
		System.out.println(flattened.toString());
	}

	@Test
	public void flattenTest2() {
		
		String jsonString = "{'status':0,'music_detail':{'music_id':'77','music_title':'\u5343\u672c\u685c','artist':'\u9ed2\u3046\u3055\uff30','skin_name':'flower','ex_flag':1,'simple_result_data':{'rating':'S++','no_miss':1,'full_chain':0,'play_count':1,'is_clear_mark':false,'is_failed_mark':false,'music_level':'SIMPLE','score':992132,'max_chain':835,'adlib':13},'normal_result_data':{'rating':'S+','no_miss':1,'full_chain':0,'play_count':1,'is_clear_mark':false,'is_failed_mark':false,'music_level':'NORMAL','score':974407,'max_chain':1084,'adlib':7},'hard_result_data':{'rating':'S+','no_miss':1,'full_chain':0,'play_count':4,'is_clear_mark':false,'is_failed_mark':false,'music_level':'HARD','score':958706,'max_chain':1504,'adlib':8},'extra_result_data':{'rating':'S','no_miss':0,'full_chain':0,'play_count':7,'is_clear_mark':true,'is_failed_mark':false,'music_level':'EXTRA','score':914770,'max_chain':1446,'adlib':8}}}";
		JSONObject object = new JSONObject(jsonString).getJSONObject("music_detail");
		JSONObject flattened = JsonUtils.flatten(object);
		
		System.out.println(flattened.toString());
	}
}
