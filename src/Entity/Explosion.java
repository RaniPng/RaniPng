package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Prizes.Ether;
import Prizes.HP;
import Prizes.MP;
import Prizes.Potion;
import Prizes.Prizes;
import TileMap.TileMap;

public class Explosion {

	private int _x;
	private int _y;
	private int _xmap;
	private int _ymap;

	private int _width;
	private int _height;

	private Animation _animation;
	private BufferedImage[] _sprites;
	private ArrayList<Prizes> _drops;

	private boolean _remove;

	public Explosion(int x, int y, TileMap tm) {

		this._x = x;
		this._y = y;

		_width = 68;
		_height = 78;

		_drops = new ArrayList<Prizes>();
		addItems(tm);

		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/heart.gif"));

			_sprites = new BufferedImage[7];
			for (int i = 0; i < _sprites.length; i++) {
				_sprites[i] = spritesheet.getSubimage(i * _width, 0, _width, _height);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		_animation = new Animation();
		_animation.setFrames(_sprites, 0);
		_animation.setDelay(70);

	}

	public void update() {
		_animation.update();
		if (_animation.hasPlayedOnce()) {
			_remove = true;
		}
	}

	public void addItems(TileMap tm) {
		Random rnd = new Random();
		int hp = rnd.nextInt(4);
		int mp = rnd.nextInt(4);
		int potion= rnd.nextInt(2);
		int eather= rnd.nextInt(2);
		
		for (int i = 0; i <hp; i++)
			_drops.add(new HP(tm, _x+(rnd.nextInt(10))*8, _y));
		for (int i = 0; i < mp; i++)
			_drops.add(new MP(tm, _x +(rnd.nextInt(10))*8, _y));
		for (int i = 0; i < potion; i++)/////////////////
			_drops.add(new Potion(tm, _x +(rnd.nextInt(10))*8, _y));		
		for (int i = 0; i < eather; i++)/////////////////
			_drops.add(new Ether(tm, _x +(rnd.nextInt(10))*8, _y));	
	}

	public ArrayList<Prizes> getDrops() {
		return _drops;
	}

	public boolean shouldRemove() {
		return _remove;
	}

	public void setMapPosition(int x, int y) {
		_xmap = x;
		_ymap = y;
	}

	public void draw(Graphics2D g) {
		g.drawImage(_animation.getImage(), _x + _xmap - _width / 2, _y + _ymap - _height / 2, null);
	}

}
