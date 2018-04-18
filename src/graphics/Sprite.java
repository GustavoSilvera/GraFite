package graphics;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Sprite {
    private double dx, dy, ax, ay;
    private final double dt = 1.0/60.0;
    private double x = 40;
    private double y = 60;
    private int w;
    private int h;
    private Image image;

    public Sprite() {
    	ImageIcon asset = new ImageIcon("C:/Users/Gustavo Silvera/eclipse-workspace/graphics/src/graphics/freddy.png");
        image = asset.getImage(); 
        w = image.getWidth(null);
        h = image.getHeight(null);
    }
    
    public void move() {
    	final double fr = 0.95;//efficiency loss of speed
    	dx = dx*fr+ax;
    	dy = dy*fr+ay;
        x += dx;
        y += dy;
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
        if (key == KeyEvent.VK_LEFT) 		ax = -2;
        if (key == KeyEvent.VK_RIGHT)		ax = 2;
        if (key == KeyEvent.VK_UP) 			ay = -2;
        if (key == KeyEvent.VK_DOWN) 		ay = 2;
    }
    public void keyReleased(KeyEvent e) {
    	int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) 		ax = 0;
        if (key == KeyEvent.VK_RIGHT)		ax = 0;
        if (key == KeyEvent.VK_UP) 			ay = 0;
        if (key == KeyEvent.VK_DOWN) 		ay = 0;
    }
}