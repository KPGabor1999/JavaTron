package resources;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Az előadáson tanult ResourceLoader osztály.
 */
public class ResourceLoader {

    public static InputStream loadResource(String resName) {                     //ez szövegfájlokhoz van
        return ResourceLoader.class.getClassLoader().getResourceAsStream(resName);
    }

    public static Image loadImage(String resName) throws IOException {           //ez képfájlokhoz van
        URL url = ResourceLoader.class.getClassLoader().getResource(resName);
        return ImageIO.read(url);
    }
}
