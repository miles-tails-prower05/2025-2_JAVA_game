// JAVA 프로그래밍 - https://codereading101.github.io/JAVA/
// 소스파일 - https://github.com/CodeReading101/JAVA/blob/main/src/move/TopViewObject.java

package move;

import java.awt.*;
import javax.swing.*;

// 탑 뷰 캐릭터
public class TopViewObject extends ObjectByKey
{
	protected int[][] map;
	protected Image[] image;
	protected final int PATH = 0, WALL = 1, CHARACTER = 2;

	public TopViewObject( int[][] map, int x, int y, final String imagePath ) {
		super( imagePath+"character.png", x, y, 0, 0, map[0].length-1, map.length-1 );
		this.map = map;
		this.image = new Image[3];
		this.image[PATH     ] = new ImageIcon( imagePath + "path.png" ).getImage();
		this.image[WALL     ] = new ImageIcon( imagePath + "wall.png" ).getImage();
		this.image[CHARACTER] = new ImageIcon( imagePath + "character.png" ).getImage();
	}

	// 벽이 아니면 캐릭터 이동
	@Override
	public void move( int directionX, int directionY ) {
		if( map[y+directionY][x+directionX] != WALL )
			super.move( directionX, directionY );
	}

	// 캐릭터와 전체 맵을 출력
	@Override
	public void paint( Graphics g ){
		for( int y = 0; y <= map.length; y++ ){
			for( int x = 0; x <= map[0].length; x++ ){
				int index = WALL;
				if ( ( this.x == x ) && ( this.y == y ) )
					index = CHARACTER;
				else if ( ( minX <= x ) && ( x <= maxX ) && ( minY <= y ) && ( y <= maxY ) )
					index = map[y][x];
				g.drawImage( image[index], x*IMGSIZE, y*IMGSIZE, IMGSIZE, IMGSIZE, null );
			}
		}
	}

	@Override
	public int backgroundWidth(){
		return IMGSIZE * ( map[0].length );
	}
	@Override
	public int backgroundHeight(){
		return IMGSIZE * ( map.length );
	}
}

