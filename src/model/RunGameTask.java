package model;

import java.awt.Container;
import java.util.TimerTask;
import view.GameWindow;

/**
 * Játékosokat léptető periodikus művelet (egy kör a játékban).
 *
 * @author KrazyXL
 */
public class RunGameTask extends TimerTask {

    Player player1;
    Player player2;
    GameWindow gameWindow;
    Container contentPane;

    public RunGameTask(Player player1, Player player2, GameWindow gameWindow) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameWindow = gameWindow;
        this.contentPane = gameWindow.getContentPane();
    }

    /**
     * Játékosok léptetése a legutóbb megadott irányokba.
     */
    @Override
    public void run() {
        if (player1.isAlive() && player2.isAlive()) {
            player1.move();
            player2.move();
            contentPane.invalidate();
            contentPane.validate();
            contentPane.repaint();
        } else {
            gameWindow.endGame();
        }
    }

}
