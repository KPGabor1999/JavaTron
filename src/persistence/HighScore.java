package persistence;

/**
 * Egy dicsőségtábla rekord reprezentálása osztályként.
 *
 * @author bli
 */
public class HighScore implements Comparable{

    private final String name;
    private int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    
    public void increaseScore(){
        score++;
    }
    
    @Override
    public int compareTo(Object o) {
        HighScore other = (HighScore) o;
        
        if(other.score > this.score){
            return 1;
        } else if (other.score == this.score){
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return name + ";" + score;
    }

}
