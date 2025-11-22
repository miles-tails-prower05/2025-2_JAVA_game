// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_Assignment/blob/main/src/move/MazeCharacter.java

package move;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class MineMazeCharacter extends TopViewObject {
	
	private Container frame;
	private CardLayout cards;
	protected int[][] map;
	protected Image[] image;
	protected final int PATH = 0, MINE = 1, CHARACTER = 2, GOAL = 3, LEFT = -1, RIGHT = 1, UP = -1, DOWN = 1, TOPMARGIN = 30;
	
	public MineMazeCharacter(Container frame, CardLayout cards, int[][] map, int x, int y, final String imagePath) {
		super(map, x, y, imagePath);
		this.map = map;
		this.image = new Image[4];
		this.image[PATH     ] = new ImageIcon( imagePath + "path.png" ).getImage();
		this.image[MINE     ] = new ImageIcon( imagePath + "mine.png" ).getImage();
		this.image[CHARACTER] = new ImageIcon( imagePath + "character.png" ).getImage();
		this.image[GOAL     ] = new ImageIcon( imagePath + "goal.png" ).getImage();
		
		// 다른 패널로 이동할 수 있도록 준비
		this.frame = frame;
		this.cards = cards;
	}
	
	// 주변의 지뢰 탐색
	public int searchMine() {
		int count = 0;
		
		// 탐색 영역 설정
		int[][] searchArea = new int[4][2];
		for (int i = 0; i < searchArea.length; i++) {
		    searchArea[i] = new int[]{this.x, this.y};
		}
		searchArea[0][0] = ( this.x + LEFT <= minX ) ? minX : this.x + LEFT;
		searchArea[1][0] = ( this.x + RIGHT >= maxX ) ? maxX : this.x + RIGHT;
		searchArea[2][1] = ( this.y + UP <= minY ) ? minY : this.y + UP;
		searchArea[3][1] = ( this.y + DOWN >= maxY ) ? maxY : this.y + DOWN;
		
		// 탐색 후 갯수 세기
		for (int i = 0; i < searchArea.length; i++) {
			if (map[searchArea[i][1]][searchArea[i][0]] == MINE)
				count++;
		}
		
		return count;
	}
	
	// 지뢰에 닿으면 사망, 목적지에 도착하면 클리어
	@Override
	public void move(int directionX, int directionY) {
		if (map[y+directionY][x+directionX] == MINE) {
			this.x = 1;
			this.y = 1;
			this.directionX = STOP;
			this.directionY = STOP;
			cards.show(frame, "2");
		} else if (map[y+directionY][x+directionX] == GOAL) {
			this.x = 1;
			this.y = 1;
			this.directionX = STOP;
			this.directionY = STOP;
			cards.show(frame, "3");
		} else {
			super.move(directionX, directionY);
		}
	}
	
	// 캐릭터와 전체 맵을 출력(제목을 위한 상단 여백, 골인 지점 추가)
	@Override
	public void paint(Graphics g) {
		for( int y = 0; y <= map.length; y++ ){
			for( int x = 0; x <= map[0].length; x++ ){
				int index = MINE;
				if ( ( this.x == x ) && ( this.y == y ) )
					index = CHARACTER;
				else if ( ( minX <= x ) && ( x <= maxX ) && ( minY <= y ) && ( y <= maxY ) )
					index = map[y][x];
				g.drawImage( image[index], x*IMGSIZE, TOPMARGIN+y*IMGSIZE, IMGSIZE, IMGSIZE, null );
			}
		}
	}
}
