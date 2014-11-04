package nomadblacky.GrooveScorer.main;

import java.awt.BorderLayout;
import java.awt.Component;
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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import net.miginfocom.swing.MigLayout;
import nomadblacky.GrooveScorer.Exceptions.AuthenticationFailedException;
import nomadblacky.GrooveScorer.Exceptions.MyPageClientException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class ScorerFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JTextField nesicaIdField, playerNameField, charsetField;
	JFrame dialog;
	ProgressMonitor pm;
	
	public ScorerFrame() {
		
		setSize(400, 150);
		setTitle("GrooveScorer");
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel center = new JPanel(new MigLayout());
		JLabel label = new JLabel("nesicaシリアルNo.");
		nesicaIdField = new JTextField();
		JLabel label2 = new JLabel("プレイヤーネーム");
		playerNameField = new JTextField();
		JLabel label3 = new JLabel("CSVファイル文字コード");
		charsetField = new JTextField("Shift-JIS");
		
		center.add(label, "w 150,h 20,");
		center.add(nesicaIdField, "wrap,w 250");
		center.add(label2, "h 30");
		center.add(playerNameField, "wrap,w 250");
		center.add(label3, "h 20");
		center.add(charsetField, "w 250");
		
		JPanel south = new JPanel();
		JButton button = new JButton("取得開始");
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

			if(!checkValues()) {
				return;
			}
			
			SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					try {
						createCSV();
					} catch (MyPageClientException e) {
						;
					}
					return null;
				}
			};
			worker.execute();
		}
	}
	
	private boolean checkValues() {
		
		try {
			Charset.forName(charsetField.getText());
		} catch(UnsupportedCharsetException | IllegalCharsetNameException e) {
			JOptionPane.showMessageDialog(
					this,
					"不正な文字コードが指定されています。",
					"エラー",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private void createCSV() throws MyPageClientException {
		
		MyPageClient client = new MyPageClient(nesicaIdField.getText(), playerNameField.getText());
		
		String replacedPlayerName = StringUtils.replacePattern(client.getPlayerName(), "\\p{Punct}", "_");
		Path outJsonsDir = Paths.get("./result/" + replacedPlayerName, "json");
		
		try {
			client.doAuth();
		}
		catch(AuthenticationFailedException e) {
			JOptionPane.showMessageDialog(
					this,
					e.getMessage(),
					"エラー",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			Files.createDirectories(outJsonsDir);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		List<Integer> idlist = client.getMusicIdList();
		
		pm = new ProgressMonitor(this, "実行中…", "", 0, idlist.size());
		pm.setMillisToDecideToPopup(0);
		pm.setMillisToPopup(0);
		int count = 0;
		pm.setProgress(count);
		
		for (Integer id : idlist) {
			
			if(pm.isCanceled()) {
				pm.close();
				return;
			}
			
			String jsonText = client.getMusicJson(id);
			JSONObject jsonObject = new JSONObject(jsonText);
			pm.setNote(String.format(
					"(%3d/%3d) : %s",
					count, idlist.size(), jsonObject.getJSONObject("music_detail").getString("music_title")));
			pm.setProgress(count++);
			try {
				Files.write(
						Paths.get(outJsonsDir.toString(), id.toString() + ".json"),
						jsonText.getBytes(),
						StandardOpenOption.CREATE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						this,
						e.getMessage(),
						"エラー",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		pm.setNote("CSVファイルを作成中…");
		
		Scorer.createCSV(
				Paths.get("./result", replacedPlayerName, "json"),
				Paths.get("./result", replacedPlayerName),
				replacedPlayerName + ".csv",
				Charset.forName(charsetField.getText()));
		
		pm.close();
		
		JOptionPane.showMessageDialog(
				this,
				"CSVファイルの作成が完了しました。",
				"成功！",
				JOptionPane.INFORMATION_MESSAGE);

	}
}
