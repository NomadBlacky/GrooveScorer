package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import nomadblacky.GrooveScorer.main.CSVWriter;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

public class CSVWriterTest {


	private static String sampleJson =
			"{\"status\":0,\"music_detail\":{\"music_title\":\"\u77b3\u306eLAZhWARD (\u30d5\u30ed\u30f3\u30c6\u30a3\u30a2S\u30fb\u65e7\u6e0b\u8c37\u8352\u5ec3\u5730\u533a\u30b9\u30c6\u30fc\u30b8BGM)\",\"artist\":\"\u30e1\u30a4\u30ea\u30a2\",\"skin_name\":\"none\",\"simple_result_data\":{\"rating\":\"S+\",\"no_miss\":1,\"full_chain\":0,\"music_level\":\"SIMPLE\",\"score\":953199,\"max_chain\":1562,\"adlib\":17},\"normal_result_data\":{\"rating\":\"S+\",\"no_miss\":1,\"full_chain\":0,\"music_level\":\"NORMAL\",\"score\":983471,\"max_chain\":1941,\"adlib\":22},\"hard_result_data\":{\"rating\":\"S+\",\"no_miss\":3,\"full_chain\":0,\"music_level\":\"HARD\",\"score\":967530,\"max_chain\":2236,\"adlib\":22},\"extra_result_data\":null}}";


	@Test
	public void writeTest() throws Exception {
		
		Charset charset = Charset.forName("UTF-8");
		CSVWriter writer = new CSVWriter(Paths.get("./result", "test", "json"), charset,true);
		writer.write(Paths.get("./result", "test"), "test.csv");
	}

	@Test
	public void DirectoryStreamTest() throws Exception {

		try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get("."))) {
			for(Path path : dirStream) {
				System.out.println(path.toAbsolutePath());
			}
		}
	}

	@Test
	public void nullJsonObjectTest() throws Exception {

		JSONObject object = new JSONObject("{\"hoge\":null}");
		System.out.println(object.optString("hoge"));
	}

	@Test
	public void optIntTest() throws Exception {

		JSONObject object = new JSONObject("{\"hoge\":null}");
		assertThat(object.optInt("hoge"), is(0));

	}
}
