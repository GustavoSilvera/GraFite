package graphics;

import java.awt.Image;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

public class drop{
	private vec3 pos;
	private int value;
	private Image img;
	private double size;
	public drop(int x, int y, int v, boolean isHealth) {
		setPos(new vec3(x, y));
		String filePath = null;
		if(isHealth) filePath = "img/heart.png";
		else filePath = "img/ammo.png";
		ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( filePath ));
		img = asset.getImage();
		value = v;
		if (Math.pow(value/2, 2) < 2) setSize(2);
		else setSize(Math.pow(value/2, 2));
	}
	public vec3 getPos() {
		return pos;
	}
	public void setPos(vec3 pos) {
		this.pos = pos;
	}
	public Image getImg() {
		return img;
	}
	public int getValue() {
		return value;
	}
    public vec3 getCenterPos() { 
    	return new vec3(
    			pos.getX() + value * size / 2.0, 
    			pos.getY() + value * size / 2.0, 0); 
    }
	public static drop randomStart(int winX, int winY, boolean isHealth) {
		int randomX = ThreadLocalRandom.current().nextInt(100, winX - 100);
    	int randomY = ThreadLocalRandom.current().nextInt(100, winY - 100);
    	int randomVal = ThreadLocalRandom.current().nextInt(1, 10);
    	drop rando = new drop(randomX, randomY, randomVal, isHealth);
    	return rando;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double d) {
		this.size = d;
	}

	
}
