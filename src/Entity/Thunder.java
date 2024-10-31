package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Thunder extends ItemAndSpell {

	protected Bolt _bolt;

	public Thunder(TileMap tm, boolean right, Double x, Double y) {
		super(tm, right);

		_width = 42;
		_height = 42;
		_cwidth = 14;
		_cheight = 14;
		_type = Character.THUNDER;

		setPosition(x, y);
		if (right)
			x += 45;
		else
			x -= 45;
		y += 45;
		_bolt = new Bolt(tm, right, x, y);
		_bolt._facingRight = right;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/thunder.gif"));

			_sprites = new BufferedImage[8];
			for (int i = 0; i < _sprites.length; i++) {
				_sprites[i] = spritesheet.getSubimage(i * _width, 0, _width, _height);
			}
			this.setSpirte(70);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setHit() {

	}

	public ItemAndSpell shouldHit() {
		if (_animation.hasPlayedOnce())
			return _bolt;
		else
			return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		_animation.update();
		if (_animation.hasPlayedOnce())
			_bolt.update();

		if (_bolt._animation.hasPlayedOnce()) {
			_remove = true;
		}

	}

	@Override
	public void draw(Graphics2D g) {

		if (_animation.hasPlayedOnce())
			_bolt.draw(g);
		else
			super.draw(g);
	}

}
