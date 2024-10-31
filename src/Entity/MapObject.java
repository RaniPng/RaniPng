package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.sun.swing.internal.plaf.metal.resources.metal;

public abstract class MapObject {

	// tile stuff
	protected TileMap _tileMap;
	protected int _tileSize;
	protected double _xmap;
	protected double _ymap;

	// position and vector
	protected double _x;
	protected double _y;
	protected double _dx;
	protected double _dy;
	protected double _lowY = 0;// a var that contains the lowest y point in the current tile where an object
								// can be stand in collution(if there is transfersy in the tile image)
	protected boolean _aboveTile = true;
	protected Tile _m;

	// dimensions
	protected int _width;
	protected int _height;

	// collision box
	protected int _cwidth;
	protected int _cheight;

	// collision
	protected int _currRow;
	protected int _currCol;
	protected double _xdest;
	protected double _ydest;
	protected double _xtemp;
	protected double _ytemp;
	protected boolean _topLeft;
	protected boolean _topRight;
	protected boolean _bottomLeft;
	protected boolean _bottomRight;

	// animation
	protected Animation _animation;
	protected int _currentAction;
	protected int _previousAction;
	protected boolean _facingRight;

	// movement
	protected boolean _left;
	protected boolean _right;
	protected boolean _up;
	protected boolean _down;
	protected boolean _jumping;
	protected boolean _falling;
	protected boolean _landing;

	// movement attributes
	protected double _moveSpeed;
	protected double _maxSpeed;
	protected double _stopSpeed;
	protected double _fallSpeed;
	protected double _maxFallSpeed;
	protected double _jumpStart;
	protected double _stopJumpSpeed;

	// constructor
	public MapObject(TileMap tm) {
		_tileMap = tm;
		_tileSize = tm.getTileSize();
		_animation = new Animation();
	}

	public boolean intersects(MapObject o) {
		if (o == null)
			return false;
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}

	public boolean intersects(Rectangle r) {
		return getRectangle().intersects(r);
	}

	public boolean contains(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.contains(r2);
	}

	public boolean contains(Rectangle r) {
		return getRectangle().contains(r);
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) _x - _cwidth / 2, (int) _y - _cheight / 2, _cwidth, _cheight);
	}

	public void calculateCorners(double x, double y) {

		int leftTile = (int) (x - _cwidth / 2) / _tileSize;
		int rightTile = (int) (x + _cwidth / 2 - 1) / _tileSize;
		int topTile = (int) (y - _cheight / 2) / _tileSize;
		int bottomTile = (int) (y + _cheight / 2 - 1) / _tileSize;

		int tl = _tileMap.getType(topTile, leftTile);
		int tr = _tileMap.getType(topTile, rightTile);
		int bl = _tileMap.getType(bottomTile, leftTile);
		int br = _tileMap.getType(bottomTile, rightTile);

		// checking character/player collision
		if (this instanceof Character) {
			if (bl == Tile.DEAD || br == Tile.DEAD)
				((Character) this).setDead(true);
		}

		_topLeft = tl == Tile.BLOCKED;
		_topRight = tr == Tile.BLOCKED;
		_bottomLeft = bl == Tile.BLOCKED;
		_bottomRight = br == Tile.BLOCKED;

	}

	public void checkTileMapCollision() {

		_currCol = (int) _x / _tileSize;
		_currRow = (int) _y / _tileSize;
		
		double prevLow=_lowY;
		int curRow=_currRow;
		if(_aboveTile)
			curRow++;
		
		_xdest = _x + _dx;
		_ydest = _y + _dy;

		_xtemp = _x;
		_ytemp = _y;
		
		

		calculateCorners(_x, _ydest);
		if (_dy < 0) {
			if (_topLeft || _topRight) {
				_dy = 0;
				_ytemp = _currRow * _tileSize + _cheight / 2;
			} else {
				_ytemp += _dy;
			}
		}
		if (_dy > 1) {
			if (_bottomLeft || _bottomRight) {
				_dy = 0;
				_falling = false;
				_landing = true;
				_ytemp = (_currRow + 1) * _tileSize - _cheight / 2;
			} else {
				_ytemp += _dy;
			}
		}

		calculateCorners(_xdest, _y);
		if (_dx < 0) {
			if (_topLeft || _bottomLeft) {
				_dx = 0;
				_xtemp = _currCol * _tileSize + _cwidth / 2;
			} else {
				_xtemp += _dx;

			}
		}
		if (_dx > 0) {
			if (_topRight || _bottomRight) {
				_dx = 0;
				_xtemp = (_currCol + 1) * _tileSize - _cwidth / 2;
			} else {
				_xtemp += _dx;
			}
		}

		if (!_falling) {
			calculateCorners(_x, _ydest + 1);
			if (!_bottomLeft && !_bottomRight) {
				_falling = true;
			}
		}

	/*	if (!_jumping && !_falling && !_landing && _tileMap.getType(_currRow + 1, _currCol) != Tile.NORMAL)
			if (_facingRight)
				_lowY = _tileMap.getTile(_currRow + 1, _currCol)
						.lowestCanStand((int) ((_x) - (int) (_x) / _tileSize * _tileSize), (int) (_cwidth / 2));

			else
				_lowY = _tileMap.getTile(_currRow + 1, _currCol)
						.lowestCanStand((int) ((_x) - (int) (_x) / _tileSize * _tileSize), (int) (_cwidth / 2));
		else {
			_lowY = 0;
		}*/
	}

	public int getx() {
		return (int) _x;
	}

	public int gety() {
		return (int) _y;
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getCWidth() {
		return _cwidth;
	}

	public int getCHeight() {
		return _cheight;
	}

	public void setPosition(double x, double y) {
		this._x = x;
		this._y = y;
	}

	public void setVector(double dx, double dy) {
		this._dx = dx;
		this._dy = dy;
	}

	public void setMapPosition() {
		_xmap = _tileMap.getx();
		_ymap = _tileMap.gety();
	}

	public void setLeft(boolean b) {
		_left = b;
	}

	public void setRight(boolean b) {
		_right = b;
	}

	public void setUp(boolean b) {
		_up = b;
	}

	public void setDown(boolean b) {
		_down = b;
	}

	public void setJumping(boolean b) {
		_jumping = b;
	}

	public Animation getAnimation() {
		return _animation;
	}

	public boolean notOnScreen() {
		return _x + _xmap + _width < 0 || _x + _xmap - _width > GamePanel.WIDTH || _y + _ymap + _height < 0
				|| _y + _ymap - _height > GamePanel.HEIGHT;
	}

	public boolean isFacingRight() {
		return _facingRight;
	}

	public boolean ShouldDraw() {
		// make sure to draw only whats on screen + - 2 is for safe /half or less sprite that needs to be drawn
		return ((_currCol >= _tileMap.get_colOffset() - 2
				&& _currCol <= _tileMap.get_colOffset() + _tileMap.get_numColsToDraw() + 2
				&& _currRow >= _tileMap.get_rowOffset() - 2
				&& _currRow <= _tileMap.get_rowOffset() + _tileMap.get_numRowsToDraw() + 2));
	}

	public void draw(Graphics2D g) {
		if (!(ShouldDraw()))
			return;

		if (_facingRight) {
			g.drawImage(_animation.getImage(), (int) (_x + _xmap - _width / 2 + _width),
					(int) (_y + _ymap - _height / 2), -_width, (int) (_height), null);

		} else {
			g.drawImage(_animation.getImage(), (int) (_x + _xmap - _width / 2),
					(int) (_y + _ymap - _height / 2 ), null);
		}
		if (_m != null)
			g.drawImage(_m.getImage(), 0, 0, null);
	}

}
