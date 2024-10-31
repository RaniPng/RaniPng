package Prizes;

import javax.imageio.ImageIO;

import Entity.Player;
import TileMap.TileMap;

public class Potion extends Prizes{
	
	public Potion(TileMap tm,double x1,double y1) {
		super(tm, x1, y1);	
		_height=_width=17;
			try {
				_item=ImageIO.read(getClass().getResourceAsStream("/Items/item.gif"));
				_item=_item.getSubimage(0, 0, _width, _height);						
			} catch (Exception e) {
				e.printStackTrace();
			}			
	}
	
	@Override
	public void take(Player p) {
		p.addItem(this);	
	}

	public void use(Player p) {
		p.addHealth(_impact);	
	}
	
	

}

