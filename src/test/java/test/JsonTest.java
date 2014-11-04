package test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.text.StrBuilder;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.json.JSONObject;
import org.junit.Test;

public class JsonTest {
	
	@Test
	public void readJsonTest() throws IOException {
		
		File jsonFile = new File("./result/night.json");
		StrBuilder builder = new StrBuilder();
		try(Scanner scanner = new Scanner(jsonFile)) {
			while (scanner.hasNext()) {
				builder.appendln(scanner.nextLine());
			}
		}
		
		JSONObject root = new JSONObject(builder.toString());
		JSONObject musicDetail = root.getJSONObject("music_detail");
		System.out.println(musicDetail.getString("music_title"));
	}
	
	@Test
	public void readJsonTest2() throws FileNotFoundException {
		
		File jsonFile = new File("./result/night.json");
		StrBuilder builder = new StrBuilder();
		try(Scanner scanner = new Scanner(jsonFile)) {
			while (scanner.hasNext()) {
				builder.appendln(scanner.nextLine());
			}
		}
		
		JSONObject root = new JSONObject(builder.toString());
		System.out.println(root.toString());
	}
	
	@Test
	public void extraNullTest() throws Exception {
		
		List<String> lines = Files.readAllLines(
				Paths.get("./result/test/json/1.json"), Charset.forName("UTF-8"));
		StrBuilder builder = new StrBuilder();
		for (String line : lines) {
			builder.append(line);
		}
		String jsonString = builder.toString();
		
		JSONObject root = new JSONObject(jsonString);
		JSONObject extra = root.getJSONObject("music_detail").getJSONObject("extra_result_data");
		
		assertThat(extra, nullValue(JSONObject.class));
	}
	
	
}
