package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
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
    private final int NUM_ENEMIES = 40;
    public Board() {
    	addKeyListener(new TAdapter());
    	addMouseListener(new MAdapter());
    	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setFocusable(true);
        setBackground(Color.black);
        setDoubleBuffered(true);
        Freddy = new Sprite((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2, 0.5, 1);
        for(int i = 0; i < NUM_ENEMIES; i++) {
        	enemies.add(Sprite.randomStart((int)screenSize.getWidth(), (int)screenSize.getHeight()));
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
            g2d.rotate(-(enemies.get(i).getAngle()), enemies.get(i).getXcntr(), enemies.get(i).getYcntr());
        }
        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(10f));
        if(Freddy.gun.isFiring) 
        	g2d.draw(new Line2D.Double(Freddy.getXcntr(), Freddy.getYcntr(), Freddy.getXcntr() + 1000*Math.cos(Freddy.getAngle()), Freddy.getYcntr() + 1000*Math.sin(Freddy.getAngle())));
        g2d.rotate(Freddy.getAngle(), Freddy.getXcntr(), Freddy.getYcntr());
        g2d.drawImage(Freddy.getImage(), Freddy.getX(), Freddy.getY(), (int)(Freddy.getScale() * Freddy.getWidth()), (int)(Freddy.getScale() * Freddy.getHeight()), this);
        g2d.setColor(new Color(255, 255, 255));
        g2d.rotate(-Freddy.getAngle(), Freddy.getXcntr(), Freddy.getYcntr());
        g2d.setFont(new Font("Purisa", Font.PLAIN, 40));
        g2d.drawString("EnemiesKilled: " + Freddy.getKills(), 50, 50);
        g2d.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {    //update()
    	Freddy.update(getWidth(), getHeight());
    	for(int i = 0; i < enemies.size(); i++) {
    		enemies.get(i).target(Freddy);
            enemies.get(i).update(getWidth(), getHeight());
            enemies.get(i).getShot(Freddy);
            for(int j = 0; j < enemies.size(); j++) {
            	if(i != j) enemies.get(i).collideWith(enemies.get(j));//physics with other entities
            	//double dist = enemies.get(i).getWidth()/2*enemies.get(i).getScale() + Freddy.getWidth()*Freddy.getScale()/2;
            	/*if(enemies.get(i).getCenterPos().distanceSqr(Freddy.getCenterPos()) < Math.pow(dist, 2)) {
            		enemies.remove(i);
            		Freddy.addKill();
            		enemies.add(Sprite.randomStart(getWidth(), getHeight()));
            	}*/
            }
            if(!enemies.get(i).isAlive()) {
        		enemies.remove(i);
        		Freddy.addKill();
        		enemies.add(Sprite.randomStart(getWidth(), getHeight()));
        	}
        }
    	//repaint((int)Freddy.getX()-1, (int)Freddy.getY()-1, Freddy.getWidth()+2, Freddy.getHeight()+2);     
    	repaint(0, 0, getWidth(), getHeight());     
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
    private class MAdapter extends MouseAdapter {
    	@Override
        public void mouseMoved(MouseEvent e) {
        	Freddy.mouseMove(e);
        }
    }
}