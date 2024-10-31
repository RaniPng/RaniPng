package Handlers;

import java.awt.event.KeyEvent;

// this class contains a boolean array of current and previous key states
// for the keys that are used for this game.
// a key k is down when keyState[k] is true.
public class Keys {

	private static int _counter = 0;
	public static int UP = _counter++;
	public static int LEFT = _counter++;
	public static int DOWN = _counter++;
	public static int RIGHT = _counter++;
	public static int ZB = _counter++;
	public static int WB = _counter++;
	public static int EB = _counter++;
	public static int RB = _counter++;
	public static int FB = _counter++;
	public static int AB = _counter++;
	public static int GB = _counter++;
	public static int HB = _counter++;
	public static int ENTER = _counter++;
	public static int ESCAPE = _counter++;

	public static final int NUM_KEYS = _counter;

	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static void keySet(int i, boolean b) {
		// vk=hash code of the key after _
		if (i == KeyEvent.VK_UP)
			keyState[UP] = b;
		else if (i == KeyEvent.VK_LEFT)
			keyState[LEFT] = b;
		else if (i == KeyEvent.VK_DOWN)
			keyState[DOWN] = b;
		else if (i == KeyEvent.VK_RIGHT)
			keyState[RIGHT] = b;
		else if (i == KeyEvent.VK_Z)
			keyState[ZB] = b;
		else if (i == KeyEvent.VK_W)
			keyState[WB] = b;
		else if (i == KeyEvent.VK_E)
			keyState[EB] = b;
		else if (i == KeyEvent.VK_H)
			keyState[GB] = b;
		else if (i == KeyEvent.VK_R)
			keyState[RB] = b;
		else if (i == KeyEvent.VK_G)
			keyState[HB] = b;
		else if (i == KeyEvent.VK_F)
			keyState[FB] = b;
		else if (i == KeyEvent.VK_A)
			keyState[AB] = b;
		else if (i == KeyEvent.VK_ENTER)
			keyState[ENTER] = b;
		else if (i == KeyEvent.VK_ESCAPE)
			keyState[ESCAPE] = b;
	}

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}

	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}

	public static boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i])
				return true;
		}
		return false;
	}
}
