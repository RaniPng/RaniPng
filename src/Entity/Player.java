package Entity;

import TileMap.*;

import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Prizes.Prizes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Player extends Character {

	// player stuff
	protected boolean _firing;
	protected boolean _blizzarding;
	protected boolean _thundering;
	protected boolean _healing;
	protected int _magicBallCost;
	protected int _thunderBoltCost;

	// magic and items
	protected boolean _takeItem;
	protected ArrayList<ItemAndSpell> _itemsAndMagic;
	protected ArrayList<Prizes> _items;
	protected ArrayList<Prizes> _orbs;
	protected BufferedImage _glows[];

	// fight
	protected boolean _airAttacking;
	protected long _cooldown;

	// ROLLING
	protected boolean _rolling;
	protected double _rollSpeed;
	protected double _rollMax;
	protected double _rollStop;
	protected int _rollCount;

	// Holding edge
	protected boolean _holding;
	protected boolean _jumpingOnEade;
	protected boolean _canHold;

	protected long _wating;

	// animations
	protected ArrayList<BufferedImage[]> _sprites;
	protected int[] _NUMFRAMES;

	protected int[] _FRAMEWIDTHS;
	protected int[] _FRAMEHEIGHTS;

	protected int[] _SPRITEDELAYS;

	// animation actions
	protected static int _animationCounter = 0;// 1

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

	public Player(TileMap tm) {
		super(tm);
	}

	protected void setSprites(String name) {
		// load sprites
		int count = 10;// the distance between sprites in the sprites sheet
		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/" + name));

			_sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < _animationCounter; i++) {
				BufferedImage[] bi = new BufferedImage[_NUMFRAMES[i]];
				for (int j = 0; j < _NUMFRAMES[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * _FRAMEWIDTHS[i], count, _FRAMEWIDTHS[i], _FRAMEHEIGHTS[i]);
				}
				_sprites.add(bi);
				count += _FRAMEHEIGHTS[i] + 10;

			}

			spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/glow.png"));
			_glows = new BufferedImage[2];
			for (int j = 0; j < _glows.length; j++)
				_glows[j] = spritesheet.getSubimage(j * 20, 0, 20, 25);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeSprites() {
		for (int i = 0; i < _animationCounter; i++) 
			for (int j = 0; j < _NUMFRAMES[i]; j++)
				try {
					ImageIO.write(_sprites.get(i)[j], "gif",new File(i+" "+j+".gif"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	}

	public void reset() {
		_health = _maxHealth;
		_facingRight = true;
		stopAnyThing();
	}

	public void setReady() {
		_isInFight = true;
	}

	public void setHolding(boolean b) {
		if (!_canHold)
			return;
		if (b)
			stopAnyThing();

		_holding = b;
	}

	public void setRolling() {
		if (!_jumpingOnEade && !_jumping && !_falling && !_landing && !_firing && !_thundering && !_healing
				&& !_blizzarding && !_attacking && !_dead) {
			stopAnyThing();
			_rolling = true;
		}
	}

	public void setLeft(boolean b) {
		if (!b)
			_left = b;

		if (!_jumpingOnEade && !_firing && !_landing && !_blizzarding && !_thundering && !_healing && !_rolling
				&& !_attacking && !_dead) {
			_left = b;
			if (_holding)
				_canHold = false;
		}
	}

	public void setRight(boolean b) {
		if (!b)
			_right = b;

		if (!_jumpingOnEade && !_firing && !_landing && !_blizzarding && !_thundering && !_healing && !_rolling
				&& !_attacking && !_dead) {
			_right = b;
			if (_holding)
				_canHold = false;
		}
	}

	public void setJumping(boolean b) {
		if (!b)
			_jumping = b;

		if (!_jumpingOnEade && !_firing && !_landing && !_blizzarding && !_thundering && !_healing && !_rolling
				&& !_attacking && !_dead) {
			_jumping = b;
			if (_holding)
				_canHold = false;

		}

	}

	public void setAttacking() {

		if (!_jumpingOnEade && !_holding && !_firing && !_landing && !_blizzarding && !_thundering && !_healing
				&& !_rolling) {
			if (!_falling && !_jumping && !_dead) {
				_attacking = true;
			} else
				_airAttacking = true;

		}
	}

	public void setTaking() {
		if (!_items.isEmpty() && !_jumpingOnEade && !_jumping && !_falling && !_landing && !_firing && !_thundering
				&& !_healing && !_blizzarding && !_attacking && !_dead) {
			_takeItem = true;
		}
	}

	public boolean isHolding() {
		return _holding;
	}

	public void _setHolding(boolean b) {
		_canHold = b;
		_holding = false;
	}

	public void rollJumping() {
		_jumpingOnEade = true;
		_canHold = false;
		_holding = false;
	}

	public void checkAttack(ArrayList<Enemy> enemies) {

		// loop through enemies
		for (int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);
			if (e == null || e.isDead())
				continue;

			// attack
			if ((_attacking || _airAttacking) && _animation.getFrame() == 3) {
				_cooldown = System.nanoTime();
				if (_facingRight) {
					if (e.getx() > _x && e.getx() < _x + _attackRange && e.gety() > _y - _height / 2
							&& e.gety() < _y + _height / 2) {
						e.hit(this, this._type);
						_isInFight = true;
						_cooldown = System.nanoTime();
					}
				} else {
					if (e.getx() < _x && e.getx() > _x - _attackRange && e.gety() > _y - _height / 2
							&& e.gety() < _y + _height / 2) {
						e.hit(this, this._type);
						_isInFight = true;
						_cooldown = System.nanoTime();
					}
				}
			}

			// magic attack
			for (int j = 0; j < _itemsAndMagic.size(); j++) {
				ItemAndSpell magic = _itemsAndMagic.get(j).shouldHit();
				if (magic != null && !(magic instanceof Heal) && magic.intersects(e)) {
					e.hit(this, magic.getType());
					_isInFight = true;
					_itemsAndMagic.get(j).setHit();
					break;
				}
			}

			// check enemy collision

			if (intersects(e) && !_rolling) {
				hit(e, e._type);
			}

		}

	}

	protected abstract void getNextPosition();

	public void dead() {
		_facingRight = false;
		stopAnyThing();
		// dead 1
		if (_currentAction != _DEAD1 && _currentAction != _DEAD2) {
			setAnimation(_DEAD1, -1);
		}

		_animation.update();
	}

	protected void setAnimation(int i, int l) {
		_currentAction = i;
		_animation.setFrames(_sprites.get(_currentAction), l);
		_animation.setDelay(_SPRITEDELAYS[_currentAction]);
		_width = _FRAMEWIDTHS[_currentAction];
		_height = _FRAMEHEIGHTS[_currentAction];
	}

/////////////////////////////////////////////////////////
	protected boolean needToHoldEdge() {
		int block, res, upperTile, upperTile2, freeTile;
		// if player doesnt fall
		if (!_falling || _jumping)
			return false;

		if (_facingRight)
			block = (int) (_x - _cwidth / 2 - 3) / _tileSize + 1;
		else
			block = (int) (_x + _cwidth / 2 - 1 + 3) / _tileSize - 1;

		// 30 is plus one all
		upperTile = (int) (_y + _cheight / 2 + 3) / _tileSize - 1;
		upperTile2 = (int) (_y - _cheight / 2 - 3) / _tileSize;
		freeTile = (int) (_y - _cheight / 2) / _tileSize - 1;

		// if the player head is not bettween the 3 pixel of the Tile and the Tile above
		// it is not Free-Normal
		if (upperTile != upperTile2 || _tileMap.getType(freeTile, block) != Tile.NORMAL)
			return false;

		res = _tileMap.getType(upperTile, block);

		// System.out.println(upperTile+" "+upperTile2+" "+freeTile);

		return res == Tile.BLOCKED;
	}

	public void update() {

		// update position
		getNextPosition();

		checkTileMapCollision();
		if (!_holding && (_holding = _canHold && needToHoldEdge())) {
			stopAnyThing();
			_ytemp = (int) ((_y - _cheight / 2) / _tileSize + 1) * _tileSize;

			if (_facingRight)
				_xtemp = (int) ((_x - _cwidth / 2) / _tileSize) * _tileSize + _tileSize / 2;
			else
				_xtemp = (int) ((_x + _cwidth / 2 - 1) / _tileSize) * _tileSize + _tileSize / 2;
		}
		setPosition(_xtemp, _ytemp);

		if (_dead) {
			dead();
			return;
		}

		for (int i = 0; i < _orbs.size(); i++) {
			_orbs.get(i).flyingAroundPlayer(_dx, _dy);
			if (_orbs.get(i).shuoldRemove()) {
				_orbs.get(i).use(this);
				_orbs.remove(i);
			}
		}

		if (_isInFight && !_attacking) {
			long elapsed = (System.nanoTime() - _cooldown) / 1000000;
			if (elapsed > 3000)
				_isInFight = false;
		}

		if (_landing && _currentAction == _LANDING && _animation.hasPlayedOnce()) {
			_landing = false;
			_canHold = true;
		}
		if (!_isInFight)
			_counterAttack = 1;

		if (_airAttacking)
			if (_currentAction == _AIRATTACK && _animation.hasPlayedOnce())
				_airAttacking = false;
		if (_jumpingOnEade)
			if (_currentAction == _ROLLJUMP && _animation.hasPlayedOnce()) {
				_jumpingOnEade = false;
				_landing = true;
			}

		if (_firing || _blizzarding) {

			if (_animation.getFrame() == 2 && !_animation.getSignificantFrame()) {
				_animation.setSignificantFrame(true);
				// magicball attack
				if (_mp >= _maxMp)
					_mp = _maxMp;

				if (_mp >= _magicBallCost) {
					_mp -= _magicBallCost;

					MagicBall mb = null;
					if (_firing)
						mb = new Fire(_tileMap, _facingRight);
					if (_blizzarding)
						mb = new Blizzard(_tileMap, _facingRight);

					double x = _x + 30;
					if (!_facingRight)
						x = _x - 30;
					double y = _y - 8 + _lowY;
					mb.setPosition(x, y);
					_itemsAndMagic.add(mb);
				}
			} else if (_animation.hasPlayedOnce()) {
				_firing = false;
				_blizzarding = false;
				_animation.setSignificantFrame(false);
			}
		} else if (_thundering) {
			if (_animation.getFrame() == 4 && !_animation.getSignificantFrame()) {
				_animation.setSignificantFrame(true);
				// magicball attack
				if (_mp >= _maxMp)
					_mp = _maxMp;

				if (_mp >= _magicBallCost) {
					_mp -= _magicBallCost;
					double x = _x + 6;
					if (!_facingRight)
						x = _x - 6;
					double y = _y - 45 + _lowY;
					Thunder t = new Thunder(_tileMap, _facingRight, x, y);
					_itemsAndMagic.add(t);
				}
			} else if (_currentAction == _MAGICHEALTHUNDER && _animation.hasPlayedOnce()) {
				_thundering = false;
				_animation.setSignificantFrame(false);
			}

		} else if (_healing) {
			if (_animation.getFrame() == 4 && !_animation.getSignificantFrame()) {
				_animation.setSignificantFrame(true);
				if (_mp > 0) {
					_mp = 0;
					_health += _maxHealth * 0.35;
					if (_health > _maxHealth)
						_health = _maxHealth;
					double x = _x + 6;
					if (!_facingRight)
						x = _x - 6;
					double y = _y - 45 + _lowY;
					Heal h = new Heal(_tileMap, _facingRight, x, y);
					_itemsAndMagic.add(h);

				}
			} else if (_currentAction == _MAGICHEALTHUNDER && _animation.hasPlayedOnce()) {
				_healing = false;
				_animation.setSignificantFrame(false);
			}
		}

		if (_takeItem) {
			if (_animation.getFrame() == 4 && !_animation.getSignificantFrame()) {
				_animation.setSignificantFrame(true);
				if (!_items.isEmpty()) {
					TakeItem(_items.remove(0));
					if (_health > _maxHealth)
						_health = _maxHealth;
					if (_mp > _maxMp)
						_mp = _maxMp;
					double x = _x;

					double y = _y - 35 + _lowY;
					ItemTake h = new ItemTake(_tileMap, _facingRight, x, y);
					_itemsAndMagic.add(h);
				}
			} else if (_currentAction == _TAKEITEM && _animation.hasPlayedOnce()) {
				_takeItem = false;
				_animation.setSignificantFrame(false);
			}
		}

		// update
		for (int i = 0; i < _itemsAndMagic.size(); i++) {
			_itemsAndMagic.get(i).update();
			if (_itemsAndMagic.get(i).shouldRemove()) {
				_itemsAndMagic.remove(i);
				i--;
			}
		}

		// check done flinching
		if (_flinching) {
			long elapsed = (System.nanoTime() - _flinchTimer) / 1000000;
			if (elapsed > 1000) {
				_flinching = false;
			}
		}

		// check done rolling
		if (_rolling && _currentAction == _ROLLING && _animation.hasPlayedOnce()) {
			_animation.setSignificantFrame(false);
			_rolling = false;

		}
		// check done attacking
		if (_attacking) {
			if (_animation.hasPlayedOnce()) {
				_attacking = false;
				if (_counterAttack == 3)
					_counterAttack = 1;
				else
					_counterAttack++;
			}
		}

		// set animation

		// attacking
		if (_attacking) {
			if (!_falling && !_jumping && _currentAction != _READY + _counterAttack) {
				setAnimation(_READY + _counterAttack, -1);
			}
		}

		else if (_airAttacking) {
			if (_currentAction != _AIRATTACK) {
				setAnimation(_AIRATTACK, -1);
			}
		}

		// Healing
		else if (_healing) {
			if (_currentAction != _MAGICHEALTHUNDER) {
				setAnimation(_MAGICHEALTHUNDER, -1);
			}
		} else if (_takeItem) {
			if (_currentAction != _TAKEITEM)
				setAnimation(_TAKEITEM, -1);

		}
		// magic ball
		else if (_firing || _blizzarding) {
			if (_currentAction != _MAGICFIREBLIZZARD) {
				setAnimation(_MAGICFIREBLIZZARD, -1);
			}
		}

		// thunder
		else if (_thundering) {
			if (_currentAction != _MAGICHEALTHUNDER) {
				setAnimation(_MAGICHEALTHUNDER, -1);
			}
		} else if (_jumpingOnEade) {
			if (_currentAction != _ROLLJUMP)
				setAnimation(_ROLLJUMP, -1);
		}
		// faling
		else if (_dy > 0) {
			if (_currentAction != _FALLING) {
				setAnimation(_FALLING, -1);
				_rolling = false;
			}
		} else if (_landing) {
			if (_currentAction != _LANDING)
				setAnimation(_LANDING, -1);
		} else if (_holding) {
			if (_currentAction != _HOLDING)
				setAnimation(_HOLDING, -1);
		}

		// jumping
		else if (_dy < 0) {
			if (_currentAction != _JUMPING) {
				setAnimation(_JUMPING, -1);

			}
		}
		// runing to one side
		else if (_left || _right) {
			if (_currentAction != _WALKING) {
				setAnimation(_WALKING, 0);
			}
		} else if (_rolling) {
			if (_currentAction != _ROLLING) {
				setAnimation(_ROLLING, -1);

			}
		}
		// fight
		else if (_isInFight) {
			if (_currentAction != _READY) {
				setAnimation(_READY, 0);
			}
		}

		// standing
		else {
			if (_wating > _SPRITEDELAYS[_IDLE] / 4) {
				if (_currentAction != _WATING) {
					setAnimation(_WATING, -1);
				}
			} else if (_currentAction != _IDLE) {
				setAnimation(_IDLE, -1);
			}
		}

		// wating animation
		if (_currentAction != _WATING) {
			if (_currentAction == _IDLE)
				_wating++;
			else
				_wating = 0;
		}

		_animation.update();

		// set direction
		if (_currentAction != _AIRATTACK && _currentAction != _MAGICFIREBLIZZARD && !_dead) {
			if (_right)
				_facingRight = true;
			if (_left)
				_facingRight = false;
		}

	}

	public void addItem(Prizes i) {
		_items.add(i);
	}

	public void TakeItem(Prizes i) {
		i.use(this);
		_items.remove(i);
	}

	public void takeOrb(Prizes i) {
		i.setTook(_x, _y);
		_orbs.add(i);
	}

	public void stopAnyThing() {
		_left = _right = _firing = _blizzarding = _thundering = _attacking = _isInFight = _jumping = _falling = _airAttacking = _flinching = false;
	}

	public boolean canDoAnAction() {
		return !(_left || _right || _firing || _blizzarding || _thundering || _attacking || _rolling || _isInFight
				|| _jumping || _falling || _airAttacking);
	}

	public void drawRandomGlows(Graphics2D g) {
		Random rnd = new Random();

		for (int i = 0; i < rnd.nextInt(4); i++) {
			g.drawImage(_glows[0], (int) (_x + _xmap - _width / 2) + rnd.nextInt(_cwidth * 2) - _cwidth / 2,
					(int) (_y + _ymap - _height / 2) + rnd.nextInt(_cheight), null);
		}

		for (int i = 0; i < rnd.nextInt(4); i++) {
			g.drawImage(_glows[1], (int) (_x + _xmap - _width / 2) + rnd.nextInt(_cwidth * 2) - _cwidth / 2,
					(int) (_y + _ymap - _height / 2) + rnd.nextInt(_cheight), null);
		}
	}

	public void draw(Graphics2D g) {

		setMapPosition();

		// draw flying orbs behind player
		for (int i = 0; i < _orbs.size(); i++) {
			if (_orbs.get(i).getDir() == 1)
				_orbs.get(i).draw(g);
		}

		// draw player
		super.draw(g);

		// shiny effect
		if (!_orbs.isEmpty()) {
			Color c = new Color(84, 195, 54, 60);
			Color bColor = g.getColor();
			g.setColor(c);
			g.fillOval((int) (_x + _xmap - _width / 2), (int) (_y + _ymap - _height / 2 + _lowY), _width, _height);
			g.setColor(bColor);
			drawRandomGlows(g);
		}

		// draw flying orbs infront player
		for (int i = 0; i < _orbs.size(); i++) {
			if (_orbs.get(i).getDir() == -1)
				_orbs.get(i).draw(g);
		}

		// draw magic
		for (int i = 0; i < _itemsAndMagic.size(); i++) {
			_itemsAndMagic.get(i).draw(g);
		}

	}

}
