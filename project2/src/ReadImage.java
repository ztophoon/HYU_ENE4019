import java.net.URL;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
 
public class ReadImage {	
    public void RI(String urlstring) {
    	Image image = null;
    	
        try {
            URL url = new URL(urlstring);
            image = ImageIO.read(url);
        } catch (IOException e) {
        	e.printStackTrace();
        }
 
        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(image));

        frame.add(label);
        frame.setSize(300, 300);
        frame.setBounds(100, 100, 800, 600);
        frame.setVisible(true);
    }
}