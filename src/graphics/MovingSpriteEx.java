package graphics;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MovingSpriteEx extends JFrame {

    public MovingSpriteEx() {
    	add(new Board());
        setTitle("Five Knights @ Freddy's");
        //setSize(1500, 750);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MovingSpriteEx program = new MovingSpriteEx();
            program.setVisible(true);
        });
    }
}