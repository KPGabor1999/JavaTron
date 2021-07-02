package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import resources.ResourceLoader;

/**
 *
 * @author KrazyXL
 */
public class TitlePanel extends JPanel {
    Image titleImage;
    
    public TitlePanel(String imageName) {
        super();
        try{
            titleImage = ResourceLoader.loadImage("resources/" + imageName);
        } catch (IOException e){
            System.err.println("Nem sikerült betölteni a kezdőképernyőt.");
        }
        setSize(450, 450);
        
        try{
            Image image = ImageIO.read(new File("src/resources/javatron_title_screen.png"));
            JLabel picLabel = new JLabel(new ImageIcon(image));
            add(picLabel);
            repaint(); 
        } catch (IOException e){
            System.err.println("Error: IOException while loading src/resources/javatron_title_screen.png");
        }
    }

    /*
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.drawImage(titleImage, 0, 0, 450, 450, null);     //Miért csak egy fekete négyzetet rajzol?
    }*/
    
}
