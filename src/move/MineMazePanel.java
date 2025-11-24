// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/src/move/MineMazePanel.java

package move;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// 지뢰 피하기 패널
public class MineMazePanel extends ViewPanel {
	protected MineMazeCharacter character;
	protected JLabel stageInfoLabel, mineInfoLabel;

	// 패널 초기화
	public MineMazePanel(MineMazeCharacter character) {
		super(character);
		timer.stop();
		this.character = character;
		
		// 배경 색상 채우기, 제목과 지뢰 개수를 표시할 라벨 준비
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout());
		
		stageInfoLabel = new JLabel();
		stageInfoLabel.setFont( new Font("맑은 고딕", Font.BOLD, 32) );
		stageInfoLabel.setForeground(Color.WHITE);
		stageInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		stageInfoLabel.setText("스테이지 2: 지뢰 피하기");
		add(stageInfoLabel, BorderLayout.NORTH);
		
		mineInfoLabel = new JLabel();
		mineInfoLabel.setFont( new Font("맑은 고딕", Font.BOLD, 30) );
		mineInfoLabel.setForeground(Color.PINK);
		add(mineInfoLabel, BorderLayout.SOUTH);
		
		// 화면 전환 시 반응하는 리스너 등록
		this.addComponentListener(new ComponentAdapter() {
			// 패널이 보여지면 주기적으로 활성화되는 타이머 시작
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
            
            // 패널이 숨겨지면 패널 초기화
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
