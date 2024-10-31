package TileMap;


import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tile {
	
	private BufferedImage _image;
	private int _type;
	
	//collution
	private ArrayList<Integer> lowHighPoints;//contains 4 points of the Tile which tell if the sprite shold go down or up
	//also helps with staris collution
	
	// tile types
	public static int _typeState=0;
	public static final int NORMAL = _typeState++;
	public static final int BLOCKED = _typeState++;
	public static final int DEAD=_typeState++;
	
	public Tile(BufferedImage image, int type) {
		this._image = image;
		this._type = type;
		lowHighPoints=new ArrayList<Integer>();
	}
	
	public BufferedImage getImage() { return _image; }
	public int getType() { return _type; }
	
	public boolean isTransparent( int x, int y ) {	
		if(_image==null)
			return false;
		
		if(x<0)
			x=0;
		if(x>=_image.getWidth())
			x=_image.getWidth()-1;
		if(y<0)
			y=0;
		if(y>=_image.getHeight())
			y=_image.getHeight()-1;		
			
		
		  int pixel = _image.getRGB(x,y);
		  if( (pixel>>24) == 0x00 ) {
		      return true;
		  }
		  return false;
		}
	
	public void lowestCanStand() {
		lowHighPoints=new ArrayList<Integer>();
		int width=_image.getWidth();
		
		for (int i = 0; i <= 3; i++) {
			int x=0;
			x+=i*width/3;
			int temp=0;
			int y=0;
			
			while(y<TileMap.getTileSize() && isTransparent(x,y++))
				temp++;
			lowHighPoints.add(temp);		
		}
	}
	
	public void printtt() {
		for (int i = 0; i < lowHighPoints.size(); i++) {
			System.out.print(lowHighPoints.get(i)+"  ");
		}
		System.out.println();
	}
	
	
}
