package view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import persistence.HighScore;

/**
 * Saját táblázatmodell a dicsőségtáblához.
 *
 * @author KrazyXL
 */
public class LeaderboardModel extends AbstractTableModel {

    private ArrayList<HighScore> topHighscores;             //csak a legjobb 10-et jelenítjük meg

    public LeaderboardModel(ArrayList<HighScore> allHighscores) {
        topHighscores = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            if (i + 1 <= allHighscores.size()) {
                topHighscores.add(allHighscores.get(i));
            } else {
                break;
            }
        }
    }

    @Override
    public int getRowCount() {
        return topHighscores.size();    //ahány elem van a toplistán
    }

    @Override
    public int getColumnCount() {
        return 2;                      //név és pontszám
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HighScore currentHighScore = topHighscores.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return currentHighScore.getName();
            case 1:
                return currentHighScore.getScore();
            default:
                System.err.println("Nem létező index(" + columnIndex + ") a táblázat létrehozásakor.");
                return null;
        }
    }

}
