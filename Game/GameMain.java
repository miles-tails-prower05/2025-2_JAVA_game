// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/Game/GameMain.java

import java.awt.*;
import javax.swing.*;

import battle.*;
import move.*;

public class GameMain {

	public static void main(String[] args) {
		final String imagePath = "C:\\Users\\kunwo\\eclipse-workspace\\JAVA_Game\\src\\"; // 원본: C:\\Users\\user\\Downloads\\JAVA-main\\src\\
		final int WIDTH = 1280;
		final int HEIGHT= 720;
		// 미로 맵을 2차원 배열로 초기화
		int[][] map = { { 1,1,1,1,1,1,1 },
		                { 1,0,0,1,0,3,1 },
		                { 1,1,0,1,0,1,1 },
		                { 1,0,0,1,0,0,1 },
		                { 1,0,1,1,1,0,1 },
		                { 1,0,0,0,0,0,1 },
		                { 1,1,1,1,1,1,1 }
		              };
		
		// 패널 준비 : 패널번호, 패널이름, 기본 다음 패널번호, 실패시 다음 패널번호, 사용할 이미지, 버튼 텍스트, 버튼 이미지
		String[][] panels = {
				{ "1", "타이틀 화면",             "6", "", "title.png", "게임 시작", "button_Gstart.jpg" },
				{ "2", "스테이지 1: 게임 오버 화면", "6", "", "fail.png", "다시하기" },
				{ "3", "스테이지 2: 게임 오버 화면", "7", "", "fail.png", "다시하기" },
				{ "4", "스테이지 3: 게임 오버 화면", "8", "", "fail.png", "다시하기" },
				{ "5", "게임 클리어 화면",         "1", "", "success.png", "처음으로" },
				{ "6", "스테이지 1: 총알 피하기",   "7", "2"},
				{ "7", "스테이지 2: 지뢰 피하기",   "8", "3"},
				{ "8", "스테이지 3: 적과의 전투",   "5", "4"}
		};
		
		// 프레임에 여러 개의 패널을 카드처럼 쌓아서 배치하고 실행 준비 완료
		final JFrame frame = new JFrame("라이언 일병 구하기");
		final CardLayout cards = new CardLayout();
		frame.setLayout(cards);
		for( int i = 0; i < 5; i++ ) {
			frame.add(new SimplePanel(frame.getContentPane(), cards, panels[i], imagePath + "image\\"), panels[i][0]);
		}
		frame.add(new EnhancedBulletDodgePanel(frame.getContentPane(), cards, panels[5], new CollidableObject(imagePath+"move\\image\\soldier.png", 70, WIDTH-70, HEIGHT/2, WIDTH, HEIGHT ), imagePath+"move\\image\\", 70), panels[5][0]);
		frame.add(new MineMazePanel(new MineMazeCharacter(frame.getContentPane(), cards, panels[6], map, 1, 1, imagePath + "move\\image\\", 40)), panels[6][0]);
		frame.add(new BattlePanel(frame.getContentPane(), cards, panels[7], imagePath+"battle\\image\\"), panels[7][0]);
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
