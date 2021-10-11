package app;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class AppFrame extends JFrame{

    private static final long serialVersionUID = 1L;

    AppFrame(String title) {
        super(title);
        this.setUndecorated(true);
        this.setSize(730, 115);
        ImageIcon imageIcon = new ImageIcon("app/srtPlayer.png");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
    }
}
