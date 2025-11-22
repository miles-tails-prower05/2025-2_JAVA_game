package move;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class EnhancedBulletDodgePanel extends BulletDodgePanel {

	private Container frame;
	private CardLayout cards;
	private String[] panel;
	private CollidableObject character;
	private Image background;
    private ArrayList<CollidableObject> shields;
    protected String imageShield;
    private int shieldCount, imageSize;
	
	public EnhancedBulletDodgePanel(Container frame, CardLayout cards, String[] panel, CollidableObject character, String imagePath, int imageSize) {
		super(character, imagePath+"bullet.png", imageSize);
		this.character = character;
		this.characterHP = 2;
		
		this.imageSize = imageSize;
		
		this.background = new ImageIcon(imagePath+"background.png").getImage();
		
		this.imageShield = imagePath+"shield.png";
        this.shields = new ArrayList<CollidableObject>();
        this.shieldCount = 0;
        
        // 다른 패널로 이동할 수 있도록 준비
 		this.frame = frame;
 		this.cards = cards;
 		this.panel = panel;
 		
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
            	timer.start();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            	character.directionX = character.STOP;
            	character.directionY = character.STOP;
    			bombs.clear();
    			shields.clear();
    			shieldCount = 0;
    	        characterHP = 2; // 원래는 2
    	        time = 300; // 원래는 300
            	timer.stop();
            }
        });
	}
	
	@Override
	protected void update() {
		// 모든 총알은 오른쪽으로 이동하며, 주기적으로 새 총알을 발사
		for ( CollidableObject bomb : bombs )
			bomb.move();
		if ( ( ( time-- ) % 2 ) == 0 ) {  // 주기 변경 (원본: 8)
			bombs.add( new CollidableObject( CollidableObject.RIGHT, CollidableObject.STOP, imageBomb, imageSize, width, height ) );
		}
		
		// 모든 방어구는 오른쪽으로 이동하며, 주기적으로 새 방어구 생성
		for (CollidableObject shield : shields)
            shield.move();
		if ( ( time % 20 ) == 0 ) {
			shields.add( new CollidableObject( CollidableObject.RIGHT, CollidableObject.STOP, imageShield, imageSize, width, height ) );
		}
		
		// 캐릭터는 상하로 이동하고 HP 업데이트
		character.move( CollidableObject.STOP, character.directionY() );
		updateHP();
	}
	
	// 캐릭터와 폭탄이 충돌하면 캐릭터 HP 감소, 캐릭터와 보호막이 충돌하면 보호막 획득
	@Override
	protected void updateHP() {
		// 총알 충돌 체크
		for ( int i = 0; i < bombs.size(); i++ ) {
			CollidableObject bomb = bombs.get(i);
			if ( ( bomb.collide( character ) == true ) ) {
				if (shieldCount > 0)
                    shieldCount--; // 보호막 소모
                else
                    characterHP--;
				bombs.remove( bomb );
			}
			// 이동 허용 범위를 벗어난 폭탄은 제거
			else if ( ( bomb.collide() == true ) ) {
				bombs.remove( bomb );
			}
		}
		
		// 보호막 충돌 체크
        for (int i = 0; i < shields.size(); i++) {
            CollidableObject shield = shields.get(i);
            if (shield.collide(character)) {
                shieldCount++; // 보호막 획득
                shields.remove(shield);
            } else if (shield.collide()) {
                shields.remove(shield);
            }
        }
	}
	
	// 화면 출력
	@Override
	public void paint(Graphics g) {
	    // 배경 이미지 그리기
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

	    // === 클리어 또는 게임 오버 시 ===
	    if (this.time <= 0) {
	    	cards.show(frame, panel[2]);
	        return;
	    } 
	    else if (this.characterHP <= 0) {
	    	cards.show(frame, panel[3]);
	        return;
	    }

	    // === 캐릭터, HP, 폭탄 ===
	    g.setColor(Color.black);
	    g.setFont(new Font("Arial", Font.BOLD, 20));
	    g.drawString("HP : " + hp(), 10, 30);

	    character.paint(g);
	    for (CollidableObject bomb : bombs)
	        bomb.paint(g);
	    
	    // 남은 실드 표시
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Shield : " + shieldCount, 10, 60);

        // 실드 아이템 그리기
        for (CollidableObject shield : shields)
            shield.paint(g);

	    // === 시간 게이지 ===
	    int barHeight = 20;
	    int barY = height - barHeight - 45;

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
