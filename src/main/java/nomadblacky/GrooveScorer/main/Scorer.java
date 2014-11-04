package nomadblacky.GrooveScorer.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

import nomadblacky.GrooveScorer.Exceptions.AuthenticationFailedException;
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
		

	}

	public static void getJsons(MyPageClient client, Path outJsonsDir) throws MyPageClientException {

		client.doAuth();

		try {
			Files.createDirectories(outJsonsDir);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		List<Integer> idlist = client.getMusicIdList();

		for (Integer id : idlist) {
			String jsonText = client.getMusicJson(id);
			JSONObject jsonObject = new JSONObject(jsonText);
			System.out.println(id + " : " + jsonObject.getJSONObject("music_detail").getString("music_title"));
			try {
				Files.write(
						Paths.get(outJsonsDir.toString(), id.toString() + ".json"),
						jsonText.getBytes(),
						StandardOpenOption.CREATE);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static void createCSV(Path jsonsDir, Path outCsvDir, String fileName, Charset charset) {

		CSVWriter writer = new CSVWriter(jsonsDir, charset);
		try {
			writer.write(outCsvDir, fileName);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
