package graphics;

import java.awt.Image;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;

public class weapon {
	private int ammo;
	private int killCount;
	private double angle;
	public boolean isFiring;
    private Image image;
    public int length;
	public weapon() {
		ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/pow.png" ));
		image = asset.getImage(); 
		ammo = 100;
		killCount = 0;
		angle = 0;
		length = 100;
		isFiring = false;
	}
	
	public void shoot(Sprite gunner, Sprite target) {
		isFiring = true;
		ammo--;
	}
	public void ceaseFire() {
		isFiring = false;
		length = 100;
	}

	public Image getImage() {return image;	}

}
