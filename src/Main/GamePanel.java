package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import Handlers.Keys;

import GameState.GameStateManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	// dimensions
	public static int WIDTH = 320;
	public static int HEIGHT = 240;
	public static final int SCALE = 2;

	// game thread
	private Thread _thread;
	private boolean _running;
	private int _FPS = 60;
	private long _targetTime = 1000 / _FPS;

	// image
	private BufferedImage _image;
	private Graphics2D _g;

	// game state manager
	private GameStateManager _gsm;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (_thread == null) {
			_thread = new Thread(this);
			addKeyListener(this);
			_thread.start();
		}
	}

	private void init() {

		_image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		_g = (Graphics2D) _image.getGraphics();

		_running = true;

		_gsm = new GameStateManager();

	}

	public void run() {

		init();

		long start;
		long elapsed;
		long wait;

		// game loop
		while (_running) {

			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = _targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private void update() {
		_gsm.update();
		Keys.update();
	}

	private void draw() {
		_gsm.draw(_g);
	}

	private void drawToScreen() {
		HEIGHT = getSize().height / SCALE;
		WIDTH = getSize().width / SCALE;
		Graphics g2 = getGraphics();
		g2.drawImage(_image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key) {
	}

	public void keyPressed(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

}
