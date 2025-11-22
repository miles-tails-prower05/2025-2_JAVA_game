// 소스파일 - 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimplePanel extends JPanel {
	protected Image[] image;
	protected final int TITLE = 0, FAIL = 1, SUCCESS = 2;
	private String panelName;
	private String nextPanel;
	private String buttonLabel;
	private Container frame;
	private CardLayout cards;
	private JButton button;
	
	// 패널 초기화
	public SimplePanel( Container frame, CardLayout cards, String[] panel, final String imagePath ) {
		this.image = new Image[3];
		this.image[TITLE  ] = new ImageIcon( imagePath + "title.png" ).getImage();
		this.image[FAIL   ] = new ImageIcon( imagePath + "fail.png" ).getImage();
		this.image[SUCCESS] = new ImageIcon( imagePath + "success.png" ).getImage();
		panelName = panel[1];
		nextPanel = panel[2];
		buttonLabel = panel[5];
		
		setLayout(null);
		// 클릭하면 반응하는 버튼을 추가
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

        int index = TITLE;
		if (panelName == "게임 오버 화면")
			index = FAIL;
		else if (panelName == "게임 클리어 화면")
			index = SUCCESS;
        g.drawImage(image[index], 0, 0, null);
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
