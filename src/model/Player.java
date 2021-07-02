package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import static model.PlayerDirection.*;
import resources.ResourceLoader;
import static model.Tile.*;         //Enumokat mindig statikusan importálj!

/**
 * Játékos karaktereket reprezentáló osztály.
 *
 * @author KrazyXL
 */
public class Player {

    public final int number;
    public final String name;
    public final PlayerColor color;
    private PlayerDirection direction;
    private Position position;
    private boolean alive;
    private GameState gameState;

    public Player(int number, String playerName, PlayerColor playerColor, GameState gameState) {
        this.number = number;
        this.name = playerName;
        this.color = playerColor;
        this.direction = (this.number == 1 ? PlayerDirection.SOUTH : PlayerDirection.NORTH);
        this.position = (this.number == 1 ? new Position(0, 0) : new Position(9, 9));
        this.alive = true;
        this.gameState = gameState;
        System.out.println("A " + this.number + ". játékos kezdőkoordinátái: (" + this.position.getYCoord() + "," + this.position.getXCoord() + ")");
        System.out.println("");
    }

    public PlayerDirection getDirection() {
        return direction;
    }

    public void setDirection(PlayerDirection direction) {
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isAlive() {
        return alive;
    }

    /**
     * Játékos mozgatása a pályán (egy mezővel arrébb).
     */
    public void move() {
        switch (this.direction) {
            case NORTH:
                makeAMove(NORTH);
                break;
            case EAST:
                makeAMove(EAST);
                break;
            case SOUTH:
                makeAMove(SOUTH);
                break;
            case WEST:
                makeAMove(WEST);
                break;
            default:
                System.err.println("Nem létező irányba próbálsz meg lépni!(" + this.direction + ")");
        }

        //Logoljuk a játéktábla belső állapotát
        Tile[][] tileGrid = gameState.getTileGrid();
        System.out.println("Pálya állapota:");
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = 0; j < tileGrid.length; j++) {
                System.out.print(tileGrid[i][j].type);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Játékos mozgatásának belső művelete (kerüljük a kódismétlést).
     *
     * @param direction: Az irány, amelybe lépni kívánunk.
     */
    private void makeAMove(PlayerDirection direction) {
        //Számontartjuk a játékos jelenlegi pozícióját
        int currentY = this.position.getYCoord();
        int currentX = this.position.getXCoord();
        //És a meglépni kívánt mezőjét
        int nextY = 0;      //Csak default értékek.
        int nextX = 0;
        switch (direction) {
            case NORTH:
                nextY = this.position.getYCoord() - 1;
                nextX = this.position.getXCoord();
                break;
            case EAST:
                nextY = this.position.getYCoord();
                nextX = this.position.getXCoord() + 1;
                break;
            case SOUTH:
                nextY = this.position.getYCoord() + 1;
                nextX = this.position.getXCoord();
                break;
            case WEST:
                nextY = this.position.getYCoord();
                nextX = this.position.getXCoord() - 1;
                break;
        }

        //Falnak ütközést már korábban lekezeljük
        if (nextY < 0 || nextY == gameState.LEVEL_SIZE || nextX < 0 || nextX == gameState.LEVEL_SIZE) {
            this.alive = false;
            this.gameState.setTile(currentY, currentX, EXPLOSION);
            playCrashSound();
            return;
        }

        Tile nextTile = this.gameState.getTile(nextY, nextX);

        //Ütközések kezelése: fal, csík, másik játékos.
        if (nextTile == FLOOR) {
            System.out.println(this.number + ". játékos "
                    + (direction == NORTH ? "északra" : (direction == EAST ? "keletre" : (direction == SOUTH ? "délre" : (direction == WEST ? "nyugatra" : "HIBA"))))
                    + " lép. Új helye: (" + nextY + "," + nextX + ")");
            //A jelenelegi mezőre a játékos színének megfelelő csíkot rajzol.
            switch (this.color) {
                case RED:
                    this.gameState.setTile(currentY, currentX, RED_LINE);
                    break;                        //Nem referencia, külön setter kell?
                case GREEN:
                    this.gameState.setTile(currentY, currentX, GREEN_LINE);
                    break;                    //  ->külön setTile(y,x,Tile) metódus
                case BLUE:
                    this.gameState.setTile(currentY, currentX, BLUE_LINE);
                    break;
                case YELLOW:
                    this.gameState.setTile(currentY, currentX, YELLOW_LINE);
                    break;
                default:
                    System.err.println("Valami baj van a játékos színével! (Player.java 47-es sor)");
            }
            //A következő mezőre átteszi a játékost (elsőt vagy másodikat?).
            switch (this.number) {
                case 1:
                    this.gameState.setTile(nextY, nextX, PLAYER1);
                    break;
                case 2:
                    this.gameState.setTile(nextY, nextX, PLAYER2);
                    break;
                default:
                    System.err.println("A játékos sorszáma nagyobb kettőnél!");
            }
            //Ne felejtsd el frissíteni a játékos koordinátáit sem.
            this.position.move(direction);
        } else {
            this.alive = false;
            this.gameState.setTile(currentY, currentX, EXPLOSION);
        }
    }

    /**
     * Lejátssza a robbanás hangot ütkzéskor.
     */
    public void playCrashSound(){
        try{
            Clip explosionSound = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/resources/explosion.wav"));
            explosionSound.open(inputStream);
            explosionSound.start();
        } catch (LineUnavailableException e){
            System.err.println("Error: Line Unavailable.");
        } catch (UnsupportedAudioFileException e){
            System.err.println("Error: Audio file format not supported.");
        } catch (IOException e){
            e.printStackTrace();
            System.err.println("Error: IOException");
        }
        
    }
}
