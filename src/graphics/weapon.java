package graphics;

import java.awt.Image;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class weapon {
	public int ammunition;
	public double angle;
	private int killCount;
	public boolean isFiring;
    private Image image;
    public int numBullets = 0;
    ArrayList<Integer> bullets = new ArrayList<Integer>();

	public weapon() {
		ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/pow.png" ));
		image = asset.getImage(); 
		ammunition = 100;
		killCount = 0;
		isFiring = false;
	}
	public void shoot(double getAngle) {
		if(ammunition > 0) {//has to have SOMETHING
			angle = getAngle;
			isFiring = true;
			ammunition--;
			bullets.add(new Integer(0));
		}
	}
	public void ceaseFire() {
		isFiring = false;
	}

	public Image getImage() {return image;	}
	public double getAngle() {return angle;}
	public int getAmmo() {return ammunition;}
}
