package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import Entity.Enemy;
import Entity.Player;
import Entity.Enemies.Shadow;
import Entity.Enemies.Soldier;
import Players.Riku;
import Players.Sora;
import TileMap.Background;
import TileMap.TileMap;

public class Level2 extends LevelState {

	public Level2(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		_bg = new Background("/Backgrounds/traverse town.gif", 0.3);

		_tileMap = new TileMap(10, 107);
		_tileMap.loadTiles("/Tilesets/traverse town.gif");

		_tileMap.loadMap("/Maps/traverse town.xml");
		_tileMap.setPosition(0, 0);
		_tileMap.setTween(1);

		_player.setPosition(100, 100);

		super.init();
	}

	protected void populateEnemies() {

		_enemies = new ArrayList<Enemy>();

		Soldier s;
		Point[] points = new Point[] { new Point(300, 100), new Point(420, 200), new Point(1525, 40),
				new Point(1710, 80), new Point(1770, 170), new Point(1870, 170) };
		for (int i = 0; i < points.length; i++) {
			s = new Soldier(_tileMap);
			s.setPosition(points[i].x, points[i].y);
			_enemies.add(s);
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub

		super.draw(g);
	}

}
