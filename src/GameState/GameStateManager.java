package GameState;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.GamePanel;
import TileMap.Background;

public class GameStateManager {

	private GameState[] _gameStates;
	private int _currentState;
	public Background _loadScreen;
	

	private PauseState _pauseState;
	private boolean _paused;

	public static int _statCounter=0;
	public static final int MENUSTATE = _statCounter++;
	public static final int LEVEL1 = _statCounter++;
	public static final int LEVEL2 = _statCounter++;
	public static final int DEAD = _statCounter++;
	public static final int NUMGAMESTATES = _statCounter;

	public GameStateManager() {

		_gameStates = new GameState[NUMGAMESTATES];

		_pauseState = new PauseState(this);
		_paused = false;

		_loadScreen = new Background("/Backgrounds/load.gif", 0);
		

		_currentState = MENUSTATE;
		loadState(_currentState);

	}

	private void loadState(int state) {
		if (state == MENUSTATE)
			_gameStates[state] = new MenuState(this);
		else if (state == LEVEL1)
			_gameStates[state] = new Level1(this);
		else if (state == LEVEL2)
			_gameStates[state] = new Level2(this);
		else if (state == DEAD)
			_gameStates[state] = new Dead(this);

	}

	private void unloadState(int state) {
		_gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(_currentState);
		_currentState = state;
		loadState(_currentState);
	}

	public void setPaused(boolean b) {
		_pauseState.setLayer(b);
		_paused = b;
	}

	public void update() {
		if (_paused) {
			_pauseState.update();
			return;
		}
		if (_gameStates[_currentState] != null)
			_gameStates[_currentState].update();
	}

	public void draw(java.awt.Graphics2D g) {
		if (_paused) {
			_pauseState.draw(g);
			return;
		}
		if (_gameStates[_currentState] != null)
			_gameStates[_currentState].draw(g);

		else {
			_loadScreen.draw(g);
		}
	}


	public boolean getpause() {
		// TODO Auto-generated method stub
		return _paused;
	}

}
