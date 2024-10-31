package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Player;
import Handlers.Keys;
import Main.GamePanel;
import Players.Sora;
import TileMap.Background;
import TileMap.TileMap;

public class Dead extends GameState {
	// choose
	private int _currentChoice;
	private String[] _options = { "Continue", "Title" };
	// player
	private TileMap _tileMap;
	private Sora p;
	private Background _bg;
	// buttons
	private Animation _heart;
	private BufferedImage _select[];
	private Animation _choose;

	public Dead(GameStateManager gsm) {
		_gsm = gsm;
		init();
	}

	@Override
	public void init() {
		_currentChoice = 0;
		_bg = new Background("/Backgrounds/dead/BG.gif", 0);
		_tileMap = new TileMap(8,107);
		_tileMap.loadTiles("/Tilesets/dead.gif");
		_tileMap.loadMap("/Maps/dead.xml");
		_tileMap.setPosition(0, 0);
		_tileMap.setTween(1);

		p = new Sora(_tileMap);

		p.deadScreen();
		p.setPosition(150, 160);

		try {

			_select = new BufferedImage[2];
			_heart = new Animation();
			_heart.setDelay(150);
			_choose = new Animation();
			_choose.setDelay(150);
			//select
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/buttons/dead/select.gif"));
			
			 for (int i = 0; i < _select.length; i++)
				 _select[i]=spritesheet.getSubimage(i * 136, 0, 136, 37);
			 
			 //heart
				 spritesheet = ImageIO.read(getClass().getResourceAsStream("/buttons/dead/hearts.gif"));
				 BufferedImage arr[] = new BufferedImage[8];
				 for (int i = 0; i < 8; i++)
					 arr[i]=spritesheet.getSubimage(i * 38, 0, 38, 46);
			 _heart.setFrames(arr, 0);
			 
			 //choose one
			 spritesheet = ImageIO.read(getClass().getResourceAsStream("/buttons/dead/choosed.gif")); 
			 arr = new BufferedImage[6];
			 for (int i = 0; i < 6; i++)
				 arr[i]=spritesheet.getSubimage(i * 29, 0, 29, 24);
		 _choose.setFrames(arr, 0);
			 

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void update() {
		// check keys
		handleInput();
		p.update();
		_choose.update();
		_heart.update();

	}

	private void select() {
		if (_currentChoice == 0) {
			_gsm.setState(Save.getLev());
		}
		if (_currentChoice == 1) {
			_gsm.setState(GameStateManager.MENUSTATE);
		}

	}

	@Override
	public void draw(Graphics2D g) {
		_bg.draw(g);
		p.draw(g);	

		 g.drawImage(_heart.getImage(),GamePanel.WIDTH/3+20,100-40,null);
		 
		 g.drawImage(_select[_currentChoice],GamePanel.WIDTH/4,100,null);
		 if (_currentChoice == 0) 
			 g.drawImage(_choose.getImage(),GamePanel.WIDTH-121,92,null);	
		 else
			 g.drawImage(_choose.getImage(),GamePanel.WIDTH-121,88+25,null);	

	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER)) {
			select();
		}
		if (Keys.isPressed(Keys.UP)) {
			_currentChoice--;
			if (_currentChoice == -1) {
				_currentChoice = _options.length - 1;
			}
		}
		if (Keys.isPressed(Keys.DOWN)) {
			_currentChoice++;
			if (_currentChoice == _options.length) {
				_currentChoice = 0;
			}
		}
	}

}
