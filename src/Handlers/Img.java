package Handlers;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Img {
	private BufferedImage _image;
	private int x, y, width, height;

	public Img(String s, int x, int y, int width, int height) {
		try {
			_image = ImageIO.read(getClass().getResourceAsStream(s));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		setImgCords(x, y);
		setImgSize(width, height);
	}

	public void drawImg(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(_image, x, y, width, height, null);
	}

	public void setImgCords(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public void setImgSize(int width, int height) {
		this.width += width;
		this.height += height;
	}

	public void setImg(BufferedImage image) {
		_image = image;
	}
}