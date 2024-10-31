package Prizes;

import javax.imageio.ImageIO;

import Entity.Player;
import TileMap.TileMap;

public class MP extends Prizes {
	
	public MP(TileMap tm,double x1,double y1) {
		super(tm, x1, y1);	
		_width=12;
		_height=16;
			try {
				_item=ImageIO.read(getClass().getResourceAsStream("/Items/Mp.gif"));
				_item=_item.getSubimage(12*(_size-1), 0, 13, 16);						
			} catch (Exception e) {
				e.printStackTrace();
			}			
	}
	
	@Override
	public void take(Player p) {
		p.takeOrb(this);
	}

	@Override
	public void use(Player p) {
		p.addMp(_impact);		
	}

}
