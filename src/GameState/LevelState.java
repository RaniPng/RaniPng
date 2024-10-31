package GameState;

import Main.GamePanel;
import Players.Riku;
import Players.Sora;
import Prizes.*;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Handlers.Img;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Handlers.Keys;

public abstract class LevelState extends GameState {

	protected TileMap _tileMap;
	protected Background _bg;

	protected Sora _player;
	protected ArrayList<Enemy> _enemies;
	protected ArrayList<Explosion> _explosions;

	protected HUD _hud;
	protected HUD _enemyHud;

	protected ArrayList<Prizes> _pickUps;

	// events
	public Img _betweenScreen;
	protected boolean _blockInput = false;
	protected int _eventCount = 0;
	protected boolean _eventStart;
	protected ArrayList<Rectangle> _tb;
	protected boolean _eventFinish;
	protected boolean _eventDead;

	public LevelState(GameStateManager gsm) {
		this._gsm = gsm;
		init();
	}

	public void init() {

		_player = new Sora(_tileMap);
		populateEnemies();
		_explosions = new ArrayList<Explosion>();
		_pickUps = new ArrayList<Prizes>();
		_hud = new HUD(_player, 65, 30);
		// start event
		_eventStart = true;
		_tb = new ArrayList<Rectangle>();
		eventStart();
	}

	protected abstract void populateEnemies();

	public void update() {

		// check keys
		handleInput();

		// update player
		_player.update();

		if (_player.isDead()) {
			_eventDead = _blockInput = true;
			eventDead();
		}
		_tileMap.setPosition(GamePanel.WIDTH / 2 - _player.getx(), GamePanel.HEIGHT / 2 - _player.gety());

		// set background
		if (_bg != null)
			_bg.setPosition(_tileMap.getx(), _tileMap.gety());

		if (_player.isDead())
			return;

		if (_eventStart)
			eventStart();
		if (_eventFinish)
			eventFinish();

		// attack enemies
		_player.checkAttack(_enemies);

		// update all enemies
		for (int i = 0; i < _enemies.size(); i++) {
			Enemy e = _enemies.get(i);
			e.goToPlayerDiraction(_player);
			e.update();
			if (e.isFlinching())
				_enemyHud = new HUD(_enemies.get(i), GamePanel.WIDTH - 30, 10);
			if (e.isDead()) {
				_explosions.add(e.getHeart());
				_enemies.remove(i);
			}
		}

		// update explosions
		for (int i = 0; i < _explosions.size(); i++) {
			Explosion heart = _explosions.get(i);
			heart.update();
			if (_explosions.get(i).shouldRemove()) {
				for (int j = 0; j < heart.getDrops().size(); j++)
					_pickUps.add(heart.getDrops().get(j));
				_explosions.remove(i);
				i--;
			}
		}
		for (int i = 0; i < _pickUps.size(); i++) {
			if (_pickUps.get(i).isLowerThanMap())
				_pickUps.remove(i);
			else if (_pickUps.get(i).intersects(_player)) {
				_pickUps.get(i).take(_player);
				_pickUps.remove(i);
			} else {
				_pickUps.get(i).update();
			}
		}
		if (_enemyHud != null && _enemyHud.getCharacter().isDead())
			_enemyHud = null;

	}

	public void draw(Graphics2D g) {

		// draw bg
		if (_bg != null)
			_bg.draw(g);

		// draw tilemap
		_tileMap.draw(g);

		// draw player
		_player.draw(g);

		// draw enemies
		for (int i = 0; i < _enemies.size(); i++) {
			_enemies.get(i).draw(g);
		}

		// draw explosions
		for (int i = 0; i < _explosions.size(); i++) {
			_explosions.get(i).setMapPosition((int) _tileMap.getx(), (int) _tileMap.gety());
			_explosions.get(i).draw(g);
		}
		// draw items
		for (int i = 0; i < _pickUps.size(); i++) {
			_pickUps.get(i).draw(g);
		}

		// draw hud
		_hud.draw(g);
		if (_enemyHud != null)
			_enemyHud.draw(g);

		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for (int i = 0; i < _tb.size(); i++) {
			g.fill(_tb.get(i));
		}
	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			_gsm.setPaused(true);
		if (_blockInput || _player.isDead())
			return;

		// doesnt check weather they pressed or not so the player can stop the jump/run
		// action in the middle of the action
		// acting while the key is pressed
		_player.setJumping(Keys.keyState[Keys.UP]);
		_player.setLeft(Keys.keyState[Keys.LEFT]);
		_player.setDown(Keys.keyState[Keys.DOWN]);
		_player.setRight(Keys.keyState[Keys.RIGHT]);

		// check jumping of/on edge
		if (_player.isHolding()) {
			if ((Keys.isPressed(Keys.LEFT) && !_player.isFacingRight()
					|| Keys.isPressed(Keys.RIGHT) && _player.isFacingRight()) || Keys.isPressed(Keys.UP))
				_player.rollJumping();
			else if (Keys.isPressed(Keys.DOWN) || (Keys.isPressed(Keys.LEFT)) || (Keys.isPressed(Keys.RIGHT)))
				_player._setHolding(false);
		}

		// check weather they pressed or not so because it should be a full action
		// acting after the key is pressed until the action is finished
		if (Keys.isPressed(Keys.ZB))
			_player.setAttacking();

		if (Keys.isPressed(Keys.EB))
			_player.setRolling();

		if (Keys.isPressed(Keys.HB))
			_player.setThundering();
		if (Keys.isPressed(Keys.FB))
			_player.setFiring();

		if (Keys.isPressed(Keys.GB))
			_player.setBlizzarding();

		if (Keys.isPressed(Keys.AB))
			_player.setHealing();
		if (Keys.isPressed(Keys.RB))
			_player.setTaking();
	}

	///////////////////////////////////////////////////////
	//////////////////// EVENTS
	///////////////////////////////////////////////////////

	// reset level
	private void reset() {
		_player.reset();
		_player.setPosition(300, 161);
		populateEnemies();
		_blockInput = true;
		_eventCount = 0;
		_eventStart = true;
		eventStart();
	}

	// level started
	private void eventStart() {
		_eventCount++;
		if (_eventCount == 1) {
			_tb.clear();
			_tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			_tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			_tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			_tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if (_eventCount > 1 && _eventCount < 60) {
			_tb.get(0).height -= 4;
			_tb.get(1).width -= 6;
			_tb.get(2).y += 4;
			_tb.get(3).x += 6;
		}
		/*
		 * if (_eventCount == 30) _title.begin();
		 */

		if (_eventCount == 60) {
			_eventStart = _blockInput = false;
			_eventCount = 0;
			_tb.clear();
		}
	}

	// finished level
	private void eventFinish() {
		_eventCount++;

		if (_eventCount == 120) {
			_tb.clear();
			_tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (_eventCount > 120) {
			_tb.get(0).x -= 6;
			_tb.get(0).y -= 4;
			_tb.get(0).width += 12;
			_tb.get(0).height += 8;

		}

		if (_eventCount == 180) {
			/*
			 * PlayerSave.setHealth(player.getHealth());
			 * PlayerSave.setLives(player.getLives()); PlayerSave.setTime(player.getTime());
			 */
			_eventFinish = false;

		}

	}

	// player has died
	private void eventDead() {
		_eventCount++;

		if (_eventCount == 60) {
			_tb.clear();
			_tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (_eventCount > 159) {
			_tb.get(0).x -= 6;
			_tb.get(0).y -= 4;
			_tb.get(0).width += 12;
			_tb.get(0).height += 8;
		}

		if (_eventCount > 190) {
			_gsm.setState(GameStateManager.DEAD);
		}
	}

}
