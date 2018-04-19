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
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Sprite Freddy;
    private final int msDELAY = 10;

    public Board() {
    	addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        setDoubleBuffered(true);
        Freddy = new Sprite();
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
        g2d.rotate(Freddy.getAngle(), Freddy.getX() + Freddy.getWidth()/2, Freddy.getY() + Freddy.getHeight()/2);
        g2d.drawImage(Freddy.getImage(), (int)Freddy.getX(), (int)Freddy.getY(), this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {    //update()
    	Freddy.move();
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	//repaint((int)Freddy.getX()-1, (int)Freddy.getY()-1, Freddy.getWidth()+2, Freddy.getHeight()+2);     
    	repaint(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());     
    }
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) { Freddy.keyReleased(e); }
        
        @Override
        public void keyPressed(KeyEvent e) { Freddy.keyPressed(e); }
    }
}