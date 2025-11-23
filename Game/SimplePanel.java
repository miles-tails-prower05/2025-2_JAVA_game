// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/Game/SimplePanel.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimplePanel extends JPanel {
	protected Image panelImage;
	protected final int TITLE = 0, FAIL = 1, SUCCESS = 2;
	private String panelName, panelImagePath;
	private String nextPanel;
	private String buttonLabel, buttonImage;
	private Container frame;
	private CardLayout cards;
	private JButton button;
	
	// 패널 초기화
	public SimplePanel( Container frame, CardLayout cards, String[] panel, final String imagePath ) {
		panelName = panel[1];
		nextPanel = panel[2];
		panelImagePath = panel[4];
		buttonLabel = panel[5];
		if (panel.length > 6)
			buttonImage = panel[6];
		this.panelImage = new ImageIcon( imagePath + panelImagePath ).getImage();
		
		setLayout(null);
		// 클릭하면 반응하는 버튼을 추가
		if (panel.length > 6)
			button = new JButton(new ImageIcon(new ImageIcon(imagePath + buttonImage).getImage().getScaledInstance( 200, 50, Image.SCALE_SMOOTH )));
		else
			button = new JButton(buttonLabel);
		button.addActionListener(new ClickListener());
		button.setBounds(900, 500, 200, 50);
		add(button);
		
		// 다음 패널로 이동할 수 있도록 준비
		this.frame = frame;
		this.cards = cards;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(panelImage, 0, 0, null);
    }
	
	// 버튼 클릭시 반응
	private class ClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			requestFocus();
			cards.show( frame, nextPanel );
		}
	}
}
