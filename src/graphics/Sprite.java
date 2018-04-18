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
    private int w;
    private int h;
    private Image image;

    public Sprite() {
    	ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/freddy.png" ));//"C:/Users/grs53/eclipse-workspace/graphics/src/graphics/freddy.png");
        image = asset.getImage(); 
        w = image.getWidth(null);
        h = image.getHeight(null);
    }
    
    public void move() {
    	final double eff = 1;//efficiency loss of speed
    	vel = (vel.times(eff)).plus(acc);//increases velocity by speed (with efficiency loss)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	if(x >= screenSize.getWidth() - w || x <= 0) vel.setX(-eff*vel.getX());
    	x += vel.getX();	
    	if(y >= screenSize.getHeight() - h || y <= 0) vel.setY(-eff*vel.getY());
    	y += vel.getY();
    }
    //getters
    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }    
    public Image getImage() { return image; }
    
    //key listeners
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_A) 		acc.setX(-2.0);
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)		acc.setX(2.0);
        if (key == KeyEvent.VK_UP    || key == KeyEvent.VK_W) 		acc.setY(-2.0);
        if (key == KeyEvent.VK_DOWN  || key == KeyEvent.VK_S) 		acc.setY(2.0);
    }
    public void keyReleased(KeyEvent e) {
    	int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_A) 		acc.setX(0);
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)		acc.setX(0);
        if (key == KeyEvent.VK_UP    || key == KeyEvent.VK_W) 		acc.setY(0);
        if (key == KeyEvent.VK_DOWN  || key == KeyEvent.VK_S) 		acc.setY(0);
    }
}