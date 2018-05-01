package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class player extends Sprite{
	public weapon gun = new weapon();
	private int health = 15;
	private int kills = 0;

	public player(int startX, int startY, double scalar) {
	    	ImageIcon asset = new ImageIcon(getClass().getClassLoader().getResource( "img/freddy.png" ));//"C:/Users/grs53/eclipse-workspace/graphics/src/graphics/freddy.png");
	        image = asset.getImage(); 
	        width = image.getWidth(null);
	        height = image.getHeight(null);
	        pos.setX(startX);
	        pos.setY(startY);
	        scale = scalar;
	        animation = new Thread();
	        animation.start();
	   }
	public void getHurt(int amount) {
		if(health > 0) health-= 1 + amount/10;//damage based off number of enemies
	}
	public void getHeal(int value) {
		health+=value;
	}
	public void getAmmo(int value) {
		gun.ammunition+=value;
	}
	public void update(int winX, int winY) {
		super.update(winX,  winY);
		if(health < 1) {
			scale-=0.02; 
			angle++;
		}
	}
	//getters
    public int getKills() { return kills; }
    public int getHealth() {return health;}
	public void addKill() { kills ++;} 
    //key listeners
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int amnt = 1;
        if(key == KeyEvent.VK_SHIFT)  amnt = 2;
        //movement
        if (key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_A) 		acc.setX(-amnt);
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)		acc.setX(amnt);
        if (key == KeyEvent.VK_UP    || key == KeyEvent.VK_W) 		acc.setY(-amnt);
        if (key == KeyEvent.VK_DOWN  || key == KeyEvent.VK_S) 		acc.setY(amnt);
        //rotations
        if (key == KeyEvent.VK_E) 		angAcc +=  Math.PI/180;//1 degree
        if (key == KeyEvent.VK_Q) 		angAcc += -Math.PI/180;//1 degree
        //scale
        if (key == KeyEvent.VK_O) 		scaleAcc =  0.01f;
        if (key == KeyEvent.VK_P) 		scaleAcc = -0.01f;
        //shooting
        if (key == KeyEvent.VK_SPACE) if(!gun.isFiring) gun.shoot(getAngle());
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
    	double mX = e.getX() - pos.getX();
    	double mY = e.getY() - pos.getY() - scale*0.5*height;
    	//angular movement;
    	angle = Math.atan2(mY, mX);//(mY / mX);
    }
    
}
