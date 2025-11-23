// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/src/move/BulletDodgePanel.java
// 원본 소스파일 - https://github.com/CodeReading101/JAVA/blob/main/src/move/BulletDodgePanel.java

package move;
import java.awt.*;

// 총알 피하기 패널
public class BulletDodgePanel extends BombDodgePanel
{
	public BulletDodgePanel( CollidableObject character, final String imageBomb, final int imageSize ) {
		super( character, imageBomb, imageSize );
		setPreferredSize( new Dimension( width, height + imageSize ) );
	}

	// 최신 정보 업데이트
	@Override
	protected void update() {
		// 모든 총알은 오른쪽으로 이동하며, 주기적으로 새 총알을 발사
		for ( CollidableObject bomb : bombs )
			bomb.move();
		if ( ( ( time-- ) % 8 ) == 0 ) {
			bombs.add( new CollidableObject( CollidableObject.RIGHT, CollidableObject.STOP, imageBomb, imageSize, width, height ) );
		}

		// 캐릭터는 상하로 이동하고 HP 업데이트
		character.move( CollidableObject.STOP, character.directionY() );
		updateHP();
	}
}

