package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Main.GamePanel;
import jdk.internal.joptsimple.internal.Rows;

@SuppressWarnings("unused")
public class TileMap {

	// position
	private double _x;
	private double _y;

	// bounds
	private int _xmin;
	private int _ymin;
	private int _xmax;
	private int _ymax;

	private double _tween;

	// map
	private int[][] _map;
	private final static int _tileSize = 30;// tile imgs boxes in game are 15
	private int _numRows;
	private int _numCols;
	private int _width;
	private int _height;

	// tileset
	private BufferedImage _tileset;
	private int _numTilesAcross;
	private int _numTilesTypes;
	private Tile[][] _tiles;

	// drawing
	private int _rowOffset;
	private int _colOffset;

	private int _numRowsToDraw;
	private int _numColsToDraw;

	private int _counter = 0;

	public TileMap(int Rows, int Cols) {
		// 107
		// 8
		_map = new int[Rows][Cols];
		for (int i = 0; i < Rows; i++)
			for (int j = 0; j < Cols; j++)
				_map[i][j] = 0;

		_numRows = Rows;
		_numCols = Cols;
		_numRowsToDraw = GamePanel.HEIGHT / _tileSize + 2;
		_numColsToDraw = GamePanel.WIDTH / _tileSize + 2;
		_tween = 0.07;
		// _counter=Rows-1;
	}

	// Poses all the Tiles from the Tile image (string s) in the Tiles matrix
	public void loadTiles(String s) {

		try {

			_tileset = ImageIO.read(getClass().getResourceAsStream(s));
			_numTilesAcross = _tileset.getWidth() / _tileSize;
			_numTilesTypes = _tileset.getHeight() / _tileSize;
			
			_tiles = new Tile[_numTilesTypes][_numTilesAcross];

			BufferedImage subimage;
			for (int col = 0; col < _numTilesAcross; col++) {
				for (int i = 0; i < _numTilesTypes; i++) {
					int type=Tile.NORMAL;
					if(i>=30/_tileSize)//every block is 30
						type=Tile.BLOCKED;			
					subimage = _tileset.getSubimage(col * _tileSize, _tileSize*i, _tileSize, _tileSize);
					_tiles[i][col] = new Tile(subimage, type);
					if(type==Tile.BLOCKED)
						_tiles[i][col].lowestCanStand();
				}			
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String fileName) {

		_width = _numCols * _tileSize;
		_height = _numRows * _tileSize;

		_xmin = GamePanel.WIDTH - _width;
		_xmax = 0;
		_ymin = GamePanel.HEIGHT - _height;
		_ymax = 0;

		try {

			InputStream file = getClass().getResourceAsStream(fileName);
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			if (doc.hasChildNodes()) {
				readNode(doc.getChildNodes());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int[][] get_map() {
		return _map;
	}

	private void readNode(NodeList nodeList) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						_map[_counter / _numCols][_counter % _numCols] = Integer.parseInt(node.getNodeValue());
						_counter++;
					}
				}

				if (tempNode.hasChildNodes()) {
					readNode(tempNode.getChildNodes());
				}
			}
		}
		
	}
	


	public static int getTileSize() {
		return _tileSize;
	}

	public double getx() {
		return _x;
	}

	public double gety() {
		return _y;
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getNumRows() {
		return _numRows;
	}

	public int getNumCols() {
		return _numCols;
	}
	public Tile getTile(int row,int col) {
		
		if (col < 0 || col >= _numCols||row >= _numRows||row<0)
			return null;
		
		int rc = _map[row][col];
		int r = rc / _numTilesAcross;
		int c = rc % _numTilesAcross;
		if(c!=0)
			c-=1;
		return _tiles[r][c];
		
	}
	
	public int getType(int row, int col) {
		//
		if(row >= _numRows)
			return Tile.DEAD;
		
		if (col <= 0 || col >= _numCols)
			return Tile.BLOCKED;
		if(row < 0)
			return Tile.NORMAL;
		int rc = _map[row][col];
		int r = rc / _numTilesAcross;
		int c = rc % _numTilesAcross;
		if(c!=0)
			c-=1;
		return _tiles[r][c].getType();
	}
	

	public void setTween(double d) {
		_tween = d;
	}

	public void setBounds(int i1, int i2, int i3, int i4) {
		_xmin = GamePanel.WIDTH - i1;
		_ymin = GamePanel.WIDTH - i2;
		_xmax = i3;
		_ymax = i4;
	}

	public void setPosition(double x, double y) {
		this._x += (x - this._x) * _tween;
		this._y += (y - this._y) * _tween;

		fixBounds();

		_colOffset = (int) -this._x / _tileSize;
		_rowOffset = (int) -this._y / _tileSize;

	}

	private void fixBounds() {
		if (_x < _xmin)
			_x = _xmin;
		if (_y < _ymin)
			_y = _ymin;
		if (_x > _xmax)
			_x = _xmax;
		if (_y > _ymax)
			_y = _ymax;
	}

	public void draw(Graphics2D g) {

		for (int row = _rowOffset; row < _rowOffset + _numRowsToDraw; row++) {

			if (row >= _numRows)
				break;

			for (int col = _colOffset; col < _colOffset + _numColsToDraw; col++) {

				if (col >= _numCols)
					break;

				if (_map[row][col] == 0)
					continue;
		
				int rc = _map[row][col];
				int r = rc / _numTilesAcross;
				int c = rc % _numTilesAcross-1;
				try {
					g.drawImage(_tiles[r][c].getImage(), (int) _x + col * _tileSize, (int) _y + row * _tileSize, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		}

	}

	public static int getTilesize() {
		return _tileSize;
	}

	public int get_rowOffset() {
		return _rowOffset;
	}

	public int get_colOffset() {
		return _colOffset;
	}

	public int get_numRowsToDraw() {
		return _numRowsToDraw;
	}

	public int get_numColsToDraw() {
		return _numColsToDraw;
	}
}