package GUI;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;


/*We override the JPanel paintComponent Method so that we can
import our own image as the background */


public class BackgroundPanel extends JPanel{

     private Image backgroundImage;

    public BackgroundPanel(Image image) {
        this.backgroundImage = image;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
