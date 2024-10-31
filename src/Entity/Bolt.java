package Entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Bolt extends ItemAndSpell {

	public Bolt(TileMap tm, boolean right, Double x, Double y) {
		super(tm, right);
		_facingRight = right;
		_width = 25;
		_height = 58;
		_cwidth = 65;
		_cheight = 58;
		_x = x;
		_y = y;
		_dy += 0.15;
		_type = Character.THUNDER;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/thunderbolt.gif"));

			_sprites = new BufferedImage[4];
			for (int i = 0; i < _sprites.length; i++) {
				_sprites[i] = spritesheet.getSubimage(i * _width, 0, _width, _height);
			}
			setSpirte(110);

		} catch (Exception e) {
			e.printStackTrace();
		}
		toLowestTile();

	}

	public void toLowestTile() {
		checkTileMapCollision();
		while (_falling) {
			setPosition(_xtemp, _ytemp);

			checkTileMapCollision();
		}
	}

	@Override
	public void setHit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		_animation.update();
	}

	@Override
	public ItemAndSpell shouldHit() {
		return this;
	}

}
