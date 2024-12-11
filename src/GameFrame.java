import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {

    GameFrame() {
        // panel inside the frame where the logic of the game will be set up
        //GamePanel panel = new GamePanel();
        this.add(new GamePanel()); // creating and adding GamePanel instance to GameFrame class
        this.setTitle("Snake");
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setResizable(false);
        this.pack(); // fit the JFrame around all its components (auto-adjust the frame size)
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }





    // ------------------------------==============================------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    // ------------------------------==============================------------------------------



}
