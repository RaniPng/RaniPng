package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.Enemies.Shadow;
import Main.GamePanel;
import Players.Riku;
import Players.Sora;
import TileMap.Background;
import TileMap.TileMap;

public class Level1 extends LevelState {

	private Background _water;
	private Background _land;

	public Level1(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
	

		_tileMap = new TileMap(8, 107);
	//	_tileMap = new TileMap(16, 214);
		_tileMap.loadTiles("/Tilesets/destiny islands.gif");
		_tileMap.loadMap("/Maps/destiny islands.xml");
		_tileMap.setPosition(0, 0);
		_tileMap.setTween(1);

		_water = new Background("/Backgrounds/destiny islands water.gif", 0.3);
		_land = new Background("/Backgrounds/destiny islands land.gif", 0.2);
		super.init();
		_player.setPosition(100, 30);

	
	}

	protected void populateEnemies() {

		_enemies = new ArrayList<Enemy>();

		Shadow s;
		Point[] points = new Point[] { new Point(300, 100), new Point(420, 200), new Point(1525, 40),
				new Point(1710, 80), new Point(1770, 170),new Point(1870, 170) };
		for (int i = 0; i < points.length; i++) {
			s = new Shadow(_tileMap);
			s.setPosition(points[i].x, points[i].y);
			_enemies.add(s);
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
		_water.setPosition(_tileMap.getx(), _tileMap.gety());
		_land.setPosition(_tileMap.getx(), _tileMap.gety());

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		_water.draw(g);
		_land.draw(g);
		super.draw(g);
	}

}
