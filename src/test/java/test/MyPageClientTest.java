package test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import nomadblacky.GrooveScorer.Exceptions.AuthenticationFailedException;
import nomadblacky.GrooveScorer.Exceptions.JsonStatusException;
import nomadblacky.GrooveScorer.Exceptions.MyPageClientException;
import nomadblacky.GrooveScorer.main.MyPageClient;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import category.MyPageFailedTestCategory;
import category.MyPageSuccessTestCategory;

public class MyPageClientTest {
	
	private static String succeedJson =
			"{\"status\":0,\"music_detail\":{\"music_title\":\"\u77b3\u306eLAZhWARD (\u30d5\u30ed\u30f3\u30c6\u30a3\u30a2S\u30fb\u65e7\u6e0b\u8c37\u8352\u5ec3\u5730\u533a\u30b9\u30c6\u30fc\u30b8BGM)\",\"artist\":\"\u30e1\u30a4\u30ea\u30a2\",\"skin_name\":\"none\",\"simple_result_data\":{\"rating\":\"S+\",\"no_miss\":1,\"full_chain\":0,\"music_level\":\"SIMPLE\",\"score\":953199,\"max_chain\":1562,\"adlib\":17},\"normal_result_data\":{\"rating\":\"S+\",\"no_miss\":1,\"full_chain\":0,\"music_level\":\"NORMAL\",\"score\":983471,\"max_chain\":1941,\"adlib\":22},\"hard_result_data\":{\"rating\":\"S+\",\"no_miss\":3,\"full_chain\":0,\"music_level\":\"HARD\",\"score\":967530,\"max_chain\":2236,\"adlib\":22},\"extra_result_data\":null}}";
	
	private static String failedJson =
			"{\"status\":1,\"music_detail\":null}";
	
	/**
	 * 認証に成功させるテスト
	 * @throws Exception
	 */
	@Category(MyPageSuccessTestCategory.class)
	@Test
	public void authSuccessTest() throws Exception {
		
		MyPageClient client = new MyPageClient("7020392000535457", "BLACKYv2");
		boolean actual = client.doAuth();
		
		assertTrue(actual);
	}
	
	/**
	 * 認証に失敗させるテスト
	 * @throws MyPageClientException 
	 * @throws Exception
	 */
	@Category(MyPageFailedTestCategory.class)
	@Test(expected = MyPageClientException.class)
	public void authFailedTest() throws Exception {
		
		MyPageClient client = new MyPageClient("hogehoge", "foofoo");
		client.doAuth();
		
	}

	/**
	 * 音楽詳細の取得に成功させるテスト
	 * @throws Exception
	 */
	@Category(MyPageSuccessTestCategory.class)
	@Test
	public void SuccessToGetMusicDetailTest() throws Exception {
		
		MyPageClient client = new MyPageClient("7020392000535457", "BLACKYv2");
		if(!client.doAuth()) {
			fail("認証に失敗");
		}
		
		String json = client.getMusicJson(250);
		
		JSONObject actualJson   = new JSONObject(json);
		JSONObject expectedJson = new JSONObject(succeedJson);
		
		String actual   = actualJson.getJSONObject("music_detail").getString("music_title");
		String expected = expectedJson.getJSONObject("music_detail").getString("music_title");
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 音楽詳細の取得に失敗させるテスト
	 * @throws Exception
	 */
	@Category(MyPageFailedTestCategory.class)
	@Test
	public void faildToGetMusicDetailTest() throws Exception {
		
		MyPageClient client = new MyPageClient("hogehoge", "foofoo");
		
		String actual = client.getMusicJson(99999);
		
		assertEquals(failedJson, actual);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Category(MyPageSuccessTestCategory.class)
	@Test
	public void getCookieTest() throws Exception {
		
		Method method = MyPageClient.class.getDeclaredMethod("hasSession", null);
		method.setAccessible(true);

		MyPageClient client = new MyPageClient("7020392000535457", "BLACKYv2");
		client.doAuth();
		
		boolean actual = (boolean) method.invoke(client, null);
		
		assertTrue(actual);
	}
	
	@Test
	public void getMusicIdListTest() throws Exception {
		
		MyPageClient client = new MyPageClient("","");
		
		client.getMusicIdList();
	}
	
}

