package view;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import model.*;
import resources.*;

/**
 * A játéktáblát kirajzoló osztály.
 *
 * @author KrazyXL
 */
public class GameBoard extends JPanel {

    private Player player1;
    private Player player2;
    private GameState gameState;
    private Image SYNTHWAVE, //Padlócsempék:
            MATRIX,
            VAPORWAVE,
            CHESSBOARD_WHITE,
            CHESSBOARD_BLACK,
            MARIO_UNDERGROUND,
            BEDROCK,
            BLURRY,
            MINDFUCK,
            NICOLAS_CAGE,
            SOMEBODY_ONCE_TOLD_ME,
            BIKER_RED_NORTH, //Motorosok és csíkjaik:
            BIKER_RED_EAST,
            BIKER_RED_SOUTH,
            BIKER_RED_WEST,
            RED_LINE,
            BIKER_GREEN_NORTH,
            BIKER_GREEN_EAST,
            BIKER_GREEN_SOUTH,
            BIKER_GREEN_WEST,
            GREEN_LINE,
            BIKER_BLUE_NORTH,
            BIKER_BLUE_EAST,
            BIKER_BLUE_SOUTH,
            BIKER_BLUE_WEST,
            BLUE_LINE,
            BIKER_YELLOW_NORTH,
            BIKER_YELLOW_EAST,
            BIKER_YELLOW_SOUTH,
            BIKER_YELLOW_WEST,
            YELLOW_LINE,
            EXPLOSION;
    private final int TILE_SIZE;

    public GameBoard(Player player1, Player player2, GameState gameState) {
        setPreferredSize(new Dimension(500, 500));
        this.player1 = player1;
        this.player2 = player2;
        this.gameState = gameState;
        try {
            this.SYNTHWAVE = ResourceLoader.loadImage("resources/SYNTHWAVE.png");
            this.MATRIX = ResourceLoader.loadImage("resources/MATRIX.png");
            this.VAPORWAVE = ResourceLoader.loadImage("resources/VAPORWAVE.png");
            this.CHESSBOARD_WHITE = ResourceLoader.loadImage("resources/CHESSBOARD_WHITE.png");
            this.CHESSBOARD_BLACK = ResourceLoader.loadImage("resources/CHESSBOARD_BLACK.png");
            this.MARIO_UNDERGROUND = ResourceLoader.loadImage("resources/MARIO_UNDERGROUND.png");
            this.BEDROCK = ResourceLoader.loadImage("resources/BEDROCK.png");
            this.BLURRY = ResourceLoader.loadImage("resources/BLURRY.png");
            this.MINDFUCK = ResourceLoader.loadImage("resources/MINDFUCK.png");
            this.NICOLAS_CAGE = ResourceLoader.loadImage("resources/NICOLAS_CAGE.png");
            this.SOMEBODY_ONCE_TOLD_ME = ResourceLoader.loadImage("resources/SOMEBODY_ONCE_TOLD_ME.png");
            this.BIKER_RED_NORTH = ResourceLoader.loadImage("resources/BIKER_RED_NORTH.png");
            this.BIKER_RED_EAST = ResourceLoader.loadImage("resources/BIKER_RED_EAST.png");
            this.BIKER_RED_SOUTH = ResourceLoader.loadImage("resources/BIKER_RED_SOUTH.png");
            this.BIKER_RED_WEST = ResourceLoader.loadImage("resources/BIKER_RED_WEST.png");
            this.RED_LINE = ResourceLoader.loadImage("resources/RED_LINE.png");
            this.BIKER_GREEN_NORTH = ResourceLoader.loadImage("resources/BIKER_GREEN_NORTH.png");
            this.BIKER_GREEN_EAST = ResourceLoader.loadImage("resources/BIKER_GREEN_EAST.png");
            this.BIKER_GREEN_SOUTH = ResourceLoader.loadImage("resources/BIKER_GREEN_SOUTH.png");
            this.BIKER_GREEN_WEST = ResourceLoader.loadImage("resources/BIKER_GREEN_WEST.png");
            this.GREEN_LINE = ResourceLoader.loadImage("resources/GREEN_LINE.png");
            this.BIKER_BLUE_NORTH = ResourceLoader.loadImage("resources/BIKER_BLUE_NORTH.png");
            this.BIKER_BLUE_EAST = ResourceLoader.loadImage("resources/BIKER_BLUE_EAST.png");
            this.BIKER_BLUE_SOUTH = ResourceLoader.loadImage("resources/BIKER_BLUE_SOUTH.png");
            this.BIKER_BLUE_WEST = ResourceLoader.loadImage("resources/BIKER_BLUE_WEST.png");
            this.BLUE_LINE = ResourceLoader.loadImage("resources/BLUE_LINE.png");
            this.BIKER_YELLOW_NORTH = ResourceLoader.loadImage("resources/BIKER_YELLOW_NORTH.png");
            this.BIKER_YELLOW_EAST = ResourceLoader.loadImage("resources/BIKER_YELLOW_EAST.png");
            this.BIKER_YELLOW_SOUTH = ResourceLoader.loadImage("resources/BIKER_YELLOW_SOUTH.png");
            this.BIKER_YELLOW_WEST = ResourceLoader.loadImage("resources/BIKER_YELLOW_WEST.png");
            this.YELLOW_LINE = ResourceLoader.loadImage("resources/YELLOW_LINE.png");
            this.EXPLOSION = ResourceLoader.loadImage("resources/EXPLOSION.png");
            System.out.println("Sikeresen betöltöttük a sprite-okat!");
            System.out.println("");
        } catch (IOException e) {
            System.err.println("Valami nem működik a képek betöltésénél! (GameBoard.java)");
        }
        this.TILE_SIZE = 50;                                                    //64x64-es png képek. Egyelőre nem kell átméretezni.
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        System.out.println("Elkezdi kirajzolni a pályát.");
        if (gameState.isLoaded()) {
            Graphics2D graphics2d = (Graphics2D) graphics;
            Level level = gameState.level;
            int levelSize = gameState.LEVEL_SIZE;
            for (int y = 0; y < levelSize; y++) {
                for (int x = 0; x < levelSize; x++) {
                    Image currentImage = null;
                    Tile currentTile = gameState.getTile(y, x);
                    switch (currentTile) {
                        case PLAYER1:                                           //A bal felső sarokban lefelé nézzen.
                            switch (player1.color) {
                                case RED:
                                    switch (player1.getDirection()) {
                                        case NORTH:
                                            currentImage = BIKER_RED_NORTH;
                                            break;
                                        case EAST:
                                            currentImage = BIKER_RED_EAST;
                                            break;
                                        case SOUTH:
                                            currentImage = BIKER_RED_SOUTH;
                                            break;
                                        case WEST:
                                            currentImage = BIKER_RED_WEST;
                                            break;
                                    }
                                    break;
                                case GREEN:
                                    switch (player1.getDirection()) {
                                        case NORTH:
                                            currentImage = BIKER_GREEN_NORTH;
                                            break;
                                        case EAST:
                                            currentImage = BIKER_GREEN_EAST;
                                            break;
                                        case SOUTH:
                                            currentImage = BIKER_GREEN_SOUTH;
                                            break;
                                        case WEST:
                                            currentImage = BIKER_GREEN_WEST;
                                            break;
                                    }
                                    break;
                                case BLUE:
                                    switch (player1.getDirection()) {
                                        case NORTH:
                                            currentImage = BIKER_BLUE_NORTH;
                                            break;
                                        case EAST:
                                            currentImage = BIKER_BLUE_EAST;
                                            break;
                                        case SOUTH:
                                            currentImage = BIKER_BLUE_SOUTH;
                                            break;
                                        case WEST:
                                            currentImage = BIKER_BLUE_WEST;
                                            break;
                                    }
                                    break;
                                case YELLOW:
                                    switch (player1.getDirection()) {
                                        case NORTH:
                                            currentImage = BIKER_YELLOW_NORTH;
                                            break;
                                        case EAST:
                                            currentImage = BIKER_YELLOW_EAST;
                                            break;
                                        case SOUTH:
                                            currentImage = BIKER_YELLOW_SOUTH;
                                            break;
                                        case WEST:
                                            currentImage = BIKER_YELLOW_WEST;
                                            break;
                                    }
                                    break;
                                default:
                                    System.err.println("Az 1. játékost nem lehet kirajzolni");
                            }
                            break;
                        case PLAYER2:                                           //A jobb alsó sarokban felfelé nézzen.
                            switch (player2.color) {
                                case RED:
                                    switch (player2.getDirection()) {
                                        case NORTH:
                                            currentImage = BIKER_RED_NORTH;
                                            break;
                                        case EAST:
                                            currentImage = BIKER_RED_EAST;
                                            break;
                                        case SOUTH:
                                            currentImage = BIKER_RED_SOUTH;
                                            break;
                                        case WEST:
                                            currentImage = BIKER_RED_WEST;
                                            break;
                                    }
                                    break;
                                case GREEN:
                                    switch (player2.getDirection()) {
                                        case NORTH:
                                            currentImage = BIKER_GREEN_NORTH;
                                            break;
                                        case EAST:
                                            currentImage = BIKER_GREEN_EAST;
                                            break;
                                        case SOUTH:
                                            currentImage = BIKER_GREEN_SOUTH;
                                            break;
                                        case WEST:
                                            currentImage = BIKER_GREEN_WEST;
                                            break;
                                    }
                                    break;
                                case BLUE:
                                    switch (player2.getDirection()) {
                                        case NORTH:
                                            currentImage = BIKER_BLUE_NORTH;
                                            break;
                                        case EAST:
                                            currentImage = BIKER_BLUE_EAST;
                                            break;
                                        case SOUTH:
                                            currentImage = BIKER_BLUE_SOUTH;
                                            break;
                                        case WEST:
                                            currentImage = BIKER_BLUE_WEST;
                                            break;
                                    }
                                    break;
                                case YELLOW:
                                    switch (player2.getDirection()) {
                                        case NORTH:
                                            currentImage = BIKER_YELLOW_NORTH;
                                            break;
                                        case EAST:
                                            currentImage = BIKER_YELLOW_EAST;
                                            break;
                                        case SOUTH:
                                            currentImage = BIKER_YELLOW_SOUTH;
                                            break;
                                        case WEST:
                                            currentImage = BIKER_YELLOW_WEST;
                                            break;
                                    }
                                    break;
                                default:
                                    System.err.println("A 2. játékost nem lehet kirajzolni");
                            }
                            break;
                        case FLOOR:
                            switch (level) {
                                case SYNTHWAVE:
                                    currentImage = SYNTHWAVE;
                                    break;
                                case MATRIX:
                                    currentImage = MATRIX;
                                    break;
                                case VAPORWAVE:
                                    currentImage = VAPORWAVE;
                                    break;
                                case CHESSBOARD:
                                    if ((x + y) % 2 == 0) {
                                        currentImage = CHESSBOARD_WHITE;
                                        break;
                                    } else {
                                        currentImage = CHESSBOARD_BLACK;
                                        break;
                                    }
                                case MARIO_UNDERGROUND:
                                    currentImage = MARIO_UNDERGROUND;
                                    break;
                                case BEDROCK:
                                    currentImage = BEDROCK;
                                    break;
                                case BLURRY:
                                    currentImage = BLURRY;
                                    break;
                                case MINDFUCK:
                                    currentImage = MINDFUCK;
                                    break;
                                case NICOLAS_CAGE:
                                    currentImage = NICOLAS_CAGE;
                                    break;
                                case SOMEBODY_ONCE_TOLD_ME:
                                    currentImage = SOMEBODY_ONCE_TOLD_ME;
                                    break;
                                default:
                                    System.err.println("Az 1. játékost nem lehet kirajzolni");
                            }
                            break;
                        case RED_LINE:
                            currentImage = RED_LINE;
                            break;
                        case GREEN_LINE:
                            currentImage = GREEN_LINE;
                            break;
                        case BLUE_LINE:
                            currentImage = BLUE_LINE;
                            break;
                        case YELLOW_LINE:
                            currentImage = YELLOW_LINE;
                            break;
                        case EXPLOSION:
                            currentImage = EXPLOSION;
                            break;
                    }
                    graphics2d.drawImage(currentImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
            }
            System.out.println("Sikeresen kirajzoltuk a pályát!");
            System.out.println("");
        }
    }

    public void refresh() {
        if (gameState.isLoaded()) {
            setPreferredSize(new Dimension(500, 500));
            repaint();
        }
    }
}
