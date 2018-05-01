package graphics;

import java.awt.Image;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class weapon {
	public int ammunition;
	public double angle;
	public boolean isFiring;
    public int numBullets = 0;
    ArrayList<Integer> bullets = new ArrayList<Integer>();

	public weapon() {
		ammunition = 100;
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

	public double getAngle() {return angle;}
	public int getAmmo() {return ammunition;}
}
