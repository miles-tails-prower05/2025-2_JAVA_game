// 소스코드 - https://github.com/miles-tails-prower05/2025-2_JAVA_game/blob/main/src/battle/BattleCharacter.java

package battle;

// 전투 캐릭터 클래스
public class BattleCharacter {
	
	private int action, message, HP;
	private final int SHOOT = 0, DODGE = 1, KNIFE = 2;
	
	// 캐릭터 초기화
	public BattleCharacter() {
		this.action = SHOOT;
		this.HP = 100;
		this.message = 0;
	}
	
	// 행동 선택: 전달인자로 받아 인스턴스 변수에 저장
	public void setAction(int action) {
		this.action = action;
		return;
	}

	// 선택한 행동에 따라 데미지 계산
	public int calculateDamage() {
        switch(this.action) {
            case SHOOT:
                if (Math.random() < 0.2) { // 20%는 빗나감
                	this.message = 1;
                    return 0;
                }
                this.message = 0;
                return 30;
            case DODGE:
                if (Math.random() < 0.6) { // 60%는 방어 성공
                	this.message = 2;
                    return 0;
                }
                this.message = 3; // 40%는 방어 실패
                return 0;
            case KNIFE:
                if (Math.random() < 0.3) { // 30% 확률로 자신도 15데미지 받음
                	this.message = 5;
                    return 45;
                }
                this.message = 4;
                return 45;
            default:
                return 0;
        }
    }
    
	// 데미지 적용하여 HP 업데이트
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
    
    // 캐릭터의 생사여부 확인
    public boolean isDead() {
    	if (this.HP < 1)
    		return true;
    	else
    		return false;
    }
    
    // 행동에 대한 메시지 변경
    public void rewriteMessage(int message) {
    	this.message = message;
    	return;
    }
    
    // 행동에 대한 메시지 반환
    public int returnMessage() {
    	return this.message;
    }
    
    // 캐릭터의 HP 반환
    public int returnHP() {
    	return this.HP;
    }
}
