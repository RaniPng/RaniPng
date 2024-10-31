package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import TileMap.TileMap;

public abstract class ItemAndSpell extends MapObject {
	protected BufferedImage[] _sprites;
	protected String _type;
	protected boolean _remove;
	
	public ItemAndSpell(TileMap tm,boolean right) {
		super(tm);
		_facingRight = right;	
	}
	
	public void setSpirte(int n) {
		_animation = new Animation();
		_animation.setFrames(_sprites);
		_animation.setDelay(n);		
	}
	
	public abstract ItemAndSpell shouldHit();
	
	public String getType() {
		return _type;
	}
	
	public boolean shouldRemove() {
		return _remove;
	}
	
	public abstract void setHit() ;
	public abstract void update() ;
	
	public void draw(Graphics2D g) {
		setMapPosition();
		super.draw(g);
	}



}
