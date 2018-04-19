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
    private final int NUM_ENEMIES = 300;
    public Board() {
    	addKeyListener(new TAdapter());
    	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setFocusable(true);
        setBackground(Color.black);
        setDoubleBuffered(true);
        Freddy = new Sprite();
        for(int i = 0; i < NUM_ENEMIES; i++) {
        	int randomX = ThreadLocalRandom.current().nextInt(0, (int)screenSize.getWidth());
        	int randomY = ThreadLocalRandom.current().nextInt(0, (int)screenSize.getHeight());
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
            g2d.translate(enemies.get(i).getScale(), enemies.get(i).getScale());
        	g2d.scale(enemies.get(i).getScale(), enemies.get(i).getScale());
            g2d.rotate(enemies.get(i).getAngle(), enemies.get(i).getX() + enemies.get(i).getWidth()/2, enemies.get(i).getY() + enemies.get(i).getHeight()/2);
            g2d.drawImage(enemies.get(i).getImage(), (int)enemies.get(i).getX(), (int)enemies.get(i).getY(), this);
        }
        
        g2d.translate(Freddy.getScale(), Freddy.getScale());
        g2d.scale(Freddy.getScale(), Freddy.getScale());
        g2d.rotate(Freddy.getAngle(), Freddy.getX() + Freddy.getWidth()/2, Freddy.getY() + Freddy.getHeight()/2);
        g2d.drawImage(Freddy.getImage(), (int)Freddy.getX(), (int)Freddy.getY(), this);
        g2d.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {    //update()
    	Freddy.move();
    	for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move();
        }
    	//repaint((int)Freddy.getX()-1, (int)Freddy.getY()-1, Freddy.getWidth()+2, Freddy.getHeight()+2);     
    	repaint(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());     
    }
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) { 
	        	for(int i = 0; i < enemies.size(); i++) {
	                enemies.get(i).keyReleased(e);
	            }
	        	Freddy.keyReleased(e); 
        	}
        
        @Override
        public void keyPressed(KeyEvent e) { 
        	for(int i = 0; i < enemies.size(); i++) {
                enemies.get(i).keyPressed(e);
            }
        	Freddy.keyPressed(e); 
        }
    }
}