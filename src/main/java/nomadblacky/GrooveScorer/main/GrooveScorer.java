package nomadblacky.GrooveScorer.main;

import javax.swing.JFrame;

public class GrooveScorer {

	public static void main(String[] args) {
		
		ScorerFrame frame = new ScorerFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
