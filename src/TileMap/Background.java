package TileMap;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Background {

	private BufferedImage _image;

	private double _x;
	private double _y;
	private double _dx;
	private double _dy;

	private double _moveScale;

	public Background(String s, double ms) {

		try {
			_image = ImageIO.read(getClass().getResourceAsStream(s));
			_moveScale = ms;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setPosition(double x, double y) {
		this._x = (x * _moveScale) % GamePanel.WIDTH;
		this._y = (y * _moveScale) % GamePanel.HEIGHT;
	}

	public void setVector(double dx, double dy) {
		this._dx = dx;
		this._dy = dy;
	}

	public void update() {
		_x += _dx;
		_y += _dy;
	}

	public void draw(Graphics2D g) {

		g.drawImage(_image, (int) _x, (int) _y, null);

		if (_x < 0) {
			g.drawImage(_image, (int) _x + GamePanel.WIDTH, (int) _y, null);
		}
		if (_x > 0) {
			g.drawImage(_image, (int) _x - GamePanel.WIDTH, (int) _y, null);
		}
	}

}
