package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

public class Soldier extends Enemy {

	private BufferedImage[] _sprites;
	
	
	public Soldier(TileMap tm) {
		super(tm);
		
		_moveSpeed = 0.4;
		_maxSpeed = 1.5;
		_fallSpeed = 0.6;
		_maxFallSpeed = 11.0;

		_width = 45;
		_height = 59;
		_cwidth = 45;
		_cheight = 59;
		_type = PHYSICAL;

		_health = _maxHealth = 67;
		_str = 10;
		_def = 2;
		_counterAttack = 1;	

		// load sprites
		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/solider.gif"));

			_sprites = new BufferedImage[9];
			for (int i = 0; i < _sprites.length; i++) {
				_sprites[i] = spritesheet.getSubimage(i * _width, 0, _width, _height);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		_animation = new Animation();
		_animation.setFrames(_sprites, 0);
		_animation.setDelay(160);

		_right = true;
		_facingRight = true;
	
	}
	
	private void getNextPosition() {
		if (_dead) {
			_dx = 0;
			_dy = 0;
			return;
		}

		if(_followPlayer)
			_maxSpeed = 1.3;
		else
			_maxSpeed = 1.0;
		
		if (!_followPlayer && isGoinToFall()) {
			turnAround();
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
		}

		// falling
		if (_falling) {
			_dy += _fallSpeed;
		}

	}
	

	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(_xtemp, _ytemp);
		// check flinching
		if (_flinching) {
			long elapsed = (System.nanoTime() - _flinchTimer) / 1000000;
			if (elapsed > 300) {
				_flinching = false;
			}
		}

		// if it hits a wall, go other direction
		if (!_followPlayer && _dx == 0) {
				turnAround();
		}
		// update animation
		_animation.update();

	}

	public void draw(Graphics2D g) {

		// if(notOnScreen()) return;
		super.draw(g);

	}


	
}
