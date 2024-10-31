package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Heal extends ItemAndSpell {

	public Heal(TileMap tm, boolean right, double x, double y) {
		super(tm, right);

		_width = 33;
		_height = 35;
		_cwidth = 33;
		_cheight = 35;
		
		setPosition(x, y);
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/heal.gif"));

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
	public ItemAndSpell shouldHit() {
		return null;
	}

	@Override
	public void setHit() {

	}

	@Override
	public void update() {
		if (_animation.hasPlayedOnce())
			_remove = true;
		_animation.update();

	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
