package nomadblacky.GrooveScorer.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class CSVWriter {

	private Path jsonDirectory;
	private Charset charset;

	private boolean debug = false;

	public CSVWriter(Path jsonDirectory, Charset charset) {
		this.jsonDirectory = jsonDirectory;
		this.charset = charset;
	}

	public CSVWriter(Path jsonDirectory, Charset charset, boolean debug) {
		this.jsonDirectory = jsonDirectory;
		this.charset = charset;
		this.debug = debug;
	}

	public void write(Path outDirectory, String fileName) throws IOException {

		Path filePath = null;
		Files.createDirectories(outDirectory);
		try {
			filePath = Files.createFile(Paths.get(outDirectory.toString(), fileName ));
		} catch(FileAlreadyExistsException e) {
			filePath = Paths.get(outDirectory.toString(), fileName);
		}

		List<MusicDetailBean> detailBeans = new ArrayList<MusicDetailBean>();

		try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(jsonDirectory)) {
			for(Path path : dirStream) {
				List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
				StrBuilder stringBuilder = new StrBuilder();
				for(String line : lines) {
					stringBuilder.append(line);
				}

				JSONObject detailJson =
						JsonUtils.flatten(
								new JSONObject(stringBuilder.toString())
								.getJSONObject("music_detail")
								);

				MusicDetailBean detailBean = new MusicDetailBean();

				int simplePlay = detailJson.optInt("simple_result_data.play_count");
				int normalPlay = detailJson.optInt("normal_result_data.play_count");
				int hardPlay   = detailJson.optInt("hard_result_data.play_count");
				int extraPlay  = detailJson.optInt("extra_result_data.play_count");
				int totalPlay  = simplePlay + normalPlay + hardPlay + extraPlay;

				detailBean.setId(detailJson.optInt("music_id"));
				detailBean.setMusicTitle(detailJson.getString("music_title"));
				detailBean.setArtist(detailJson.getString("artist"));
				detailBean.setSkin(detailJson.getString("skin_name"));
				detailBean.setTotalPlayCount(totalPlay);
				
				DIFFICLITY[] difficlities = {
						DIFFICLITY.SIMPLE,
						DIFFICLITY.NORMAL,
						DIFFICLITY.HARD,
						DIFFICLITY.EXTRA
				};
				
				String s = "_result_data.";
				
				for(DIFFICLITY d : difficlities) {
					
					detailBean.setScore(d, detailJson.optInt(d.toString() + s + "score"));
					detailBean.setRate(d, detailJson.optString(d.toString() + s + "rating"));
					detailBean.setChain(d, detailJson.optInt(d.toString() + s + "max_chain"));
					detailBean.setPlayCount(d, detailJson.optInt(d.toString() + s + "play_count"));
					detailBean.setNomissCount(d, detailJson.optInt(d.toString() + s + "no_miss"));
					detailBean.setFCCount(d, detailJson.optInt(d.toString() + s + "full_chain"));
					
					try {
						if(detailJson.getBoolean(d.toString() + s + "is_failed_mark")) {
							detailBean.setStatus(d, "Failed");
						}
						else if (detailJson.getBoolean(d.toString() + s + "is_clear_mark")) {
							detailBean.setStatus(d, "Clear");
						}
						else if (detailBean.isFullChain(d)) {
							detailBean.setStatus(d, "FullChain");
						}
						else if (detailBean.isNomiss(d)) {
							detailBean.setStatus(d, "NoMiss");
						}
						else {
							detailBean.setStatus(d, "-");
						}
					}
					catch(JSONException e) {
						detailBean.setStatus(d, "-");
					}
				}
				
				detailBean.setTotalPlayCount(
						detailBean.getSimplePlayCount() +
						detailBean.getNormalPlayCount() +
						detailBean.getHardPlayCount() +
						detailBean.getExtraPlayCount()
				);
				
				detailBeans.add(detailBean);
			}

		}

		FileOutputStream fos = new FileOutputStream(filePath.toFile());
		OutputStreamWriter writer = new OutputStreamWriter(fos, charset.toString());
		
		ICsvBeanWriter beanWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

		beanWriter.writeHeader(MusicDetailBean.CSV_HEADER);

		for(MusicDetailBean bean : detailBeans) {
			beanWriter.write(bean, MusicDetailBean.CSV_HEADER);
		}

		beanWriter.close();
	}

}
