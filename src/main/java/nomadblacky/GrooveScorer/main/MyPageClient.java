package nomadblacky.GrooveScorer.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nomadblacky.GrooveScorer.Exceptions.AuthenticationFailedException;
import nomadblacky.GrooveScorer.Exceptions.MyPageAccessFailedException;
import nomadblacky.GrooveScorer.Exceptions.MyPageClientException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * TODO: javadoc書けや
 * @author blacky
 *
 */
public class MyPageClient {
	private final String nesicaId;
	private final String password;
	private final String playerName;
	
	private final CloseableHttpClient client;
	private final BasicCookieStore cookieStore;

	public MyPageClient(String nesicaId, String password) {
		
		this.nesicaId   = nesicaId;
		this.password = password;
		this.playerName = null;
		
    	// クライアントのリクエスト設定
    	RequestConfig requestConfig = RequestConfig.custom()
    			.setRedirectsEnabled(true)
    			.build();
    	
    	// クッキーストア生成
    	cookieStore = new BasicCookieStore();
    	
    	// クライアント生成
    	client = HttpClientBuilder.create()
    			.setDefaultRequestConfig(requestConfig)
    			.setDefaultCookieStore(cookieStore)
    			.build();
    	

	}
	
	public String getNesicaId() {
		return nesicaId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPlayerName() throws MyPageClientException, ClientProtocolException, IOException {
		if(playerName == null) {
			if(doAuth()) {
				HttpGet get = new HttpGet("https://mypage.groovecoaster.jp/sp/json/player_data.php");
				try(CloseableHttpResponse res = client.execute(get)) {
					HttpEntity body = res.getEntity();
					JSONObject jsonRoot = new JSONObject(EntityUtils.toString(body));

					return jsonRoot.getJSONObject("player_data").getString("player_name");
				}
			}
			else {
				throw new AuthenticationFailedException(nesicaId, password);
			}
		}
		else {
			return playerName;
		}
	}

	public boolean doAuth() throws MyPageClientException {
		
		try {
			// 認証ページへのリクエスト設定
			HttpPost post = new HttpPost("https://mypage.groovecoaster.jp/sp/login/auth_con.php");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nesicaCardId", nesicaId));
			params.add(new BasicNameValuePair("password", password));
			post.setEntity(new UrlEncodedFormEntity(params));
			
			try(CloseableHttpResponse response = client.execute(post)) {
				
				int statusCode = response.getStatusLine().getStatusCode();
				
				if( statusCode <= 199 || 500 <= statusCode ) {
					throw new MyPageAccessFailedException(statusCode);
				}
				
				if( !hasSession() ) {
					throw new AuthenticationFailedException(nesicaId, password);
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			throw new MyPageClientException("");
		}
		
		return true;
	}

	private boolean hasSession() {
		
		for(Cookie cookie : cookieStore.getCookies()) {
			
			String name   = cookie.getName();
			String domain = cookie.getDomain();
			if(name.equals("PHPSESSID") && domain.equals("mypage.groovecoaster.jp")) {
				return true;
			}
		}
		return false;
	}
	
	public List<Integer> getMusicIdList() throws MyPageClientException {
		
		List<Integer> idlist = new ArrayList<Integer>();
		
		try {
			HttpGet get = new HttpGet("https://mypage.groovecoaster.jp/sp/json/music_list.php");
			try(CloseableHttpResponse response = client.execute(get)) {
				
				int statusCode = response.getStatusLine().getStatusCode();
				
				if(!(statusCode == HttpStatus.SC_OK)) {
					System.err.println("楽曲ページへのアクセスに失敗");
					System.err.println("HTTPステータースコード: " + statusCode);
					return null;
				}
				
				HttpEntity body = response.getEntity();
				JSONObject jsonRoot = new JSONObject(EntityUtils.toString(body));
				
				if(jsonRoot.getInt("status") == 1) {
					System.err.println("JSONステータスが不正");
					return null;
				}
				
				JSONArray musics = jsonRoot.getJSONArray("music_list");
				
				for(int i = 0; i < musics.length(); i++) {
					idlist.add(musics.getJSONObject(i).getInt("music_id"));
				}
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return idlist;
	}
	
	public List<IdAndDateTime> getUpdatedMusicIdList() throws MyPageAccessFailedException {
		
		List<IdAndDateTime> idDateList = new ArrayList<>();
		
		try {
			HttpGet get = new HttpGet("https://mypage.groovecoaster.jp/sp/json/music_list.php");
			try(CloseableHttpResponse response = client.execute(get)) {
				
				int statusCode = response.getStatusLine().getStatusCode();
				
				if(!(statusCode == HttpStatus.SC_OK)) {
					System.err.println("楽曲ページへのアクセスに失敗");
					System.err.println("HTTPステータースコード: " + statusCode);
					return null;
				}
				
				HttpEntity body = response.getEntity();
				JSONObject jsonRoot = new JSONObject(EntityUtils.toString(body));
				
				if(jsonRoot.getInt("status") == 1) {
					System.err.println("JSONステータスが不正");
					return null;
				}
				
				JSONArray musics = jsonRoot.getJSONArray("music_list");
				
				for(int i = 0; i < musics.length(); i++) {
					JSONObject object = musics.getJSONObject(i);
					idDateList.add(new IdAndDateTime(object.getInt("music_id"), object.getString("last_play_time")));
				}
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return idDateList;
		
	}

	public String getMusicJson(int i) {
		
		String jsonString = null;
		
		try {
			// 楽曲jsonヘアクセス
			HttpGet get = new HttpGet("https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=" + i);
			try(CloseableHttpResponse response = client.execute(get)) {
				
				int statusCode = response.getStatusLine().getStatusCode();
				
				if(statusCode == HttpStatus.SC_OK) {
					HttpEntity body = response.getEntity();
					jsonString = EntityUtils.toString(body);
				}
				else {
					return "{\"status\":-1}";
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return "{\"status\":-1}";
		}
		
		return jsonString;
	}
	
}
