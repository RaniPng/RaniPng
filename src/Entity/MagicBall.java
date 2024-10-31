package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public abstract class MagicBall extends ItemAndSpell {

	protected boolean _hit;
	protected boolean _pre=true;
	protected BufferedImage[] _ball;
	protected BufferedImage[] _hitSprites;
	
	public MagicBall(TileMap tm, boolean right,String s,int sp,int b,int hs) {

		super(tm,right);

		
		_moveSpeed = 0.6;
		if (_facingRight)
			_dx = _moveSpeed;
		else
			_dx = -_moveSpeed;

		_width = 58;
		_height = 53;
		_cwidth = 14;
		_cheight = 23;
		// load sprites
		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(s));

			_sprites = new BufferedImage[sp];
			for (int i = 0; i < _sprites.length; i++) {
				_sprites[i] = spritesheet.getSubimage(i * _width, 0, _width, _height);
			}
			
			_ball = new BufferedImage[b];
			for (int i = 0; i < _ball.length; i++) {
				_ball[i] = spritesheet.getSubimage(i * _width, _height, _width, _height);
			}
			

			_hitSprites = new BufferedImage[hs];
			for (int i = 0; i < _hitSprites.length; i++) {
				_hitSprites[i] = spritesheet.getSubimage(i * _width, _height*2, _width, _height);
			}
			setSpirte(70);


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
				
	}

	public void setHit() {
		if (_hit)
			return;
		_hit = true;
		_animation.setFrames(_hitSprites, 0);
		_animation.setDelay(80);
		_dx = 0;
	}
	
	public void setBall() {
		_moveSpeed = 3.8;
		if (_facingRight)
			_dx = _moveSpeed;
		else
			_dx = -_moveSpeed;
		_animation = new Animation();
		_animation.setFrames(_ball, 5);
		_animation.setDelay(70);
	}
	public ItemAndSpell shouldHit()
	{
		return this;
	}



	public void update() {
		checkTileMapCollision();
		setPosition(_xtemp, _ytemp);
		if(_pre && _animation.hasPlayedOnce()) {
			_pre=false;
			setBall();
		}
		if (_dx == 0 && !_hit) {
			setHit();
		}
			
		_animation.update();
		if (_hit && _animation.hasPlayedOnce()) {
			_remove = true;
		}
	}

	


}
