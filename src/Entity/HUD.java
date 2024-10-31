package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {

	private Character _character;

	private BufferedImage _face;
	private BufferedImage _bar[];
	private BufferedImage _health;
	private BufferedImage _mp;
	private int _x;
	private int _y;
	// +1 -1 pixal to draw
	private int _d;

	public HUD(Character c, int x, int y) {

		_character = c;
		_bar = new BufferedImage[2];
		_x = x;
		_y = y;
		try {
			// bar
			_bar[0] = ImageIO.read(getClass().getResourceAsStream("/HUD/bar1.gif"));

			// health
			_health = ImageIO.read(getClass().getResourceAsStream("/HUD/health1.gif"));

			// player
			if (c instanceof Player) {
				// face
				_face = ImageIO.read(getClass().getResourceAsStream("/HUD/face.gif"));
				// mp
				_mp = ImageIO.read(getClass().getResourceAsStream("/HUD/mp1.gif"));
				_bar[1] = ImageIO.read(getClass().getResourceAsStream("/HUD/barend.gif"));
				_d = 1;
			}

			// enemys bar
			if (c instanceof Enemy) {
				_bar[1] = ImageIO.read(getClass().getResourceAsStream("/HUD/enemybarend.gif"));
				_face = ImageIO.read(getClass().getResourceAsStream("/HUD/enemy.gif"));
				_d = -1;
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		int i;

		// the bars
		// health
		for (i = 0; i < _character._maxHealth; i++) {
			g.drawImage(_bar[0], _x + i * _d, _y, null);
		}
		g.drawImage(_bar[1], _x + i * _d, _y, null);// end bar

		// health
		if (_character._health!= 0)
		for (i = 0; i < _character._health + 1; i++) {
			g.drawImage(_health, _x + i * _d, _y + 1, null);
		}

		//player hub
		if (_character instanceof Player) {
			// mp
			for (i = 0; i < _character._maxMp / 100; i++) {
				g.drawImage(_bar[0], _x + i - 2, _y + 7, null);
			}
			g.drawImage(_bar[1], _x + i - 2, _y + 7, null);// end bar

			// mp
			if (_character._mp != 0)
				for (i = 0; i < _character._mp / 100 + 1; i++) {
					g.drawImage(_mp, _x + i - 2, _y + 8, null);
				}
			// face
			try {
				if (_character._flinching)
					_face = ImageIO.read(getClass().getResourceAsStream("/HUD/hit.gif"));
				else if (_character._health <= _character._maxHealth * 0.25)
					_face = ImageIO.read(getClass().getResourceAsStream("/HUD/low.gif"));
				else
					_face = ImageIO.read(getClass().getResourceAsStream("/HUD/face.gif"));

			} catch (Exception e) {
				e.printStackTrace();
			}
			g.drawImage(_face, _x - 38, _y - 17, null);
		}
		
		if(_character instanceof Enemy)
		{
			g.drawImage(_face, _x , _y , null);
		}
		
	}

	public Character getCharacter() {
		return _character;
	}
}
