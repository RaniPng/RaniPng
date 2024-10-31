package Players;

import java.util.ArrayList;

import Entity.ItemAndSpell;
import Entity.Player;
import Prizes.Prizes;
import TileMap.TileMap;

public class Riku extends Player {

	// player stuff

	// magic


	// animations

	// animation actions
	private static int _animationCounter = 0;// 1
	private static final int _IDLE = _animationCounter++;// 1
	private static final int _WALKING = _animationCounter++;// 8
	private static final int _JUMPING = _animationCounter++;// 3
	private static final int _FALLING = _animationCounter++;// 3
	private static final int _LANDING = _animationCounter++;// 3
	private static final int _ROLLING = _animationCounter++;// 6
	private static final int _READY = _animationCounter++;// 6
	private static final int _COMBO1 = _animationCounter++;// 5
	private static final int _COMBO2 = _animationCounter++;// 5
	private static final int _COMBO3 = _animationCounter++;// 8
	private static final int _AIRATTACK = _animationCounter++;// 5
	private static final int _MAGICFIREBLIZZARD = _animationCounter++;// 5
	private static final int _MAGICHEALTHUNDER = _animationCounter++;// 7
	private static final int _TAKEITEM = _animationCounter++;// 7
	private static final int _INGUR = _animationCounter++;// 7
	private static final int _DEAD1 = _animationCounter++;// 4
	private static final int _DEAD2 = _animationCounter++;// 4
	private static final int _HOLDING = _animationCounter++;// 6
	private static final int _ROLLJUMP = _animationCounter++;// 4
	private static final int _WATING = _animationCounter++;// 4

	public Riku(TileMap tm) {

		super(tm);

		_NUMFRAMES = new int[] { 1, 8, 4, 2, 3, 2, 6, 5, 5, 8, 5, 5, 7, 7, 7, 4, 4, 6, 5, 3 };

		_FRAMEWIDTHS = new int[] { 20, 58, 44, 35, 34, 35, 39, 70, 76, 72, 52, 57, 44, 38, 1, 45, 55, 39, 38, 27 };
		_FRAMEHEIGHTS = new int[] { 56, 53, 53, 64, 53, 63, 44, 60, 47, 45, 74, 47, 80, 59, 48, 45, 24, 60, 44, 50 };

		_SPRITEDELAYS = new int[] { 2000, 80, 50, 200, 100, 60, 150, 100, 100, 100, 100, 160, 130, 120, 100, 600, 630,
				150, 70, 130 };

		_width = 47;
		_height = 55;
		// cwidth 25
		_cwidth = 25;
		// chight 40
		_cheight = 40;

		_moveSpeed = 0.2;
		_maxSpeed = 3.1;
		_stopSpeed = 0.13;
		_fallSpeed = 0.15;
		_maxFallSpeed = 3.0;
		_jumpStart = -5.0;// 4.8
		_stopJumpSpeed = 0.71;
		_rollSpeed = 0;
		_rollMax = 4.2;
		_rollStop = 1.4;

		_facingRight = true;

		_health = _maxHealth = 18.0;

		_magicBallCost = 200;
		_itemsAndMagic = new ArrayList<ItemAndSpell>();
		_items = new ArrayList<Prizes>();
		_orbs = new ArrayList<Prizes>();

		_str = 16;// 9
		_def = 2;
		_mp = _maxMp = 1800;

		_attackRange = 40;
		_type = PHYSICAL;
		_counterAttack = 1;
		_isInFight = false;
		_attacking = false;
		_airAttacking = false;
		setSprites("Riku.gif");
		setAnimation(_IDLE, -1);
	}

	public void setThundering() {
		if (_mp >= _magicBallCost && !_holding && !_jumpingOnEade && !_jumpingOnEade && !_attacking && !_landing
				&& !_rolling && !_firing && !_blizzarding && !_healing && !_dead)
			_thundering = true;
	}

	public void setFiring() {
		if (_mp >= _magicBallCost && !_holding && !_jumpingOnEade && !_jumpingOnEade && !_attacking && !_landing
				&& !_rolling && !_blizzarding && !_thundering && !_healing && !_dead)
			_firing = true;
	}

	public void setBlizzarding() {
		if (_mp >= _magicBallCost && !_holding && !_jumpingOnEade && !_jumpingOnEade && !_attacking && !_landing
				&& !_rolling && !_firing && !_thundering && !_healing && !_dead)
			_blizzarding = true;
	}

	public void setHealing() {
		if (_mp > 0 && !_jumpingOnEade && !_holding && !_jumpingOnEade && !_attacking && !_rolling && !_landing
				&& !_firing && !_blizzarding && !_dead)
			_healing = true;
	}

	@Override
	protected void getNextPosition() {
		// dead mouvments
		if (_dead) {
			switch (_animation.getFrame()) {
			case 0:
				if (_currentAction == _DEAD2)
					_dy = -0.2;
				else {
					_dy = -2.9;
					_dx = 1.4;
				}
				break;

			case 1:
				if (_currentAction == _DEAD2)
					_dy = 0;
				else {
					_dy = -0.6;
					_dx = 0.6;
				}
				break;
			case 2:
				if (_currentAction == _DEAD2) {
					_dy = 0.2;
				} else {
					_dy = 0.8;
					_dx = 0.4;
				}
				break;
			case 3:
				if (_currentAction == _DEAD2) {
					_dy = 0;
				} else {
					_dy = 0;
					_dx = 0;
				}
				break;
			}
			return;
		}

		// movement
		if (_left) {
			_dx -= _moveSpeed;
			if (_dx < -_maxSpeed) {
				_dx = -_maxSpeed;
			}
		} else if (_right) {
			_dx += _moveSpeed;
			if (_dx > _maxSpeed) {
				_dx = _maxSpeed;
			}
		} else {
			if (_dx > 0) {
				_dx -= _stopSpeed;
				if (_dx < 0) {
					_dx = 0;
				}
			} else if (_dx < 0) {
				_dx += _stopSpeed;
				if (_dx > 0) {
					_dx = 0;
				}
			}
		}
		if (_rolling) {
			_dx = 0;
			if (_currentAction == _ROLLING && _animation.getFrame() == 2)
				_animation.setSignificantFrame(true);

			if (_currentAction == _ROLLING && _animation.getSignificantFrame())
				_rollSpeed = _rollStop;
			else
				_rollSpeed = _rollMax;

			if (!_facingRight)
				_dx = -_rollSpeed;
			else
				_dx = _rollSpeed;
		}
		if (_attacking) {
			_dx = 0;
			if (!_facingRight)
				_dx = -0.4;
			else
				_dx = 0.4;
		}

		//
		if (_airAttacking) {
			_dy = 0.1;
			if (!_facingRight)
				_dx = -0.7;
			else
				_dx = 0.7;
		}

		if (_firing || _blizzarding) {
			if (!_facingRight)
				_dx = 0.2;
			else
				_dx = -0.2;
			if (_falling) {
				_dy = 0.1;
				_dx = 0;
			}
		}
		if (_thundering || _healing || _takeItem) {
			_dx = 0;
			if (_falling)
				_dy = 0.1;
		}

		// jumping
		if (_jumping && !_falling) {
			_dy = _jumpStart;
			_falling = true;
		}

		// falling
		if (_falling) {
			_dy += _fallSpeed;
			if (_dy > 0)
				_jumping = false;
			if (_dy < 0 && !_jumping)
				_dy += _stopJumpSpeed;

			if (_dy > _maxFallSpeed)
				_dy = _maxFallSpeed;
		} else if (_landing)
			_dx = 0;

		if (_holding) {
			_dy = 0;
			_dx = 0;
		}

		if (_jumpingOnEade) {
			_dy = -3;
			_dx = -2;
			if (_facingRight)
				_dx = -_dx;
		}

	}

	public void deadScreen() {
		_dead = true;
		_facingRight = false;
		stopAnyThing();
		if (_currentAction != _DEAD2) {
			setAnimation(_DEAD2, 0);
		}

	}
	
}
