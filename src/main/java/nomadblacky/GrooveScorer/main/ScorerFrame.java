package nomadblacky.GrooveScorer.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import net.miginfocom.swing.MigLayout;
import nomadblacky.GrooveScorer.Exceptions.MyPageClientException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class ScorerFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JTextField nesicaIdField;
	JPasswordField passwordField;
	JComboBox<String> charsetComboBox;
	JButton button;
	
	JFrame dialog;
	ProgressMonitor pm;
	
	public ScorerFrame() {
		
		setSize(400, 160);
		setTitle("GrooveScorer Ver.2対応版");
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel center = new JPanel(new MigLayout());
		JLabel label = new JLabel("nesicaシリアルNo.");
		nesicaIdField = new JTextField();
		JLabel label2 = new JLabel("nesica.net パスワード");
		passwordField = new JPasswordField();
		JLabel label3 = new JLabel("CSVファイル文字コード");
		charsetComboBox = new JComboBox<>( new String[]{"Shift-JIS","UTF-8"} );
		charsetComboBox.setEditable(true);
		
		center.add(label, "w 150,h 20,");
		center.add(nesicaIdField, "wrap,w 250");
		center.add(label2, "h 30");
		center.add(passwordField, "wrap,w 250");
		center.add(label3, "h 20");
		center.add(charsetComboBox, "w 250");
		
		JPanel south = new JPanel();
		
		button = new JButton("取得開始");
		button.setName("startButton");
		button.addActionListener(this);
		
		south.add(button);
		
		getContentPane().add(center, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		Component source = (Component)e.getSource();
		
		if(source.getName().equals("startButton")) {

			if(checkValues() == false) {
				JOptionPane.showMessageDialog(
						this,
						"不正な文字コードが指定されています。",
						"エラー",
						JOptionPane.ERROR_MESSAGE
						);
				return;
			}
			
			SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					try {
						button.setEnabled(false);
						createCSV();
					} catch (MyPageClientException e) {
						showErrorMessage(e);
					} finally {
						button.setEnabled(true);
					}
					return null;
				}
				
				
			};

			worker.execute();
		}
	}
	
	private boolean checkValues() {
		
		try {
			Charset.forName((String)charsetComboBox.getSelectedItem());
		} catch(UnsupportedCharsetException | IllegalCharsetNameException e) {
			return false;
		}
		
		return true;
	}
	
	private void createCSV() throws Exception {
		
		MyPageClient client = new MyPageClient(nesicaIdField.getText(), new String(passwordField.getPassword()));
		
		String playerName = null;
		
		playerName = client.getPlayerName();
		
		String replacedPlayerName = StringUtils.replacePattern(playerName, "\\p{Punct}", "_");
		Path outJsonsDir = Paths.get("./result/" + replacedPlayerName, "json");
		
		client.doAuth();

		Files.createDirectories(outJsonsDir);
		
		List<IdAndDateTime> idDateList = client.getUpdatedMusicIdList();
		
		ArrayList<IdAndDateTime> updated = new ArrayList<>();
		for(IdAndDateTime idAndDateTime : idDateList) {
			try {
				Path path = Paths.get(outJsonsDir.toString(), idAndDateTime.id().toString() + ".json");
				StringBuilder builder = new StringBuilder();
				for(String line : Files.readAllLines(path, Charset.defaultCharset())) {
					builder.append(line);
				}
				JSONObject fileJson = new JSONObject(builder.toString());
				String str = fileJson.optString("last_play_time");
				if(StringUtils.isEmpty(str)) {
					updated.add(idAndDateTime);
					continue;
				}
				Date fileJsonDate = IdAndDateTime.parse(str);
				if(fileJsonDate.before(idAndDateTime.date())) {
					updated.add(idAndDateTime);
				}
			}
			catch(IOException e) {
				updated.add(idAndDateTime);
			}
		}
		
		pm = new ProgressMonitor(this, "実行中…", "", 0, updated.size());
		pm.setMillisToDecideToPopup(0);
		pm.setMillisToPopup(0);
		int count = 0;
		pm.setProgress(count);
		
		for (IdAndDateTime idDate : updated) {
			
			if(pm.isCanceled()) {
				pm.close();
				return;
			}
			
			String jsonText = client.getMusicJson(idDate.id());
			JSONObject jsonObject = new JSONObject(jsonText);
			jsonObject = jsonObject.put("last_play_time", idDate.formatDate());
			
			pm.setNote(String.format(
					"(%3d/%3d) : %s",
					count, updated.size(), jsonObject.getJSONObject("music_detail").getString("music_title")));
			pm.setProgress(count++);
			Files.write(
					Paths.get(outJsonsDir.toString(), idDate.id().toString() + ".json"),
					jsonObject.toString().getBytes(),
					StandardOpenOption.CREATE
					);
		}
		
		pm.setNote("CSVファイルを作成中…");
		
		Scorer.createCSV(
				Paths.get("./result", replacedPlayerName, "json"),
				Paths.get("./result", replacedPlayerName),
				replacedPlayerName + ".csv",
				Charset.forName((String)charsetComboBox.getSelectedItem()));
		
		pm.close();
		
		int option =JOptionPane.showConfirmDialog(
				this
				, "CSVファイルの作成が完了しました。ファイルを開きますか？"
				, "成功！"
				, JOptionPane.YES_NO_OPTION
				);
		
		if(option == JOptionPane.OK_OPTION) {
			Desktop.getDesktop().open(Paths.get("./result", replacedPlayerName, replacedPlayerName + ".csv").toFile());
		}
	}
	
	private void showErrorMessage(Throwable e) {
		JOptionPane.showMessageDialog(
				this,
				e.getMessage(),
				"エラー",
				JOptionPane.ERROR_MESSAGE);
	}
}
