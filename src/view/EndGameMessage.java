package view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.Player;
import model.Winner;
import static model.Winner.*;
import persistence.*;

/**
 * A meccs végeredményét közvetítő párbeszédablak.
 *
 * @author KrazyXL
 */
public class EndGameMessage extends JDialog {

    private Player player1;
    private Player player2;
    Winner winner;

    public EndGameMessage(Frame owner, Player player1, Player player2) {
        super(owner);
        this.player1 = player1;
        this.player2 = player2;

        setSize(300, 120);
        setLocation(topX(), topY());
        setResizable(false);
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        setLayout(new FlowLayout(Y_AXIS));                       //Kell megint segéd-BorderLayout?

        //Írd ki az üzenetet egy JLabel-re.
        JLabel winnerAnnouncement = new JLabel();
        winnerAnnouncement.setFont(new Font("Verdana", Font.PLAIN, 24));
        if (player1.isAlive() && !player2.isAlive()) {
            this.winner = PLAYER1;
            winnerAnnouncement.setText(player1.name + " won!");
        } else if (!player1.isAlive() && player2.isAlive()) {
            this.winner = PLAYER2;
            winnerAnnouncement.setText(player2.name + " won!");
        } else {
            this.winner = EVEN;
            winnerAnnouncement.setText("It's a draw!");
        }
        add(winnerAnnouncement);
        
        //Automatikusan frissítsd a toplistát:
        System.out.println("Most kezeljük a toplistát.");
        updateLeaderboard();

        JPanel endGameOptionsPanel = new JPanel(new FlowLayout());
        
        //Visszavágó gomb:
        JButton RematchButton = new JButton("Rematch");
        RematchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //Új meccset indítunk a jelenlegi beállításokkal.
                GameWindow parentWindow = (GameWindow)EndGameMessage.this.getParent();
                parentWindow.startGame();
            }

        });
        endGameOptionsPanel.add(RematchButton);
        
        //Új játék gomb:
        JButton NewGameButton = new JButton("New Game");
        NewGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //Új meccset indítunk a jelenlegi beállításokkal.
                GameWindow parentWindow = (GameWindow)EndGameMessage.this.getParent();
                parentWindow.setPlayers();
            }

        });
        endGameOptionsPanel.add(NewGameButton);
        
        add(endGameOptionsPanel);
    }

    private int topX() {
        return (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
    }

    private int topY() {
        return (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
    }

    /**
     * Dicsőségtábla frissítése.
     */
    public void updateLeaderboard() {
        Player currentWinner;

        switch (this.winner) {
            case PLAYER1:
                currentWinner = this.player1;
                break;
            case PLAYER2:
                currentWinner = this.player2;
                break;
            default:
                return;
        }
        
        ArrayList<HighScore> leaderboard = new ArrayList<>();
        
        //Beolvassuk a fájlból a toplistát:
        File leaderboardData = new File("src/resources/leaderboard.txt");
        BufferedReader reader = null;
        
        readEntries(leaderboardData, reader, leaderboard);
        
        //Beszújruk az új entry-t a listába:
        insertEntry(leaderboard, currentWinner.name);
        
        //Rendezzük a toplistát csökkenő sorrendben (Comparable interfész segítségével):
        Collections.sort(leaderboard, (entry1, entry2) -> entry1.compareTo(entry2));
        //Debug:
        System.out.println("A rendezett lista:");
        for(HighScore currentEntry : leaderboard){
            System.out.println(currentEntry);
        }
        
        //A toplistát visszaírjuk a fájlba.
        BufferedWriter writer = null;
        saveToFile(leaderboard, writer, leaderboardData);
    }
    
    /**
     * Beolvassuk a toplistát egy txt-ből.
     * @param from
     * @param using
     * @param into 
     */
    private void readEntries(File from, BufferedReader using, ArrayList<HighScore> into){
        try{
            using = new BufferedReader(new FileReader(from));
        } catch (FileNotFoundException e){                                      //Nem kéne előfordulnia.
            System.err.println("Error: src/resources/leaderboard.txt not found");
        }
        
        try{
            String currentLine;
            while((currentLine = using.readLine())!=null){
                String[] lineData = currentLine.split(";");
                String currentPlayerName = lineData[0];
                int currentPlayerScore = Integer.parseInt(lineData[1]);
                into.add(new HighScore(currentPlayerName, currentPlayerScore));
            }
        } catch (IOException e){
            System.err.println("Error: IOException while opening src/resources/leaderboard.txt");
        }
    }
    
    /**
     * Új játékos beszúrása a toplistába.
     * @param leaderboard
     * @param playerName 
     */
    private void insertEntry(ArrayList<HighScore> leaderboard, String playerName){
        boolean inserted = false;
        
        for(HighScore currentEntry : leaderboard){
            if(currentEntry.getName().equals(playerName)){
                currentEntry.increaseScore();
                inserted = true;
            }
        }
        
        if(!inserted){
            leaderboard.add(new HighScore(playerName, 1));
        }
    }
    
    /**
     * A toplista fájlba mentése.
     * @param from
     * @param using 
     */
    private void saveToFile(ArrayList<HighScore> from, BufferedWriter using, File into){
        try{
            using = new BufferedWriter(new FileWriter(into));
            for(HighScore currentEntry : from){
                //System.out.println("A fájlba írandó sor: " + currentEntry.toString());
                using.write(currentEntry.toString());
                using.newLine();
            }
            using.close();
        } catch (IOException e){
            System.err.println("Errror IOException when writing src/resources/leaderboard.txt");
        }
    }
}
