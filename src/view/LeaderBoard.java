package view;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import persistence.HighScore;

/**
 * A dicsőségtáblát megjelenítő párbeszédablak.
 *
 * @author KrazyXL
 */
public class LeaderBoard extends JDialog {

    public LeaderBoard(Frame owner, String title) {
        super(owner, title);
        this.setSize(200, 210);
        this.setResizable(false);
        setLocation(topX(), topY());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel tablePanel = new JPanel();

        ArrayList<HighScore> leaderboard = new ArrayList<>();
        
        //Itt töltsd fel a toplistát a txt-ből. Csak az első 10 elemét hagyd meg.
        //Beolvassuk a fájlból a toplistát:
        File leaderboardData = new File("src/resources/leaderboard.txt");
        BufferedReader reader = null;
        
        readTop10(leaderboardData, reader, leaderboard);

        LeaderboardModel lbm = new LeaderboardModel(leaderboard);

        JTable leaderboardTable = new JTable(lbm);
        tablePanel.add(leaderboardTable);
        add(tablePanel);
        //pack();      //Működne, de így nem lehet lekérni a szélességet és a magasságot.
    }
    
    /**
     * Beolvassuk a toplistát egy txt-ből.
     * @param from
     * @param using
     * @param into 
     */
    private void readTop10(File from, BufferedReader using, ArrayList<HighScore> into){
        try{
            using = new BufferedReader(new FileReader(from));
        } catch (FileNotFoundException e){                                      //Nem kéne előfordulnia.
            System.err.println("Error: src/resources/leaderboard.txt not found");
        }
        
        try{
            String currentLine = null;
            int lineCount = 1;
            while(lineCount <= 10 && (currentLine = using.readLine())!=null){
                String[] lineData = currentLine.split(";");
                String currentPlayerName = lineData[0];
                int currentPlayerScore = Integer.parseInt(lineData[1]);
                into.add(new HighScore(currentPlayerName, currentPlayerScore));
                lineCount++;
            }
        } catch (IOException e){
            System.err.println("Error: IOException while opening src/resources/leaderboard.txt");
        }
    }

    private int topX() {
        return (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
    }

    private int topY() {
        return (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
    }
}
