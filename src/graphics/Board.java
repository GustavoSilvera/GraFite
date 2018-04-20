package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Sprite Freddy;
    ArrayList<Sprite> enemies = new ArrayList<Sprite>();
    public Dimension screenSize;
    private final int msDELAY = 10;
    private final int NUM_ENEMIES = 30;
    public Board() {
    	addKeyListener(new TAdapter());
    	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setFocusable(true);
        setBackground(Color.black);
        setDoubleBuffered(true);
        Freddy = new Sprite((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2, 1);
        for(int i = 0; i < NUM_ENEMIES; i++) {
        	int randomX = ThreadLocalRandom.current().nextInt(0, (int)screenSize.getWidth());
        	int randomY = ThreadLocalRandom.current().nextInt(0, (int)screenSize.getHeight());
        	int randStartingWall = ThreadLocalRandom.current().nextInt(0, 4);
        	if(randStartingWall == 0) randomX = 0;
        	else if(randStartingWall == 1) randomX = (int)screenSize.getWidth();
        	else if(randStartingWall == 2) randomY = 0;
        	else randomY = (int)screenSize.getHeight();
        	Sprite e = new Sprite(randomX, randomY, 0.5);
        	enemies.add(e);
        }
        timer = new Timer(msDELAY, this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < enemies.size(); i++) {
            g2d.rotate(enemies.get(i).getAngle(), enemies.get(i).getXcntr(), enemies.get(i).getYcntr());
            g2d.drawImage(enemies.get(i).getImage(), enemies.get(i).getX(), (int)enemies.get(i).getY(), (int)(enemies.get(i).getScale() * enemies.get(i).getWidth()), (int)(enemies.get(i).getScale() * enemies.get(i).getHeight()), this);
        }
        g2d.rotate(Freddy.getAngle(), Freddy.getXcntr(), Freddy.getYcntr());
        g2d.drawImage(Freddy.getImage(), Freddy.getX(), Freddy.getY(), (int)(Freddy.getScale() * Freddy.getWidth()), (int)(Freddy.getScale() * Freddy.getHeight()), this);
        g2d.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {    //update()
    	Freddy.update();
    	for(int i = 0; i < enemies.size(); i++) {
    		enemies.get(i).target(Freddy, 0.5);
            enemies.get(i).update();
            for(int j = 0; j < enemies.size(); j++) {
            	if(i != j) enemies.get(i).collideWith(enemies.get(j));//physics with other entities
            	double dist = enemies.get(i).getWidth()/2*enemies.get(i).getScale() + Freddy.getWidth()*Freddy.getScale()/2;
            	if(enemies.get(i).getPos().distanceSqr(Freddy.getPos()) < Math.pow(dist, 2)) {
            		enemies.remove(i);
            		int randomX = ThreadLocalRandom.current().nextInt(0, (int)screenSize.getWidth());
                	int randomY = ThreadLocalRandom.current().nextInt(0, (int)screenSize.getHeight());
                	int randStartingWall = ThreadLocalRandom.current().nextInt(0, 4);
                	if(randStartingWall == 0) randomX = 0;
                	else if(randStartingWall == 1) randomX = (int)screenSize.getWidth();
                	else if(randStartingWall == 2) randomY = 0;
                	else randomY = (int)screenSize.getHeight();
                	Sprite s = new Sprite(randomX, randomY, 0.5);
            		enemies.add(s);
            	}
            }
        }
    	//repaint((int)Freddy.getX()-1, (int)Freddy.getY()-1, Freddy.getWidth()+2, Freddy.getHeight()+2);     
    	repaint(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());     
    }
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) { 
	        Freddy.keyReleased(e); 
        }
        @Override
        public void keyPressed(KeyEvent e) { 
        	Freddy.keyPressed(e); 
        }
    }
}