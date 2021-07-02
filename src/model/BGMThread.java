package model;

import com.sun.tools.javac.Main;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author KrazyXL
 */
public class BGMThread extends Thread {
    
    private Clip backGroundMusic;
    
    public BGMThread(String soundFileName){
        super();
        
        try{
            backGroundMusic = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/resources/" + soundFileName));
            backGroundMusic.open(inputStream);
        } catch (LineUnavailableException e){
            System.err.println("Error: Line Unavailable.");
        } catch (UnsupportedAudioFileException e){
            System.err.println("Error: Audio file format not supported.");
        } catch (IOException e){
            e.printStackTrace();
            System.err.println("Error: IOException");
        }
    }
    
    @Override
    public void run() {
        try {
            backGroundMusic.loop(Clip.LOOP_CONTINUOUSLY);   //Végtelenített loop.
          } catch (Exception e) {
            System.err.println(e.getMessage());
          }
    }
    
}
