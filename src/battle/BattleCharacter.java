package battle;

import java.util.Scanner;

// 전투 캐릭터
public class BattleCharacter {
	
	private int action, message, HP;
	private final int SHOOT = 0, DODGE = 1, KNIFE = 2;
	// 가위바위보 초기화
	public BattleCharacter() {
		this.action = SHOOT;
		this.HP = 100;
		this.message = 0;
	}
	
	public void setAction(int action) {
		this.action = action;
		return;
	}

	// 선택한 행동에 따라 데미지 계산
	public int calculateDamage() {
        // 데미지 계산
        switch(this.action) {
            case SHOOT:      // 총 쏘기
                if (Math.random() < 0.2) {
                    // 20% 빗나감
                	this.message = 1;
                    return 0;
                }
                this.message = 0;
                return 30;   // 30 데미지
            case DODGE:      // 피하기
                if (Math.random() < 0.6) {
                    // 60% 방어 성공
                	this.message = 2;
                    return 0;
                }// 40% 방어 실패
                this.message = 3;
                return 0;
            case KNIFE:      // 칼 공격
                if (Math.random() < 0.3) {
                    // 30% 확률로 자신도 15데미지 받음
                	this.message = 5;
                    return 45;
                }
                this.message = 4;
                return 45;   // 성공: 적에게 45
            default:
                return 0;
        }
    }
    
	// 데미지 적용
    public void fight(BattleCharacter counterpart) {    	
        int damageToPlayer = counterpart.calculateDamage();
        int damageToCounterpart = this.calculateDamage();
        
        if ((this.action == KNIFE) && (this.message == 5)) {
        	damageToPlayer += 15;
        }
        if ((counterpart.action == KNIFE) && (counterpart.message == 5)) {
        	damageToCounterpart += 15;
        }
        
        if ((this.action == DODGE) && (this.message == 2)) {
        	damageToPlayer = 0;
        	counterpart.rewriteMessage(6);
        }
        if ((counterpart.action == DODGE) && (counterpart.message == 2)) {
        	damageToCounterpart = 0;
        	this.message = 6;
        }
        
        this.HP -= damageToPlayer;
        counterpart.HP -= damageToCounterpart;
    }
    
    public boolean isDead() {
    	if (this.HP < 1)
    		return true;
    	else
    		return false;
    }
    
    public void rewriteMessage(int message) {
    	this.message = message;
    	return;
    }
    
    public int returnMessage() {
    	return this.message;
    }
    
    public int returnHP() {
    	return this.HP;
    }
}
