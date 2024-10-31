package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Handlers.Keys;
import Main.GamePanel;

public class MenuState extends GameState {

	private Background _bg;
	private BufferedImage _button;
	private BufferedImage _selcted;
	private Animation _glow;
	private BufferedImage _recB;
	

	private int _currentChoice = 0;
	private String[] _options = { "Start", "Help", "Quit" };

	private Color _titleColor;
	private Font _titleFont;

	private Font _font;

	public MenuState(GameStateManager gsm) {

		this._gsm = gsm;
		Save.setCheckP(0);
		Save.setLev(GameStateManager.LEVEL1);

		try {

			_bg = new Background("/Backgrounds/menu.gif", 1);
			_button= ImageIO.read(getClass().getResourceAsStream("/buttons/Title/btn.png"));
			_selcted= ImageIO.read(getClass().getResourceAsStream("/buttons/Title/Selected.png"));
			
			_recB= ImageIO.read(getClass().getResourceAsStream("/buttons/Title/recB.png"));
			
			_titleColor = new Color(128, 0, 0);
			_titleFont = new Font("Century Gothic", Font.PLAIN, 28);

			_font = new Font("Arial", Font.PLAIN, 12);
			
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/buttons/Title/glow.png"));
			BufferedImage glow[]=new BufferedImage[8];
			for (int i = 0; i < glow.length; i++) {
				glow[i]= spritesheet.getSubimage(i * 50, 0, 50, 50);
			}
			_glow=new Animation();
			_glow.setDelay(150);
			_glow.setFrames(glow);
			_glow.set_revloop(true);
		
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init() {
	}

	public void update() {
		// check keys
		_glow.update();
		handleInput();
	}

	public void draw(Graphics2D g) {

		// draw bg
		_bg.draw(g);

		// draw title
		g.setFont(_titleFont);

		// draw menu options
		g.setFont(_font);
		g.setColor(Color.BLACK);
		int x=10,y=GamePanel.HEIGHT/2;
		for (int i = 0; i < _options.length; i++) {
			g.drawImage(_recB, x, y+30*i,20,20, null);
			
			if (i == _currentChoice) {
				g.drawImage(_selcted, x, y+30*i,GamePanel.WIDTH-x,20, null);
				g.drawImage(_glow.getImage(), x, y+30*i,20,20, null);				
			} else {
				g.drawImage(_button, x, y+30*i,GamePanel.WIDTH-x,20, null);
			}
			g.drawString(_options[i], x+30, y+15+30*i);
		}

		 
	}

	private void select() {
		//exit
		if (_currentChoice == 0) {
			_gsm.setState(Save.getLev());
		}
		if (_currentChoice == 1) {
			// help
		}
		//exit
		if (_currentChoice == 2) {
			System.exit(0);
		}
	}

	public void keyPressed(int k) {

	}

	public void keyReleased(int k) {
	}
	

	@Override
	public void handleInput() {
		Boolean glowreset=false;
		
		if(Keys.isPressed(Keys.ENTER)) select();
		
		if(Keys.isPressed(Keys.UP)) {
			if(_currentChoice > 0) 
				_currentChoice--;
			
			else
				_currentChoice=_options.length-1;
			glowreset=true;
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(_currentChoice < _options.length - 1) 
				_currentChoice++;	
			else
				_currentChoice=0;
			glowreset=true;
		}
		
		if(glowreset)
			_glow.init();		
	}

}
