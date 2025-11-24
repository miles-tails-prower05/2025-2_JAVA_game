// 소스파일 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/Game/GameMain.java

import java.awt.*;
import javax.swing.*;

import battle.*;
import move.*;

public class GameMain {
	// 프로그램 시작
	public static void main(String[] args) {
		final String imagePath = "C:\\Users\\kunwo\\eclipse-workspace\\JAVA_Game\\src\\"; // 원본: C:\\Users\\user\\Downloads\\JAVA-main\\src\\
		final int WIDTH = 1280;
		final int HEIGHT= 720;
		// 미로 맵을 2차원 배열로 초기화
		int[][] map = { {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,3},
			    		{0,1,0,0,0,0,1,0,1,0,0,1,0,1,0,0,0},
		    			{0,0,1,0,0,1,0,0,0,1,0,0,1,0,0,1,0},
		    			{0,0,0,1,0,0,0,1,0,0,1,0,0,0,1,0,0},
		    			{1,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0},
		    			{0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    			{0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0}
					  };
		Image icon = new ImageIcon(imagePath + "move\\image\\character1.png").getImage();
		
		// 패널 준비 : 패널번호, 패널이름, 기본 다음 패널번호, 실패시 다음 패널번호, 사용할 이미지, 버튼 텍스트, 버튼 이미지
		String[][] panels = {
				{ "1",  "타이틀 화면",             "2", "", "title.png",       "게임시작", "button_Gstart.jpg" },
				{ "2",  "스테이지 1: 게임 정보 화면", "9", "", "info_stage1.png", "시작하기", "button_Sstart.jpg" },
				{ "3",  "스테이지 1: 게임 오버 화면", "9", "", "fail.png",        "다시하기", "button_retry.jpg" },
				{ "4",  "스테이지 2: 게임 정보 화면", "10","", "info_stage2.png", "시작하기", "button_Sstart.jpg" },
				{ "5",  "스테이지 2: 게임 오버 화면", "10","", "fail.png",        "다시하기", "button_retry.jpg" },
				{ "6",  "스테이지 3: 게임 정보 화면", "11","", "info_stage3.png", "시작하기", "button_Sstart.jpg" },
				{ "7",  "스테이지 3: 게임 오버 화면", "11","", "fail.png",        "다시하기", "button_retry.jpg" },
				{ "8",  "게임 클리어 화면",         "1", "", "success.png",     "처음으로", "button_restart.jpg" },
				{ "9",  "스테이지 1: 총알 피하기",   "4", "3"},
				{ "10", "스테이지 2: 지뢰 피하기",   "6", "5"},
				{ "11", "스테이지 3: 적과의 전투",   "8",  "7"}
		};
		
		// 프레임에 여러 개의 패널을 카드처럼 쌓아서 배치하고 실행 준비 완료
		final JFrame frame = new JFrame("라이언 일병 구하기");
		frame.setIconImage(icon);
		final CardLayout cards = new CardLayout();
		frame.setLayout(cards);
		for( int i = 0; i < 8; i++ ) {
			frame.add(new SimplePanel(frame.getContentPane(), cards, panels[i], imagePath + "image\\"), panels[i][0]);
		}
		frame.add(new EnhancedBulletDodgePanel(frame.getContentPane(), cards, panels[8], new CollidableObject(imagePath+"move\\image\\character1.png", 70, WIDTH-70, HEIGHT/2, WIDTH, HEIGHT-130 ), imagePath+"move\\image\\", 70), panels[8][0]);
		frame.add(new MineMazePanel(new MineMazeCharacter(frame.getContentPane(), cards, panels[9], map, 0, 0, imagePath + "move\\image\\", 75)), panels[9][0]);
		frame.add(new BattlePanel(frame.getContentPane(), cards, panels[10], imagePath+"battle\\image\\"), panels[10][0]);
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	// 프레임의 X 버튼 누르면 프로그램 종료
}
