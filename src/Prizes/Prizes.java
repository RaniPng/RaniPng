package Prizes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Entity.MapObject;
import Entity.Player;
import TileMap.TileMap;

public abstract class Prizes extends MapObject {

	protected double _impact;
	protected int _size;
	protected BufferedImage _item;

	public final int SMALL = 1;
	public final int BIG = 2;
	protected boolean _took = false;
	protected boolean _shuoldRemove = false;
	private int _flyingCount = 0;
	private int _dir = 1;

	public Prizes(TileMap tm, double x1, double y1) {
		super(tm);

		_cwidth = 25;
		_cheight = 16;
		_x = x1;
		_y = y1;

		Random rnd = new Random();
		_size = rnd.nextInt(2) + 1;
		_impact = _size * 0.06;

		_facingRight = false;
		_width = 30;
		_height = 30;
		_cwidth = 14;
		_cheight = 14;
		checkTileMapCollision();
		update();
	}

	public double get_impact() {
		return _impact;
	}

	public int get_size() {
		return _size;
	}

	public abstract void take(Player p);

	public abstract void use(Player p);

	public void draw(Graphics2D g) {
		setMapPosition();
		if (!(ShouldDraw()))
			return;
		
		g.drawImage(_item, (int) (_x + _xmap - _cwidth / 2), (int) (_y + _ymap - _cheight / 2), _width, _height, null);
	}

	public void setTook(double x, double y) {
		_xtemp = x - _width / 2;
		_ytemp = y - _height;
		_took = true;
		_falling = false;
	}
	
	public boolean isLowerThanMap() {
		return _y>_tileSize*_tileMap.getNumRows();
	}

	public void flyingAroundPlayer(double x, double y) {
		_flyingCount++;
		if (_flyingCount % 10 == 0) {
			_dir *= -1;
			if (_width > 0)
				_width--;
			if (_height > 0)
				_height--;	
		}

		_xtemp += x + 4.3 * _dir;
		_ytemp += y + 0.5;

		if (_flyingCount == 90)
			_shuoldRemove = true;
		update();
	}

	public boolean shuoldRemove() {
		return _shuoldRemove;
	}

	public int getDir() {
		return _dir;
	}

	public void update() {
		if (_falling) {
			_dy = 0.4;
			checkTileMapCollision();
		}

		setPosition(_xtemp, _ytemp);
	}

}
