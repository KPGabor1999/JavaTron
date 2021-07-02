package model;

/**
 * Játékos pozíciója a pályán/táblán.
 *
 * @author KrazyXL
 */
public class Position {

    private int XCoord;
    private int YCoord;

    public Position(int XCoord, int YCoord) {
        this.XCoord = XCoord;
        this.YCoord = YCoord;
    }

    public int getXCoord() {
        return XCoord;
    }

    public int getYCoord() {
        return YCoord;
    }

    /**
     * Játékos koordinátáinak frissítése.
     *
     * @param direction: Az irány, amelybe lépni kívánunk.
     */
    public void move(PlayerDirection direction) {
        switch (direction) {
            case NORTH:
                this.YCoord--;
                break;
            case EAST:
                this.XCoord++;
                break;
            case SOUTH:
                this.YCoord++;
                break;
            case WEST:
                this.XCoord--;
                break;
        }
    }
}
