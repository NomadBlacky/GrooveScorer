package nomadblacky.GrooveScorer.main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import nomadblacky.GrooveScorer.Exceptions.MyPageClientException;

import org.json.JSONObject;

public class Scorer {

	private static String nesicaId, playerName;

	public static void main(String[] args) throws MyPageClientException {
		
		Charset charset = Charset.forName("utf-8");
		try {
			if(args.length > 0) {
				charset = Charset.forName(args[0]);
			}
		}
		catch(UnsupportedCharsetException e) {
			
			System.err.println("指定された文字コードが不正です。");
			return;
		}
		
		
		Scanner scanner = new Scanner(System.in);

		while(true) {
			System.out.print("nesicaシリアルNo. : ");
			nesicaId = scanner.nextLine();
			if(!nesicaId.isEmpty()) {
				break;
			}
		}

		while(true) {
			System.out.print("プレイヤーネーム : ");
			playerName = scanner.nextLine();
			if(!playerName.isEmpty()) {
				break;
			}
		}
		
		scanner.close();
		

		try {
			MyPageClient client = new MyPageClient(nesicaId, playerName);
	
			System.out.println("取得開始…");
			getJsons(client , Paths.get("./result/" + playerName, "json"));
			
			System.out.println("CSVファイル作成中…");
			createCSV(
					Paths.get("./result", playerName, "json"),
					Paths.get("./result", playerName),
					playerName + ".csv",
					charset);

			System.out.println("OK!");
		}
		catch(MyPageClientException e) {
			System.err.print("ERROR: ");
			System.err.println(e.getMessage());
		}
		catch (IOException e) {
			System.err.print("ERROR: ");
			System.err.println(e.getMessage());
		}
		

	}

	public static void getJsons(MyPageClient client, Path outJsonsDir) throws IOException, MyPageClientException {

		client.doAuth();

		Files.createDirectories(outJsonsDir);

		List<IdAndDateTime> idDateList = client.getUpdatedMusicIdList();

		for (IdAndDateTime idDate : idDateList) {
			Path outJsonPath = Paths.get(outJsonsDir.toString(), idDate.id().toString() + ".json");
			JSONObject fileJson = new JSONObject(Files.readAllLines(outJsonPath, Charset.defaultCharset()));
			String str = fileJson.optString("last_play_time");
			Date fileJsonDate = IdAndDateTime.parse(str);
			
			if(idDate.before(fileJsonDate) || str == null) {
				String jsonText = client.getMusicJson(idDate.id());
				JSONObject jsonObject = new JSONObject(jsonText);
				jsonObject = jsonObject.put("last_play_time", idDate.formatDate());
				
				System.out.println(idDate.id() + " : " + jsonObject.getJSONObject("music_detail").getString("music_title"));
				try {
					Files.write(
							outJsonPath,
							jsonObject.toString().getBytes(),
							StandardOpenOption.CREATE);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	
	public static void createCSV(Path jsonsDir, Path outCsvDir, String fileName, Charset charset) throws IOException {

		CSVWriter writer = new CSVWriter(jsonsDir, charset);
		
		writer.write(outCsvDir, fileName);
	}

}
