// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/src/move/EnhancedBulletDodgePanel.java

package move;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

// 개선된 총알 피하기 패널
public class EnhancedBulletDodgePanel extends BombDodgePanel
{

	private Container frame;
	private CardLayout cards;
	private String[] panel;
	protected JLabel stageInfoLabel;
	private CollidableObject character;
	private Image background;
    private ArrayList<CollidableObject> shields;
    protected String imageShield;
    private int shieldCount, imageSize;
	
    // 패널 초기화
	public EnhancedBulletDodgePanel(Container frame, CardLayout cards, String[] panel, CollidableObject character, String imagePath, int imageSize) {
		super(character, imagePath+"bullet.png", imageSize);
		timer.stop();
		this.character = character;
		this.characterHP = 2;
		this.imageSize = imageSize;
		
		this.background = new ImageIcon(imagePath+"bg_stage1.png").getImage();
		setLayout(new BorderLayout());
		stageInfoLabel = new JLabel();
		stageInfoLabel.setFont( new Font("맑은 고딕", Font.BOLD, 32) );
		stageInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		stageInfoLabel.setText("스테이지 1: 총알 피하기");
		add(stageInfoLabel, BorderLayout.NORTH);
		
		this.imageShield = imagePath+"shield.png";
        this.shields = new ArrayList<CollidableObject>();
        this.shieldCount = 0;
        
        // 다른 패널로 이동할 수 있도록 준비
 		this.frame = frame;
 		this.cards = cards;
 		this.panel = panel;
 		
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
            	timer.start();
            }
            
            // 패널이 숨겨지면 패널 초기화
            @Override
            public void componentHidden(ComponentEvent e) {
            	character.directionX = character.STOP;
            	character.directionY = character.STOP;
    			bombs.clear();
    			shields.clear();
    			shieldCount = 0;
    	        characterHP = 2;
    	        time = 300;
            	timer.stop();
            }
        });
	}
	
	// 최신 정보 업데이트
	@Override
	protected void update() {
		// 모든 총알은 오른쪽으로 이동하며, 더 짧은 주기로 새 총알을 발사
		for ( CollidableObject bomb : bombs )
			bomb.move();
		if ( ( ( time-- ) % 2 ) == 0 ) {  // 주기 변경 (원본: 8)
			bombs.add( new CollidableObject( CollidableObject.RIGHT, CollidableObject.STOP, imageBomb, 55, width, height ) );
		}
		
		// 모든 방어구는 오른쪽으로 이동하며, 주기적으로 새 방어구 생성
		for (CollidableObject shield : shields)
            shield.move();
		if ( ( time % 20 ) == 0 ) {
			shields.add( new CollidableObject( CollidableObject.RIGHT, CollidableObject.STOP, imageShield, imageSize, width, height ) );
		}
		
		// 캐릭터는 상하로 이동하고 HP, 방어구 개수 업데이트
		character.move( CollidableObject.STOP, character.directionY() );
		updateHP();
	}
	
	// 캐릭터와 폭탄이 충돌하면 보호막 소모 후 캐릭터 HP 감소, 캐릭터와 방어구가 충돌하면 방어구 획득
	@Override
	protected void updateHP() {
		// 총알 충돌 체크
		for ( int i = 0; i < bombs.size(); i++ ) {
			CollidableObject bomb = bombs.get(i);
			if ( ( bomb.collide( character ) == true ) ) {
				if (shieldCount > 0)
                    shieldCount--; // 방어구 소모
                else
                    characterHP--;
				bombs.remove( bomb );
			} else if ( ( bomb.collide() == true ) ) {
				bombs.remove( bomb );
			}
		}
		
		// 방어구 충돌 체크
        for (int i = 0; i < shields.size(); i++) {
            CollidableObject shield = shields.get(i);
            if (shield.collide(character)) {
                shieldCount++; // 방어구 획득
                shields.remove(shield);
            } else if (shield.collide()) {
                shields.remove(shield);
            }
        }
	}
	
	// 화면 출력
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

	    // 클리어 또는 게임 오버 시 패널 전환
	    if (this.time <= 0) {
	    	cards.show(frame, panel[2]);
	        return;
	    } 
	    else if (this.characterHP <= 0) {
	    	cards.show(frame, panel[3]);
	        return;
	    }

	    // 아니면, 체력과 방어구 개수 출력
	    g.setColor(Color.black);
	    g.setFont(new Font("Arial", Font.BOLD, 20));
	    g.drawString("HP : " + hp(), 10, 30);
	    
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Shield : " + shieldCount, 10, 60);

        // 캐릭터, 총알, 방어구의 현재 게임 상황 출력
	    character.paint(g);
	    for (CollidableObject bomb : bombs)
	        bomb.paint(g);
        for (CollidableObject shield : shields)
            shield.paint(g);

	    // 남은 시간 게이지 출력
	    int barHeight = 20;
	    int barY = 720 - barHeight - 39;
	    int maxTime = 300;
	    int barWidth = (int)(((double) time / maxTime) * width);

	    g.setColor(Color.LIGHT_GRAY);
	    g.fillRect(0, barY, width, barHeight);
	    g.setColor(Color.GREEN);
	    g.fillRect(0, barY, barWidth, barHeight);

	}
	
	@Override
	public String hp() {
		switch( this.characterHP ) {
			case 2:
				return "● ●";
			case 1:
				return "● ○";
			default:
				return "○ ○";
		}
	}
}
