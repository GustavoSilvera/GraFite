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
    private int x = 40;
    private int y = 60;
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
    public Sprite(int xPos, int yPos, double scalar) {
    	ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/freddy.png" ));//"C:/Users/grs53/eclipse-workspace/graphics/src/graphics/freddy.png");
        image = asset.getImage(); 
        w = image.getWidth(null);
        h = image.getHeight(null);
        x = xPos;
        y = yPos;
        scale = scalar;
    }
    public void move() {
    	final double eff = 0.99;//efficiency loss of speed
    	vel = (vel.times(eff)).plus(acc);//increases velocity by speed (with efficiency loss)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	if(x*scale >= screenSize.getWidth() - (w*scale) || x <= 0) vel.setX(-eff*vel.getX());
    	x += vel.getX();	
    	if(y*scale >= screenSize.getHeight() - (h*scale) || y <= 0) vel.setY(-eff*vel.getY());
    	y += vel.getY();
    	angVel = angVel*eff*0.9 + angAcc;//updates velocity
    	angle += angVel;//updates angle
    	scaleVel = scaleVel*eff*0.8 + scaleAcc;
    	if(scale > 0.1) scale += scaleVel;
    	else scale += Math.abs(scaleVel);
    }
    //getters
    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }    
    public double getAngle() { return angle; }
    public double getScale() { return scale; }
    public Image getImage() { return image; }
    
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
}