package Entity.Enemies;

import Entity.*;
import Entity.Character;
import TileMap.Tile;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Shadow extends Enemy {

	private BufferedImage[] _sprites;

	public Shadow(TileMap tm) {

		super(tm);

		_moveSpeed = 0.3;
		_maxSpeed = 0.6;
		_fallSpeed = 0.2;
		_maxFallSpeed = 10.0;

		_width = 39;
		_height = 27;
		_cwidth = 20;
		_cheight = 20;
		_type = PHYSICAL;

		_health = _maxHealth = 50;
		_str = 7;
		_def = 2;
		_counterAttack = 1;

		// load sprites
		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Shdow.gif"));

			_sprites = new BufferedImage[3];
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
		_maxSpeed = 0.6;
		else
			_maxSpeed = 0.3;
		
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
		super.update();
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
