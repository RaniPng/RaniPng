package Entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.Tile;
import TileMap.TileMap;

public class Enemy extends Character {
	protected boolean _followPlayer = false;
	protected int _lookRange;

	public Enemy(TileMap tm) {
		super(tm);
		_lookRange = 120;
	}

	public void goToPlayerDiraction(Player p) {

		boolean follow = false;
		if (_x - _lookRange < p._x && _x >= p._x) {
			_followPlayer = follow = true;
			if (_facingRight)
				turnAround();
		}
		else
		 if (_x + _lookRange > p._x && _x  <= p._x) {
			_followPlayer = follow = true;
			if (!_facingRight)
				turnAround();
		}

		if(!follow) {
	
			_followPlayer = false;
		}
		
	}
	

	protected boolean isGoinToFall() {
		Tile t;
		if (_falling)
			return false;
		int block;
		if (_facingRight)
			block = (int) (_x + _dx - _cwidth / 2) / _tileSize + 1;
		else
			block = (int) (_x + _dx + _cwidth / 2 - 1) / _tileSize - 1;

		int bottomTile = (int) (_y + _cheight / 2 - 1) / _tileSize + 1;
		// System.out.println(bottomTile);
		t=_tileMap.getTile(bottomTile, block);
		
		if(t!=null)
			return t.getType() == Tile.NORMAL;
		
		return false;
	}
	

	public void turnAround() {
		_left = !_left;
		_right = !_right;
		_facingRight = !_facingRight;
	}

	public void update() {
		if (!(ShouldDraw()))
			return;

	}

	public Explosion getHeart() {
		return new Explosion((int) _x, (int) _y, _tileMap);
	}

}
