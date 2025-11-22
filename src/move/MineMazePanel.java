// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/src/move/MineMazePanel.java

package move;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MineMazePanel extends ViewPanel {
	protected MineMazeCharacter character;
	protected JLabel stageInfoLabel, mineInfoLabel;

	public MineMazePanel(MineMazeCharacter character) {
		super(character);
		this.character = character;
		
		setLayout(new BorderLayout());
		stageInfoLabel = new JLabel();
		stageInfoLabel.setFont( new Font("맑은 고딕", Font.BOLD, 32) );
		stageInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		stageInfoLabel.setText("스테이지 2: 지뢰 피하기");
		add(stageInfoLabel, BorderLayout.NORTH);
		
		mineInfoLabel = new JLabel();
		mineInfoLabel.setFont( new Font("맑은 고딕", Font.BOLD, 17) );
		add(mineInfoLabel, BorderLayout.SOUTH);
		
		timer.stop();
		
		this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
            	timer = new Timer( 100, new ActionListener() {
                	@Override
                	public void actionPerformed( ActionEvent event ) {
                		update();
                		repaint();
                	}
                });
            	MineMazePanel.this.requestFocusInWindow();
            	timer.start();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            	character.x = character.initialX;
            	character.y = character.initialY;
            	character.directionX = character.STOP;
            	character.directionY = character.STOP;
            	timer.stop();
            }
        });
	}
	
	// 최신 정보 업데이트
	@Override
	protected void update() {
		super.update();
		mineInfoLabel.setText("주변에 " + character.searchMine() + "개의 지뢰가 있습니다.");
	}
}
