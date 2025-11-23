package battle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// 전투 패널
public class BattlePanel extends JPanel implements ActionListener {
	protected final int SHOOT = 0, DODGE = 1, KNIFE = 2, IDLE = 3;
	protected final int PLAYER = 0, ENEMY = 1;
	protected final String[] playerActionMessage, enemyActionMessage;
	private Container frame;
	private CardLayout cards;
	private String[] panel;
	private ImageIcon backgroundImage;
    private String imagePath;
	protected ImageIcon[][] image;
	protected BattleCharacter player, enemy;
	protected JLabel stageInfoLabel, imgPlayer, imgEnemy, playerMessage, enemyMessage;
	protected JProgressBar playerHPBar, enemyHPBar;
	protected JButton[] button;
	protected Timer timer;
	
	public BattlePanel(Container frame, CardLayout cards, String[] panel, String imagePath) {
		setLayout(new BorderLayout());
		
		this.playerActionMessage = new String[] { "총을 쏴서 데미지를 입혔습니다",
				   			   				      "총을 쐈지만 빗나갔습니다",
			   			   				      	  "방어에 성공했습니다",
			   			   				      	  "방어에 실패했습니다",
			   			   				      	  "칼로 공격하여 데미지를 입혔습니다",
			   			   				      	  "칼로 공격하는데 성공했지만, 자신도 데미지를 입었습니다",
			   			   				      	  "상대가 회피하였습니다",
			   			   				      	  " "
										  		};
		this.enemyActionMessage = new String[] { "적군이 총을 쏴서 데미지를 입혔습니다",
				      							 "적군이 총을 쐈지만 빗나갔습니다",
				      							 "적군이 방어에 성공했습니다",
				      							 "적군이 방어에 실패했습니다",
				      							 "적군이 칼로 공격하여 데미지를 입혔습니다",
				      							 "적군이 칼로 공격하는데 성공했지만, 자신도 데미지를 입었습니다",
				      							 "상대가 회피하였습니다",
				      							 " "
		  									   };
		
		// 가위바위보 3가지, 미선택 1가지, 승패 2가지를 고려하여 이미지를 준비
		image = new ImageIcon[4][2];
		image[SHOOT][PLAYER] = new ImageIcon( new ImageIcon( imagePath + "character3_gun.png"  ).getImage().getScaledInstance( 200, 200, Image.SCALE_SMOOTH ) );
		image[SHOOT][ENEMY ] = new ImageIcon( new ImageIcon( imagePath + "enemy_gun.png"       ).getImage().getScaledInstance( 200, 200, Image.SCALE_SMOOTH ) );
		image[DODGE][PLAYER] = new ImageIcon( new ImageIcon( imagePath + "character3_avoid.png").getImage().getScaledInstance( 200, 200, Image.SCALE_SMOOTH ) );
		image[DODGE][ENEMY ] = new ImageIcon( new ImageIcon( imagePath + "enemy_avoid.png"     ).getImage().getScaledInstance( 200, 200, Image.SCALE_SMOOTH ) );
		image[KNIFE][PLAYER] = new ImageIcon( new ImageIcon( imagePath + "character3_knife.png").getImage().getScaledInstance( 200, 200, Image.SCALE_SMOOTH ) );
		image[KNIFE][ENEMY ] = new ImageIcon( new ImageIcon( imagePath + "enemy_knife.png"     ).getImage().getScaledInstance( 200, 200, Image.SCALE_SMOOTH ) );
		image[IDLE ][PLAYER] = new ImageIcon( new ImageIcon( imagePath + "character3_idle.png" ).getImage().getScaledInstance( 200, 200, Image.SCALE_SMOOTH ) );
		image[IDLE ][ENEMY ] = new ImageIcon( new ImageIcon( imagePath + "enemy_idle.png"      ).getImage().getScaledInstance( 200, 200, Image.SCALE_SMOOTH ) );
		
		// 화면 위쪽에 가위바위보 이미지 초기화
		
		
		// GUI 준비
        this.backgroundImage = new ImageIcon(new ImageIcon(imagePath + "bg_stage3.png").getImage());
		
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setOpaque(false);
		stageInfoLabel = new JLabel();
		stageInfoLabel.setFont( new Font("맑은 고딕", Font.BOLD, 32) );
		stageInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		stageInfoLabel.setText("스테이지 3: 적과의 전투");
		topPanel.add(stageInfoLabel, BorderLayout.NORTH);
		
		enemyMessage = new JLabel(enemyActionMessage[7]);
        enemyMessage.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
        enemyMessage.setForeground(Color.WHITE);
        enemyMessage.setHorizontalAlignment(SwingConstants.RIGHT);
        topPanel.add(enemyMessage, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);
		
		JPanel battleArena = new JPanel(new GridLayout(2, 1)); // 2행 1열 (위: 적, 아래: 나)
		battleArena.setOpaque(false);
		
		JPanel enemyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
		enemyPanel.setOpaque(false);
        enemyHPBar = createHPBar(Color.RED);
        imgEnemy = new JLabel(image[IDLE][ENEMY]);
        enemyPanel.add(enemyHPBar);
        enemyPanel.add(imgEnemy);
        
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        playerPanel.setOpaque(false);
        imgPlayer = new JLabel(image[IDLE][PLAYER]);
        
        JPanel playerControlPanel = new JPanel(new BorderLayout());
        playerControlPanel.setOpaque(false);
        playerHPBar = createHPBar(Color.GREEN); // 아군은 초록색
        playerControlPanel.add(playerHPBar, BorderLayout.NORTH);
        playerPanel.add(imgPlayer);
        playerPanel.add(playerControlPanel);
        
        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 5, 5)); // 1행 3열, 간격 5
        btnPanel.setOpaque(false);
		// 화면 아래쪽에 가위바위보 입력 버튼 초기화
		button = new JButton[3];
		button[SHOOT] = new JButton("총 쏘기");
		button[DODGE] = new JButton("피하기");
		button[KNIFE] = new JButton("칼로 공격하기");
		button[SHOOT].addActionListener( this );
		button[DODGE].addActionListener( this );
		button[KNIFE].addActionListener( this );
		btnPanel.add( button[SHOOT] );
		btnPanel.add( button[DODGE] );
		btnPanel.add( button[KNIFE] );
		playerControlPanel.add(btnPanel, BorderLayout.CENTER);
		
		battleArena.add(enemyPanel);
        battleArena.add(playerPanel);
        add(battleArena, BorderLayout.CENTER);
        
        playerMessage = new JLabel(playerActionMessage[7]);
        playerMessage.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
        playerMessage.setForeground(Color.WHITE);
        add(playerMessage, BorderLayout.SOUTH);
		
		// 다른 패널로 이동할 수 있도록 준비
 		this.frame = frame;
 		this.cards = cards;
 		this.panel = panel;
		
		// 전투 준비
 		this.player = new BattleCharacter();
		this.enemy = new BattleCharacter();
		this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
            	BattlePanel.this.timer = new Timer( 1000, new ActionListener() {
        			@Override
        			public void actionPerformed( ActionEvent event ) {
        				ready();
        				timer.stop();
        			}
        		});
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            	BattlePanel.this.player = new BattleCharacter();
            	BattlePanel.this.enemy = new BattleCharacter();
            	imgPlayer.setIcon( image[IDLE][ PLAYER ] );
        		imgEnemy.setIcon( image[IDLE][ ENEMY ] );
        		
        		// 체력바 리셋
                playerHPBar.setValue(100);
                playerHPBar.setString("체력: 100");
                enemyHPBar.setValue(100);
                enemyHPBar.setString("체력: 100");
            	
                playerMessage.setText(playerActionMessage[7]);
                enemyMessage.setText(enemyActionMessage[7]);
            }
        });
	}
	
	private JProgressBar createHPBar(Color color) {
        JProgressBar bar = new JProgressBar(0, 100); // 최소 0, 최대 100
        bar.setValue(100);                           // 초기값
        bar.setStringPainted(true);                  // 글자 표시 허용
        bar.setString("체력: 100");                  // 초기 글자
        bar.setForeground(color);                    // 게이지 색상
        bar.setBackground(Color.LIGHT_GRAY);         // 배경 색상
        bar.setPreferredSize(new Dimension(200, 30)); // 크기 지정 (너비 200, 높이 30)
        
        bar.setFont(new Font("맑은 고딕", Font.BOLD, 15)); // 글자 폰트
        bar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
        	@Override
            protected Color getSelectionBackground() {
                return Color.BLACK; // 게이지가 없는(배경) 부분 위의 글자 색
            }
            @Override
            protected Color getSelectionForeground() {
                return Color.BLACK; // 게이지가 찬(Foreground) 부분 위의 글자 색
            }
        });
        
        return bar;
    }
	
	private void updateHPBar(JProgressBar bar, int hp) {
        bar.setValue(hp);
        bar.setString("체력: " + hp);
    }
	
	// 물음표 제시하고 버튼 활성화
	public void ready() {
		imgPlayer.setIcon( image[IDLE][PLAYER] );
		imgEnemy.setIcon( image[IDLE][ENEMY] );
		button[0].setEnabled( true );
		button[1].setEnabled( true );
		button[2].setEnabled( true );
	}
	
	// 가위바위보 내기
	@Override
	public void actionPerformed( ActionEvent event ) {
		// 승패 결과 제시
		int player = select( event );
		int enemy = (int)( Math.random() * 3 );
		show( player, enemy );
		// 다음 판은 잠시 대기
		timer.start();
	}
	
	// 승패 결과 제시하고 버튼 비활성화
	public void show( int playerA, int playerB ) {
		player.setAction(playerA);
		enemy.setAction(playerB);
		player.fight(enemy);
		
		if (enemy.isDead() == true) {
			cards.show(frame, panel[2]);
			enemyMessage.setText("적군 사망");
			return;
		} else if (player.isDead() == true) {
			cards.show(frame, panel[3]);
			playerMessage.setText("플레이어 사망");
			return;
		}
		
		imgPlayer.setIcon( image[ playerA ][ PLAYER ] );
		imgEnemy.setIcon( image[ playerB ][ ENEMY ] );
		updateHPBar(playerHPBar, player.returnHP());
        updateHPBar(enemyHPBar, enemy.returnHP());
        playerMessage.setText(playerActionMessage[player.returnMessage()]);
        enemyMessage.setText(enemyActionMessage[enemy.returnMessage()]);
		
		button[0].setEnabled( false );
		button[1].setEnabled( false );
		button[2].setEnabled( false );
	}
	
	public int select( ActionEvent event ) {
		if( event.getSource() == button[SHOOT] )
			return SHOOT;
		else if ( event.getSource() == button[DODGE] )
			return DODGE;
		else
			return KNIFE;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g); // 기본적인 JPanel 그리기를 수행
	    
	    if (backgroundImage != null) {
	        // 패널의 크기에 맞게 배경 이미지를 늘려서 그립니다.
	        g.drawImage(
	            backgroundImage.getImage(), 
	            0, 0, // 시작 위치
	            getWidth(), getHeight(), // 그릴 영역의 너비와 높이
	            this // ImageObserver
	        );
	    }
	}
}
