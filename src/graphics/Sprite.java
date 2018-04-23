package graphics;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Sprite {
    private vec3 vel = new vec3(0, 0, 0);
    private vec3 acc = new vec3(0, 0, 0);
    private vec3 pos = new vec3(0, 0, 0);
    private double speed = 1;
    private double scale = 1;	
    private double scaleVel = 0;	
    private double scaleAcc = 0;	
    private double angAcc = 0;
    private double angVel = 0;
    private double angle  = 0;
    private int w;
    private int h;
    private boolean lifeStatus = true;
    public double mX;
    public double mY;
    private Image image;
    private int kills = 0;
    public weapon gun = new weapon();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Sprite() {
    	ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/freddy.png" ));//"C:/Users/grs53/eclipse-workspace/graphics/src/graphics/freddy.png");
        image = asset.getImage(); 
        w = image.getWidth(null);
        h = image.getHeight(null);
    }
    public Sprite(int x, int y, double scalar, double s) {
    	ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/freddy.png" ));//"C:/Users/grs53/eclipse-workspace/graphics/src/graphics/freddy.png");
        image = asset.getImage(); 
        w = image.getWidth(null);
        h = image.getHeight(null);
        pos.setX(x);
        pos.setY(y);
        scale = scalar;
        speed = s;
    }
    public void update(int winX, int winY) {
    	final double eff = 0.95;//efficiency loss of speed
    	vel = (vel.times(eff)).plus(acc);//increases velocity by speed (with efficiency loss)
    	if(pos.getX() >= winX - w*scale ) 					vel.setX(-Math.abs(vel.getX()));	
    	else if (pos.getX() <= 0) 									vel.setX(Math.abs(vel.getX()));										
    	if(pos.getY() >= winY - h*scale ) 					vel.setY(-Math.abs(vel.getY()));	
    	else if (pos.getY() <= 0) 									vel.setY(Math.abs(vel.getY()));	
    	pos = pos.plus(vel);
    	angVel = angVel*eff*0.9 + angAcc;//updates velocity
    	angle += angVel;//updates angle
    	scaleVel = scaleVel*eff*0.8 + scaleAcc;
    	if(scale > 0.1) scale += scaleVel;
    	else scale += Math.abs(scaleVel);
    	//scale += 0.00001*kills;
    }
    public void getShot(Sprite shooter) {
    	if(
    		shooter.gun.isFiring && 
			Math.abs(getXcntr() - (shooter.getXcntr() + shooter.gun.length*Math.cos(shooter.getAngle()))) < w*scale && 
			Math.abs(getYcntr() - (shooter.getYcntr() + shooter.gun.length*Math.sin(shooter.getAngle()))) < h*scale ) 
    			die();
    }
    public void target(Sprite goal) {//moves towards goal
    	double goalX = goal.getX() + goal.getWidth()/2.0*goal.getScale() * scale;//gets middle of image X
    	double goalY = goal.getY() + goal.getWidth()/2.0*goal.getScale() * scale;//gets middle of image Y
    	if(pos.getX() < goalX) acc.setX(speed);
    	else if(pos.getX() > goalX) acc.setX(-speed);
    	if(pos.getY() < goalY) acc.setY(speed);
    	else if(pos.getY() > goalY) acc.setY(-speed);
    	angVel = 0.1;
    }
    public void collideWith(Sprite collision) {
    	final double dist = Math.pow(getXcntr() - collision.getXcntr(), 2) + Math.pow(getYcntr() - collision.getYcntr(), 2);
    	final double radiusSum = w*scale/2.0 + collision.getWidth()*collision.getScale()/2.0;
    	final double bounce = 0.01;//bounce coefficient
    	if(dist < Math.pow(radiusSum, 2)) {
    		vec3 normal = new vec3(collision.getXcntr() - getXcntr(), collision.getYcntr() - getYcntr());
    		vel = vel.plus(normal.times((radiusSum - dist) / dist).times(bounce));
    	}
    }
    public void die() {
    	lifeStatus = false;
    }
    public static Sprite randomStart(int winX, int winY) {
    	int randomX = ThreadLocalRandom.current().nextInt(0, winX);
    	int randomY = ThreadLocalRandom.current().nextInt(0, winY);
    	int random = ThreadLocalRandom.current().nextInt(10, 30);
    	
    	int randStartingWall = ThreadLocalRandom.current().nextInt(0, 4);
    	if(randStartingWall == 0) randomX = 0;
    	else if(randStartingWall == 1) randomX = winX;
    	else if(randStartingWall == 2) randomY = 0;
    	else randomY = winY;
    	Sprite rando = new Sprite(randomX, randomY, random/100.0, random/100.0);
    	return rando;
    }
    //getters
    public int getX() { return (int)pos.getX(); }
    public int getY() { return (int)pos.getY(); }
    public int getKills() { return kills; }
    public vec3 getPos() {	return pos; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }    
    public double getAngle() { return angle; }
    public double getScale() { return scale; }
    public Image getImage() { return image; }
    public int getXcntr() {	return (int) (pos.getX() + scale * w / 2.0); }
    public int getYcntr() {	return (int) (pos.getY() + scale * h / 2.0); }
    public vec3 getCenterPos() { return new vec3((pos.getX() + scale * w / 2.0), pos.getY() + scale * h / 2.0, 0); }
    public boolean isAlive() { return lifeStatus; }
    //setters
    public void setAngle(float a) { angle = toRad(a); } 
    public void addKill() { kills ++;} 
    //key listeners
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //movement
        if (key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_A) 		acc.setX(-2.0);
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)		acc.setX(2.0);
        if (key == KeyEvent.VK_UP    || key == KeyEvent.VK_W) 		acc.setY(-2.0);
        if (key == KeyEvent.VK_DOWN  || key == KeyEvent.VK_S) 		acc.setY(2.0);
        //rotations
        if (key == KeyEvent.VK_E) 		angAcc +=  Math.PI/180;//1 degree
        if (key == KeyEvent.VK_Q) 		angAcc += -Math.PI/180;//1 degree
        //scale
        if (key == KeyEvent.VK_O) 		scaleAcc =  0.01f;
        if (key == KeyEvent.VK_P) 		scaleAcc = -0.01f;
        //shooting
        if (key == KeyEvent.VK_SPACE) gun.shoot(this, this);
        
    }
    public void keyReleased(KeyEvent e) {
    	int key = e.getKeyCode();
    	//movement
        if (key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_A) 		acc.setX(0);
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)		acc.setX(0);
        if (key == KeyEvent.VK_UP    || key == KeyEvent.VK_W) 		acc.setY(0);
        if (key == KeyEvent.VK_DOWN  || key == KeyEvent.VK_S) 		acc.setY(0);
        //rotations
        if (key == KeyEvent.VK_E) 		angAcc = 0;
        if (key == KeyEvent.VK_Q) 		angAcc = 0;
        //scale
        if (key == KeyEvent.VK_O) 		scaleAcc =  0;
        if (key == KeyEvent.VK_P) 		scaleAcc =  0;
        //shooting
        if (key == KeyEvent.VK_SPACE) gun.ceaseFire();
    }
    //mouse listeners
    public void mouseMove(MouseEvent e) {
    	mX = e.getX() - pos.getX();
    	mY = e.getY() - pos.getY();
    	//angular movement;
    	angle = Math.atan(mY/mX);
    }
    
    private double toRad(double degrees) {
    	return (degrees * Math.PI / 180.0);
    }
}