package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import TileMap.TileMap;

public abstract class Character extends MapObject {
	// stat bar
	protected double _health;
	protected double _maxHealth;
	protected boolean _dead;
	protected boolean _flinching;
	protected long _flinchTimer;

	// attacks
	protected int _counterAttack;
	protected boolean _attacking;
	protected boolean _isInFight;
	
	
	//types
	protected String _type;
	public final static String FIRE = "fire";
	public final static String BLIZZARD = "blizzard";
	public final static String THUNDER = "thunder";
	public final static String PHYSICAL = "physical";
	

	//// stats
	// atckk
	protected int _str;
	protected int _attackRange;

	// defence
	protected int _def;

	// magic
	protected int _mp;
	protected int _maxMp;
	protected double _magicDamage;

	// animations
	protected ArrayList<BufferedImage[]> _sprites;

	public Character(TileMap tm) {
		super(tm);
	}

	//calcualte damage
	public final double getDamage(Character tokef, String attackType) {
		double strVsDef=tokef._str - this._def;
		if(strVsDef<=0)
			strVsDef=0.1;
		
		double damge = strVsDef * ((double) tokef._counterAttack / 2) * getRes(attackType);// *game mode
		return damge;
	}

	
	public final double getRes(String attackType) {
		
		if(attackType.toString()==_type.toString())
		return 2.0;
		
		if ((attackType == FIRE && _type == BLIZZARD) || (attackType == BLIZZARD && _type == FIRE))
			return 2.0;
		
		if (attackType!=PHYSICAL &&attackType == _type)
			return 1.6;
		
		if ((_type == PHYSICAL && attackType == THUNDER) || (_type == THUNDER && attackType == PHYSICAL))
				return 1.4;
		
		if(attackType==THUNDER && (_type==FIRE || _type==BLIZZARD))
			return 1.7;
		
		if((attackType==FIRE ||attackType==BLIZZARD) && (_type==PHYSICAL ||_type==THUNDER))
			return 1.7;
		
			return 0.99;
	}
	

	public void hit(Character tokef, String attackType) {
		double damage = getDamage(tokef, attackType);		
		if (_dead || _flinching)
			return;
		_health -= damage;
		if (_health < 0)
			_health = 0;
		if (_health == 0)
			_dead = true;
		_flinching = true;
		_flinchTimer = System.nanoTime();
		System.out.println(_health);
	}
	
	
	
	public void draw(Graphics2D g) {

		setMapPosition();

		// draw player
		if (_flinching) {
			long elapsed = (System.nanoTime() - _flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}

	public boolean isFlinching() {
		return _flinching;
	}
	
	public boolean isDead() {
		return _dead;
	}
	public void setDead(boolean isdead) {
		_dead=isdead;
		if(_dead) 
			_health=0;
	
	}

	public double getHealth() {
		return _health;
	}
	public void addHealth(double add) {
		_health+=_maxHealth*add;
		if(_health>_maxHealth)
			_health=_maxHealth;
	}

	public double getMaxHealth() {
		return _maxHealth;
	}

	public int getStr() {
		return _str;
	}

	public int getDef() {
		return _def;
	}

	public int getMp() {
		return _mp;
	}
	public void addMp(double add) {
		_mp+=_maxMp*add;
		if(_mp>_maxMp)
			_mp=_maxMp;
	}
	public int getMaxMp() {
		return _maxMp;
	}

}
