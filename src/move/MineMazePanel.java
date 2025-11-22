package move;

import java.awt.*;
import javax.swing.*;

public class MineMazePanel extends ViewPanel {
	protected MineMazeCharacter character;
	protected JLabel stageInfoLabel, mineInfoLabel;

	public MineMazePanel(MineMazeCharacter character) {
		super(character);
		this.character = character;
		
		setLayout(new BorderLayout());
		stageInfoLabel = new JLabel();
		stageInfoLabel.setFont( new Font( "굴림", Font.BOLD, 30 ) );
		stageInfoLabel.setText("스테이지 2: 지뢰 피하기");
		add(stageInfoLabel, BorderLayout.NORTH);
		
		mineInfoLabel = new JLabel();
		mineInfoLabel.setFont( new Font( "굴림", Font.BOLD, 17 ) );
		add(mineInfoLabel, BorderLayout.SOUTH);
	}
	
	// 최신 정보 업데이트
	@Override
	protected void update() {
		super.update();
		mineInfoLabel.setText("주변에 " + character.searchMine() + "개의 지뢰가 있습니다.");
	}
}
