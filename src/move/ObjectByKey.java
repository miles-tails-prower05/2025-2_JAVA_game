// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/src/move/ObjectByKey.java
// 원본 소스파일 - https://github.com/CodeReading101/JAVA/blob/main/src/move/ObjectByKey.java

package move;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// 방향키로 이동하는 객체
public class ObjectByKey extends KeyAdapter
{
	private Image image;
	protected int imageSize;
	protected int x, y, directionX, directionY;
	protected int minX, minY, maxX, maxY;
	public static final int LEFT = -1, RIGHT = 1, UP = -1, DOWN = 1, STOP = 0;

	// 초기화: 이미지, 현재 위치, 이동 허용 범위, 이동 방향을 설정
	public ObjectByKey( String image, int size, int x, int y, int minX, int minY, int maxX, int maxY ) {
		this.image = new ImageIcon( image ).getImage();
		this.imageSize = size;
		this.x = x;
		this.y = y;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.directionX = STOP;
		this.directionY = STOP;
	}

	// 키를 누르면 상하좌우 이동방향 설정
	@Override
	public void keyPressed( KeyEvent event ) {
		switch( event.getKeyCode() ) {
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		case KeyEvent.VK_LEFT: case 'A': case 'a':
			directionX = LEFT;
			break;
		case KeyEvent.VK_RIGHT: case 'D': case 'd':
			directionX = RIGHT;
			break;
		case KeyEvent.VK_UP: case 'W': case 'w':
			directionY = UP;
			break;
		case KeyEvent.VK_DOWN: case 'S': case 's':
			directionY = DOWN;
			break;
		}
	}

	// 키를 해제하면 이동방향 해제
	@Override
	public void keyReleased( KeyEvent event ) {
		switch( event.getKeyCode() ) {
		case KeyEvent.VK_LEFT: case 'A': case 'a':
		case KeyEvent.VK_RIGHT: case 'D': case 'd':
			directionX = STOP;
			break;
		case KeyEvent.VK_UP: case 'W': case 'w':
		case KeyEvent.VK_DOWN: case 'S': case 's':
			directionY = STOP;
			break;
		}
	}

	// 이동 허용 범위내에서만 객체 이동
	public void move() {
		move( directionX, directionY );
	}
	public void move( int directionX, int directionY ) {
		this.x += directionX;
		this.y += directionY;
		this.x = ( this.x <= minX ) ? minX : this.x;
		this.y = ( this.y <= minY ) ? minY : this.y;
		this.x = ( this.x >= maxX ) ? maxX : this.x;
		this.y = ( this.y >= maxY ) ? maxY : this.y;
	}

	// 현재 위치에 객체를 출력
	public void paint( Graphics g ) {
		g.drawImage( image, x, y, imageSize, imageSize, null );
	}

	public int directionX() {
		return directionX;
	}

	public int directionY() {
		return directionY;
	}

	public int backgroundWidth(){
		return maxX;
	}

	public int backgroundHeight(){
		return maxY;
	}
}

