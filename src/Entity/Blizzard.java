package Entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Blizzard extends MagicBall{

	
	public Blizzard (TileMap tm, boolean right) {

		super(tm,right,"/Sprites/Player/blizzard.gif",3,7,4);
		
		_type=Character.BLIZZARD;

	}
	
}
