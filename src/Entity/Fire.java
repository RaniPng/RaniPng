package Entity;

import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Fire extends MagicBall {


	public Fire(TileMap tm, boolean right) {

		super(tm,right,"/Sprites/Player/fire.gif",4,7,7);

		_type=Character.FIRE;

	}

}
