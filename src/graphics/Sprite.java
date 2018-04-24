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

public abstract class Sprite {
	protected vec3 vel = new vec3(0, 0, 0);
	protected vec3 acc = new vec3(0, 0, 0);
	protected vec3 pos = new vec3(0, 0, 0);
    protected double scale = 1;	
    protected double scaleVel = 0;	
    protected double scaleAcc = 0;	
    protected double angAcc = 0;
    protected double angVel = 0;
    protected double angle  = 0;
    protected int width;
    protected int height;
    protected int health = 100;
    protected Image image;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static Thread animation;
    public double alpha = 1;
    
    public void update(int winX, int winY) {
    	final double eff = 0.95;//efficiency loss of speed
    	vel = (vel.times(eff)).plus(acc);//increases velocity by speed (with efficiency loss)
    	if(pos.getX() >= winX - width*scale ) 					vel.setX(-Math.abs(vel.getX()));	
    	else if (pos.getX() <= 0) 									vel.setX(Math.abs(vel.getX()));										
    	if(pos.getY() >= winY - height*scale ) 					vel.setY(-Math.abs(vel.getY()));	
    	else if (pos.getY() <= 0) 									vel.setY(Math.abs(vel.getY()));	
    	pos = pos.plus(vel);
    	angVel = angVel*eff*0.9 + angAcc;//updates velocity
    	angle += angVel;//updates angle
    	scaleVel = scaleVel*eff*0.8 + scaleAcc;
    	if(scale > 0.1) scale += scaleVel;
    	else scale += Math.abs(scaleVel);
    }
    public void collideWith(Sprite collision) {
    	final double dist = Math.pow(getXcntr() - collision.getXcntr(), 2) + Math.pow(getYcntr() - collision.getYcntr(), 2);
    	final double radiusSum = width*scale/2.0 + collision.getWidth()*collision.getScale()/2.0;
    	final double bounce = 0.01;//bounce coefficient
    	if(dist < Math.pow(radiusSum, 2)) {
    		vec3 normal = new vec3(collision.getXcntr() - getXcntr(), collision.getYcntr() - getYcntr());
    		vel = vel.plus(normal.times((radiusSum - dist) / dist).times(bounce));
    	}
    }
    public void die() {
    	health = 0;
    }
    //getters
    public int getX() { return (int)pos.getX(); }
    public int getY() { return (int)pos.getY(); }
    public vec3 getPos() {	return pos; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }    
    public double getAngle() { return angle; }
    public double getScale() { return scale; }
    public Image getImage() { return image; }
    public int getXcntr() {	return (int) (pos.getX() + scale * width / 2.0); }
    public int getYcntr() {	return (int) (pos.getY() + scale * height / 2.0); }
    public vec3 getCenterPos() { return new vec3((pos.getX() + scale * width / 2.0), pos.getY() + scale * height / 2.0, 0); }
    public int getHealth() { return health; }
    //setters
    public void setAngle(float a) { angle = toRad(a); } 
    protected double toRad(double degrees) {
    	return (degrees * Math.PI / 180.0);
    }
}