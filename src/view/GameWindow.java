package view;

import javax.swing.*;
import java.awt.*;
import static java.awt.BorderLayout.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import static javax.swing.BoxLayout.Y_AXIS;
import model.*;
import static model.PlayerDirection.*;
import static model.Tile.*;
import java.util.Timer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import persistence.HighScore;
import resources.ResourceLoader;

/**
 * A főablak, ami a játékot vezérli.
 */
public class GameWindow extends JFrame {

    //A 2. lépésből visszakapott adatok
    public Level selectedLevel;

    public String player1Name;
    public PlayerColor player1Color;

    public String player2Name;
    public PlayerColor player2Color;

    //A belső játékállás referenciái
    GameState gameState;
    Player player1;
    Player player2;
    boolean firstTime = true;       //Eső játékbetöltés-e

    //Időzítő a játékosok léptetéséhez.
    //RunGameTask runGameTask = new RunGameTask(player1, player1);
    Timer timer;
    
    //A Title screen-t megjelenítő panel
    TitlePanel titlePanel;
    
    //A háttérzenét szolgáltató szál:
    BGMThread bgmThread;

    //Alap játékablak és menüsor összeállítása
    public GameWindow() {
        this.setTitle("JavaTron");
        this.setSize(500, 540);
        this.setLocation(topX(), topY());
        setLayout(new FlowLayout(Y_AXIS));
        this.setResizable(false);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        URL url = getClass().getResource("game_icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu startGame = new JMenu("Game");
        JMenuItem start = new JMenuItem("Start Game");
        startGame.add(start);
        menuBar.add(startGame);
        JMenu other = new JMenu("Other");
        JMenuItem showLeaderBoard = new JMenuItem("Leaderboards");
        other.add(showLeaderBoard);
        JMenuItem aboutMe = new JMenuItem("Credits");
        other.add(aboutMe);
        menuBar.add(other);

        //Játékbeállítások bekérése párbeszédablakon keresztül
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setPlayers();
            }

        });

        showLeaderBoard.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showLeaderBoard();                                              //Jelenítsük meg a táblázatot egy párbeszédablakban.
            }

        });
        
        aboutMe.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                developerMessage();
            }
        });
        
        //Itt rajzold ki a title image-et.
        titlePanel = new TitlePanel("javatron_title_screen.png");
        add(titlePanel);
        
        //pack();
        
        //Itt indítod el a háttérzenét:
        bgmThread = new BGMThread("skaven_future_crew_revenge_of_the_cats.wav");
        bgmThread.start();
    }

    /**
     * Dicsőségtábla mutatása egy párbeszédablakban.
     */
    public void showLeaderBoard() {
        LeaderBoard leaderboard = new LeaderBoard(this, "Leaderboards");
        leaderboard.setVisible(true);
    }
    
    private void developerMessage(){
        JDialog messageWindow = new JDialog(this, "Credits");
        JPanel messagePanel = new JPanel();
        JLabel developerMessage = new JLabel();
        developerMessage.setText("Developed by: Gabriel Paul Korom (former ELTE student)");
        messagePanel.add(developerMessage);
        messageWindow.add(messagePanel);
        messageWindow.setSize(400, 50);
        int xLocation = (Toolkit.getDefaultToolkit().getScreenSize().width - messageWindow.getWidth()) / 2;
        int yLocation = (Toolkit.getDefaultToolkit().getScreenSize().height - messageWindow.getHeight()) / 2;
        messageWindow.setLocation(xLocation, yLocation);
        messageWindow.setVisible(true);
    }

    /**
     * Az ablak víszintes és függőleges középrezárásához szükséges koordinátákat
     * kiszámító metódusok.
     */
    private int topX() {
        return (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
    }

    private int topY() {
        return (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
    }

    /**
     * A játékbeállítások menü megjelenítése egy párbeszédablakban.
     */
    public void setPlayers() {
        PlayerSettings playerSettings = new PlayerSettings(this, "Játékosok beállítása");
        playerSettings.setVisible(true);

        System.out.println("Megjelent a játékbeállítások ablak!");
        System.out.println("");
    }

    /**
     * A játék betöltése és indítása a megadott beállítások alapján.
     */
    public void startGame() {

        //Töltsük be játékképernyőt
        //Töröljük az előző állapotot
        this.getContentPane().removeAll();

        //Játéktábla képekkel:
        gameState = new GameState(selectedLevel);
        player1 = new Player(1, player1Name, player1Color, gameState);
        player2 = new Player(2, player2Name, player2Color, gameState);
        GameBoard gameBoard = new GameBoard(player1, player2, gameState);
        gameBoard.refresh();
        add(gameBoard);

        //Gombnyomásfigyelő hozzáadása (csak első betöltéskor)
        if (this.firstTime) {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent event) {
                    super.keyPressed(event);
                    int keyCode = event.getKeyCode();
                    switch (keyCode) {
                        //Ha WASD, az első játékost mozgatja el abba az irányba.
                        case KeyEvent.VK_W:
                            player1.setDirection(PlayerDirection.NORTH);
                            break;
                        case KeyEvent.VK_A:
                            player1.setDirection(PlayerDirection.WEST);
                            break;
                        case KeyEvent.VK_S:
                            player1.setDirection(PlayerDirection.SOUTH);
                            break;
                        case KeyEvent.VK_D:
                            player1.setDirection(PlayerDirection.EAST);
                            break;
                        //Ha nyilak, akkor a másodikat.
                        case KeyEvent.VK_UP:
                            player2.setDirection(PlayerDirection.NORTH);
                            break;
                        case KeyEvent.VK_LEFT:
                            player2.setDirection(PlayerDirection.WEST);
                            break;
                        case KeyEvent.VK_DOWN:
                            player2.setDirection(PlayerDirection.SOUTH);
                            break;
                        case KeyEvent.VK_RIGHT:
                            player2.setDirection(PlayerDirection.EAST);
                            break;
                    }
                }
            });
        }
        
        pack();

        //Rajzolás után képfrissítés
        invalidate();
        revalidate();
        repaint();
        this.firstTime = false;
        
        //Elindítjuk a köridőzítőt
        timer = new Timer();
        timer.schedule(new RunGameTask(player1, player2, this), 3000, 500);
    }

    /**
     * Meccseredmény közlése párbeszédablakkal.
     */
    public void endGame() {
        timer.cancel();

        EndGameMessage endGameMessage = new EndGameMessage(this, player1, player2);
        endGameMessage.setVisible(true);
    }

    /**
     * Létrehozza a főablakot és láthatóvá teszi.
     *
     * @param args
     */
    public static void main(String[] args) {
        GameWindow window = new GameWindow();
        window.setVisible(true);
    }
}//end GameWindow
