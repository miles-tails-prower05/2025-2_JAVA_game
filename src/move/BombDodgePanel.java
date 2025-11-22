// JAVA 프로그래밍 - https://codereading101.github.io/JAVA/
// 소스파일 - https://github.com/CodeReading101/JAVA/blob/main/src/move/BombDodgePanel.java

package move;

import java.awt.*;
import java.util.ArrayList;

// 폭탄 피하기 패널
public class BombDodgePanel extends ViewPanel
{
	protected ArrayList<CollidableObject> bombs;
	protected int characterHP, time, width, height, imageSize;
	protected String imageBomb;

	public BombDodgePanel( CollidableObject character, final String imageBomb, final int imageSize ) {
		super( character );
		this.characterHP = 5;
		this.bombs = new ArrayList<CollidableObject>();
		this.imageBomb = imageBomb;
		this.width = character.backgroundWidth();
		this.height= character.backgroundHeight();
		this.imageSize = imageSize;
		this.time = 300;
		setBackground( Color.white );
		setPreferredSize( new Dimension( width + this.imageSize, height ) );
	}

	// 최신 정보 업데이트
	@Override
	protected void update() {
		// 모든 폭탄은 아래로 떨어지며, 주기적으로 새 폭탄을 투하
		for ( CollidableObject bomb : bombs )
			bomb.move();
		if ( ( ( time-- ) % 8 ) == 0 ) {
			bombs.add( new CollidableObject( CollidableObject.STOP, CollidableObject.DOWN, imageBomb, imageSize, width, height ) );
		}

		// 캐릭터는 좌우로 이동하고 HP 업데이트
		character.move( character.directionX(), CollidableObject.STOP );
		updateHP();
	}

	// 캐릭터와 폭탄이 충돌하면 캐릭터 HP 감소
	protected void updateHP() {
		for ( int i = 0; i < bombs.size(); i++ ) {
			CollidableObject bomb = bombs.get(i);
			if ( ( bomb.collide( character ) == true ) ) {
				characterHP--;
				bombs.remove( bomb );
			}
			// 이동 허용 범위를 벗어난 폭탄은 제거
			else if ( ( bomb.collide() == true ) ) {
				bombs.remove( bomb );
			}
		}
	}

	// 화면 출력
	@Override
	public void paint( Graphics g ) {
		super.paint( g );
		// 먼저, 제한시간동안 폭탄을 피해 살아남으면 성공 출력
		if ( this.time <= 0 ) {
			g.setColor( Color.black );
			g.setFont( new Font( "Arial", Font.BOLD, 40 ) );
			g.drawString( "Success!", width/2-80, height/2 );
			timer.stop();
		}
		// 또는, HP가 더 이상 없으면 실패 출력
		else if ( this.characterHP <= 0 ) {
			g.setColor( Color.black );
			g.setFont( new Font( "Arial", Font.BOLD, 40 ) );
			g.drawString( "Game Over!", width/2-100, height/2 );
			timer.stop();
		}
		// 아니면, 캐릭터 및 폭탄의 현재 게임 상황 출력
		else {
			g.setColor( Color.black );
			g.setFont( new Font( "Arial", Font.BOLD, 20 ) );
			g.drawString( "HP : " + hp(), 10, 30 );
			character.paint( g );
			for ( CollidableObject bomb : bombs )
				bomb.paint( g );
		}
	}

	public String hp() {
		switch( this.characterHP ) {
		case 5:
			return "● ● ● ● ●";
		case 4:
			return "● ● ● ● ○";
		case 3:
			return "● ● ● ○ ○";
		case 2:
			return "● ● ○ ○ ○";
		case 1:
			return "● ○ ○ ○ ○";
		default:
			return "○ ○ ○ ○ ○";
		}
	}
}

