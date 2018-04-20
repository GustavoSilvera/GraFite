package graphics;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Sprite {
    private vec3 vel = new vec3(0, 0, 0);
    private vec3 acc = new vec3(0, 0, 0);
    private vec3 pos = new vec3(0, 0, 0);
    private double scale = 1;	
    private double scaleVel = 0;	
    private double scaleAcc = 0;	
    private double angAcc = 0;
    private double angVel = 0;
    private double angle  = 0;
    private int w;
    private int h;
    private Image image;

    public Sprite() {
    	ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/freddy.png" ));//"C:/Users/grs53/eclipse-workspace/graphics/src/graphics/freddy.png");
        image = asset.getImage(); 
        w = image.getWidth(null);
        h = image.getHeight(null);
    }
    public Sprite(int x, int y, double scalar) {
    	ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/freddy.png" ));//"C:/Users/grs53/eclipse-workspace/graphics/src/graphics/freddy.png");
        image = asset.getImage(); 
        w = image.getWidth(null);
        h = image.getHeight(null);
        pos.setX(x);
        pos.setY(y);
        scale = scalar;
    }
    public void update() {
    	final double eff = 0.95;//efficiency loss of speed
    	vel = (vel.times(eff)).plus(acc);//increases velocity by speed (with efficiency loss)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	if(pos.getX()*scale >= screenSize.getWidth() - (w*scale) ) 	vel.setX(-Math.abs(vel.getX()));	
    	else if (pos.getX() <= 0) 									vel.setX(Math.abs(vel.getX()));										
    	if(pos.getY()*scale >= screenSize.getHeight() - (h*scale) ) vel.setY(-Math.abs(vel.getY()));	
    	else if (pos.getY() <= 0) 									vel.setY(Math.abs(vel.getY()));	
    	pos = pos.plus(vel);
    	angVel = angVel*eff*0.9 + angAcc;//updates velocity
    	angle += angVel;//updates angle
    	scaleVel = scaleVel*eff*0.8 + scaleAcc;
    	if(scale > 0.1) scale += scaleVel;
    	else scale += Math.abs(scaleVel);
    }
    public void target(Sprite goal, double speed) {//moves towards goal
    	double goalX = goal.getX() + goal.getWidth()/2.0*goal.getScale() * scale;//gets middle of image X
    	double goalY = goal.getY() + goal.getWidth()/2.0*goal.getScale() * scale;//gets middle of image Y
    	if(pos.getX() < goalX) acc.setX(speed);
    	else if(pos.getX() > goalX) acc.setX(-speed);
    	if(pos.getY() < goalY) acc.setY(speed);
    	else if(pos.getY() > goalY) acc.setY(-speed);
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
    //getters
    public int getX() { return (int)pos.getX(); }
    public int getY() { return (int)pos.getY(); }
    public vec3 getPos() {	return pos; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }    
    public double getAngle() { return angle; }
    public double getScale() { return scale; }
    public Image getImage() { return image; }
    public int getXcntr() {	return (int) (pos.getX() + scale * w / 2.0); }
    public int getYcntr() {	return (int) (pos.getY() + scale * h / 2.0); }
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
    }
    public void keyReleased(KeyEvent e) {
    	int key = e.getKeyCode();
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
    }
    private double toRad(double degrees) {
    	return (degrees * Math.PI / 180.0);
    }
}