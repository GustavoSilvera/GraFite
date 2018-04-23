package graphics;

import java.awt.Image;

import javax.swing.ImageIcon;

public class weapon {
	private int ammo;
	private int killCount;
	private double angle;
	public boolean isFiring;
    private Image image;

	public weapon() {
		ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/pow.png" ));
		image = asset.getImage(); 
		ammo = 100;
		killCount = 0;
		angle = 0;
		isFiring = false;
	}
	
	public void shoot(Sprite gunner, Sprite target) {
		isFiring = true;
		ammo--;
		if(gunner.getX() < target.getX()) {
			
		}
		else if(gunner.getX() > target.getX()) {
			
		}
		else if(gunner.getY() < target.getY()) {
			
		}
		else if(gunner.getY() > target.getY()) {
		
		}
	}
	public void ceaseFire() {
		isFiring = false;
	}

	public Image getImage() {return image;	}

}
