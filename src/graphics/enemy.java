package graphics;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

public class enemy extends Sprite{
	protected double maxSpeed = 1;

	public enemy(int x, int y, double scalar, double s) {
		int randomImage = ThreadLocalRandom.current().nextInt(0, 4);
		String randomString = null;
		if(randomImage == 0) randomString = "img/mask.png";
		else if(randomImage == 1) randomString = "img/harsh.png";
		else if(randomImage == 2) randomString = "img/glasses.png";
		else if(randomImage == 3) randomString = "img/classic.png";
		ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( randomString ));
        image = asset.getImage(); 
        width = image.getWidth(null);
        height = image.getHeight(null);
        pos.setX(x);
        pos.setY(y);
        scale = scalar;
        maxSpeed = s;
        animation = new Thread();
        animation.start();
    }
	public static enemy randomStart(int winX, int winY) {
    	int randomX = ThreadLocalRandom.current().nextInt(0, winX);
    	int randomY = ThreadLocalRandom.current().nextInt(0, winY);
    	int random = ThreadLocalRandom.current().nextInt(10, 30);
    	int randStartingWall = ThreadLocalRandom.current().nextInt(0, 4);
    	if(randStartingWall == 0) randomX = 0;
    	else if(randStartingWall == 1) randomX = winX;
    	else if(randStartingWall == 2) randomY = 0;
    	else randomY = winY;
    	enemy rando = new enemy(randomX, randomY, random/20.0, random/100.0);
    	return rando;
    }
	public void target(Sprite goal) {//moves towards goal
    	double goalX = goal.getX() + goal.getWidth()/2.0*goal.getScale() * scale;//gets middle of image X
    	double goalY = goal.getY() + goal.getWidth()/2.0*goal.getScale() * scale;//gets middle of image Y
    	if(pos.getX() < goalX) acc.setX(maxSpeed);
    	else if(pos.getX() > goalX) acc.setX(-maxSpeed);
    	if(pos.getY() < goalY) acc.setY(maxSpeed);
    	else if(pos.getY() > goalY) acc.setY(-maxSpeed);
    	//angular movement;
    	angle = Math.atan2(getYcntr() - goal.getYcntr(), getXcntr() - goal.getXcntr());//(mY / mX);
    }
	public void getShot(player shooter) {
		//for loop for all bullets
		for(int i = 0; i < shooter.gun.bullets.size(); i++) {
			if(
    		 Math.abs(getXcntr() - (shooter.getXcntr() + shooter.gun.bullets.get(i)*Math.cos(shooter.getAngle()))) < width*scale && 
    		 Math.abs(getYcntr() - (shooter.getYcntr() + shooter.gun.bullets.get(i)*Math.sin(shooter.getAngle()))) < height*scale ) 
    		die();
		}
	}
}
