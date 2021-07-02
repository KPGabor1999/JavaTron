package model;

/**
 * Játéktábla mezőinek lehetséges típusai.
 *
 * @author KrazyXL
 */
public enum Tile {
    PLAYER1('1'),
    PLAYER2('2'),
    RED_LINE('R'),
    GREEN_LINE('G'),
    BLUE_LINE('B'),
    YELLOW_LINE('Y'),
    FLOOR('#'),
    EXPLOSION('*');

    public final char type;

    Tile(char type) {
        this.type = type;
    }
}
