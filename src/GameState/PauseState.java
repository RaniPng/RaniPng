package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Handlers.Keys;

import Main.GamePanel;

public class PauseState extends GameState {

	private BufferedImage pause;
	
	private boolean _layer;

	public PauseState(GameStateManager gsm) {

		super();
		this._gsm = gsm;
		try {
			pause=ImageIO.read(getClass().getResourceAsStream("/buttons/pause.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void init() {
	}

	public void update() {
		handleInput();	
	}

	public void draw(Graphics2D g) {

		
	    Color c = new Color(96, 96, 96, 100);
		g.setColor(c);
		if(_layer)
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		_layer=false;
		
		g.drawImage(pause, GamePanel.WIDTH/2-20,GamePanel.HEIGHT/2 , null);
		

	}

	public void handleInput(int k) {
		if (k== KeyEvent.VK_ESCAPE)
			_gsm.setPaused(false);
		
		 if(k == KeyEvent.VK_ENTER) {
			_gsm.setPaused(false);
			_gsm.setState(GameStateManager.MENUSTATE);
		}
		_gsm.update();
	}


	public void setLayer(boolean b) {
		_layer=b;
	}

	@Override
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) _gsm.setPaused(false);
		if(Keys.isPressed(Keys.ENTER)) {
			_gsm.setPaused(false);
			_gsm.setState(GameStateManager.MENUSTATE);
		}
		
	}

}
