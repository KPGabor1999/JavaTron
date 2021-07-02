package model;

/**
 * A pálya belső állását reprezentáló osztály.
 *
 * @author KrazyXL
 */
public class GameState {

    public final Level level;
    public final int LEVEL_SIZE = 10;
    private final Tile[][] tileGrid;
    private boolean loaded;

    public GameState(Level level) {
        this.level = level;
        this.tileGrid = new Tile[LEVEL_SIZE][LEVEL_SIZE];

        System.out.println("Legenerált pálya:");
        for (int i = 0; i < LEVEL_SIZE; i++) {
            for (int j = 0; j < LEVEL_SIZE; j++) {
                if (i == 0 && j == 0) {
                    tileGrid[i][j] = Tile.PLAYER1;
                } else if (i == LEVEL_SIZE - 1 && j == LEVEL_SIZE - 1) {
                    tileGrid[i][j] = Tile.PLAYER2;
                } else {
                    tileGrid[i][j] = Tile.FLOOR;
                }
                System.out.print(tileGrid[i][j].type);
            }
            System.out.println("");
        }
        System.out.println("");

        this.loaded = true;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }

    public Tile getTile(int y, int x) {
        return tileGrid[y][x];
    }

    public void setTile(int y, int x, Tile tile) {
        tileGrid[y][x] = tile;
    }
}
