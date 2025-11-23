// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/src/move/CollidableObject.java
// 원본 소스파일 - https://github.com/CodeReading101/JAVA/blob/main/src/move/CollidableObject.java

package move;
// 충돌가능한 객체
public class CollidableObject extends ObjectByKey
{
	// 초기화: 이미지, 현재 위치, 이동 허용 범위를 설정
	public CollidableObject( final String image, int size, int x, int y, int width, int height ) {
		super( image, size, x, y, 0, 40, width, height );
	}
	public CollidableObject( int directionX, int directionY, final String image, int size, int width, int height ) {
		this( image, size, 0, 40, width, height );
		this.directionX = directionX;
		this.directionY = directionY;
		// 폭탄 초기화 : 위쪽(임의위치)에서 아래쪽으로 폭탄을 투하
		if ( ( directionX == STOP ) && ( directionY == DOWN ) ) {
			this.x = (int)( Math.random() * this.maxX );
		}
		// 총알, 방어구 초기화 : 왼쪽(임의위치)에서 오른쪽으로 총알 또는 방어구를 발사
		else if ( ( directionX == RIGHT ) && ( directionY == STOP ) ) {
			this.y = imageSize + (int)( Math.random() * ( this.maxY - imageSize ) );
		}
	}
	// 일정 속도로 위치 이동
	@Override
	public void move( int directionX, int directionY ) {
		final int SPEED = 100; // 속도 수정(원본: 20)
		super.move( directionX * SPEED, directionY * SPEED );
	}
	// 다른 객체와 충돌하거나 이동 허용 범위를 벗어나는지 여부 확인
	public boolean collide( ObjectByKey that ) {
		return ( Math.abs( this.x - that.x ) < imageSize ) && ( Math.abs( this.y - that.y ) < imageSize );
	}
	public boolean collide() {
		return ( this.x < minX ) || ( maxX < this.x ) || ( this.y < minY ) || ( maxY < this.y );
	}
}

