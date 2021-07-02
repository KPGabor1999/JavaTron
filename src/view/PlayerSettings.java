package view;

import java.awt.*;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.CENTER;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;
import model.Level;
import model.PlayerColor;

/**
 * A játékbeállításokért felelős párbeszédablak.
 *
 * @author KrazyXL
 */
public class PlayerSettings extends JDialog implements ActionListener {

    //Pályaválasztó létrehozása
    JPanel levelSelect = new JPanel();
    JLabel levelSelectLabel = new JLabel("Arena:");
    JComboBox<Level> levels = new JComboBox<>();

    //Játékos beállítások felirat létrehozása
    JPanel playerSettings = new JPanel();
    JLabel playerSettingsLabel = new JLabel("Player names ans colors:");

    //1. játékos beállítások létrehozása
    JPanel player1Settings = new JPanel();
    JLabel player1Label = new JLabel("Player 1 (Upper left corner, WASD):");
    ButtonGroup player1Color = new ButtonGroup();
    ArrayList<JRadioButton> player1ColorOptions = new ArrayList<>();
    JTextField player1Name = new JTextField(20);
    JRadioButton red1 = new JRadioButton("Red", true);
    JRadioButton green1 = new JRadioButton("Green");
    JRadioButton blue1 = new JRadioButton("Blue");
    JRadioButton yellow1 = new JRadioButton("Yellow");

    ///2. játékos beállítások létrehozása
    JPanel player2Settings = new JPanel();
    JLabel player2Label = new JLabel("Player 2 (Lower right corner, Arrows):");
    JTextField player2Name = new JTextField(20);
    ButtonGroup player2Color = new ButtonGroup();
    ArrayList<JRadioButton> player2ColorOptions = new ArrayList<>();
    JRadioButton red2 = new JRadioButton("Red", true);
    JRadioButton green2 = new JRadioButton("Green");
    JRadioButton blue2 = new JRadioButton("Blue");
    JRadioButton yellow2 = new JRadioButton("Yellow");

    //OK és Mégse gombok létrehozása
    JPanel buttonsPanel = new JPanel();
    JButton OKButton = new JButton("OK");
    JButton CancelButton = new JButton("Cancel");

    //Hiba szövegmező létrehozása
    JPanel settingsLogPanel = new JPanel();
    JTextArea settingsLog = new JTextArea("Setup players!" + System.lineSeparator(), 4, 50);

    //Párbeszédablak összerakása
    public PlayerSettings(Frame owner, String title) {
        //Alapvető ablakbeállítások
        super(owner, title);
        setSize(770, 300);
        setResizable(false);
        setLocation(topX(), topY());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(Y_AXIS));

        //Pályaválasztó panel összerakása
        levelSelect.setLayout(new FlowLayout(CENTER));
        levelSelect.add(levelSelectLabel);
        levels.addItem(Level.SYNTHWAVE);
        levels.addItem(Level.MATRIX);
        levels.addItem(Level.VAPORWAVE);
        levels.addItem(Level.CHESSBOARD);
        levels.addItem(Level.MARIO_UNDERGROUND);
        levels.addItem(Level.BEDROCK);
        levels.addItem(Level.BLURRY);
        levels.addItem(Level.MINDFUCK);
        levels.addItem(Level.NICOLAS_CAGE);
        levels.addItem(Level.SOMEBODY_ONCE_TOLD_ME);
        levelSelect.add(levels);

        //Felirat panel összerakása
        playerSettings.add(playerSettingsLabel);

        //Felső logisztikaikai szervezőpanel összerakása (erőltetett sortörés a pályaválasztő és a felirat között)
        JPanel upperLogisticalPanel = new JPanel();                             //Sortörés erőltetése segédpanellel.
        upperLogisticalPanel.setLayout(new BorderLayout());
        upperLogisticalPanel.add(levelSelect, NORTH);
        upperLogisticalPanel.add(playerSettings, SOUTH);
        add(upperLogisticalPanel);

        //1. játékos beállítások összerakása
        player1Settings.setLayout(new FlowLayout(X_AXIS));
        player1Settings.add(player1Label);
        player1Settings.add(player1Name);
        player1Color.add(red1);
        player1ColorOptions.add(red1);
        player1Settings.add(red1);
        player1Color.add(green1);
        player1ColorOptions.add(green1);
        player1Settings.add(green1);
        player1Color.add(blue1);
        player1ColorOptions.add(blue1);
        player1Settings.add(blue1);
        player1Color.add(yellow1);
        player1ColorOptions.add(yellow1);
        player1Settings.add(yellow1);
        add(player1Settings);

        //2. játékos beállítások összerakása
        player2Settings.setLayout(new FlowLayout(X_AXIS));
        player2Settings.add(player2Label);
        player2Settings.add(player2Name);
        player2Color.add(red2);
        player2ColorOptions.add(red2);
        player2Settings.add(red2);
        player2Color.add(green2);
        player2ColorOptions.add(green2);
        player2Settings.add(green2);
        player2Color.add(blue2);
        player2ColorOptions.add(blue2);
        player2Settings.add(blue2);
        player2Color.add(yellow2);
        player2ColorOptions.add(yellow2);
        player2Settings.add(yellow2);
        add(player2Settings);

        //OK és Mégse gombok panel összerakása
        buttonsPanel.setLayout(new FlowLayout(CENTER));
        buttonsPanel.add(OKButton);
        buttonsPanel.add(CancelButton);
        buttonsPanel.setPreferredSize(new Dimension(150, 40));                  //Vagy ez, vagy két JPanel legyen egy másik közös panelen, aminek BorderLayout-ja van.

        //OK és mégse gombok eseményfigyelése és kezelése (lásd lejjebb)
        OKButton.addActionListener(this);
        CancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();                                                      //jelenlegi ablak bezárása
            }

        });

        //Validálási hiba kiíró konzol összerakása
        settingsLog.setEditable(false);
        settingsLogPanel.add(settingsLog);

        JPanel lowerLogisticalPanel = new JPanel();                             //Alsó logisztikai panel: Nem zárja középre a gombokat.
        lowerLogisticalPanel.setLayout(new BorderLayout());
        lowerLogisticalPanel.add(buttonsPanel, NORTH);
        lowerLogisticalPanel.add(settingsLogPanel, SOUTH);
        add(lowerLogisticalPanel);
    }

    /**
     * Az OK gomb eseménykezelését maga a párbeszédablak végzi.
     * Validáljuka megadott beállításokat, és ha helyesek, beállítjuk őket.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //A validált adatokat a szülőnek adjuk át
        GameWindow parentWindow = (GameWindow) this.getParent();

        //A kiválasztott pálya átadása (nem kell validálni)
        parentWindow.selectedLevel = (Level) levels.getSelectedItem();

        //Játékosok által megadott nevek és színek validálása
        String givenPlayer1Name = player1Name.getText();                        //Sorra kiválasztjuk a beállított értékeket
        String givenPlayer2Name = player2Name.getText();

        PlayerColor givenPlayer1Color = PlayerColor.NONE;
        for (JRadioButton currentButton : player1ColorOptions) {
            if (currentButton.isSelected()) {
                if(currentButton.getText().equals("Red")){
                    givenPlayer1Color = PlayerColor.RED;
                } else if(currentButton.getText().equals("Green")){
                    givenPlayer1Color = PlayerColor.GREEN;
                } else if(currentButton.getText().equals("Blue")){
                    givenPlayer1Color = PlayerColor.BLUE;
                } else if(currentButton.getText().equals("Yellow")){
                    givenPlayer1Color = PlayerColor.YELLOW;
                } else {
                    System.err.println("Ilyen szín nem létezik: " + currentButton.getText());
                }
            }
        }

        PlayerColor givenPlayer2Color = PlayerColor.NONE;
        for (JRadioButton currentButton : player2ColorOptions) {
            if (currentButton.isSelected()) {
                if(currentButton.getText().equals("Red")){
                    givenPlayer2Color = PlayerColor.RED;
                } else if(currentButton.getText().equals("Green")){
                    givenPlayer2Color = PlayerColor.GREEN;
                } else if(currentButton.getText().equals("Blue")){
                    givenPlayer2Color = PlayerColor.BLUE;
                } else if(currentButton.getText().equals("Yellow")){
                    givenPlayer2Color = PlayerColor.YELLOW;
                } else {
                    System.err.println("Ilyen szín nem létezik: " + currentButton.getText());
                }
            }
        }
        
        System.out.println("Az 1. játékos színe: " + givenPlayer1Color);
        System.out.println("Az 2. játékos színe: " + givenPlayer2Color);

        settingsLog.setForeground(Color.RED);                                   //Az esetleges hibaüzenetek színe piros

        //Megadott játékosnevek és színek validálása és átadása
        if (givenPlayer1Name.trim().length() > 0
                && givenPlayer2Name.trim().length() > 0
                && !givenPlayer1Name.equals(givenPlayer2Name)
                && givenPlayer1Color != givenPlayer2Color
                && !givenPlayer1Name.contains(";")
                && !givenPlayer2Name.contains(";")) {
            parentWindow.player1Name = givenPlayer1Name;
            parentWindow.player2Name = givenPlayer2Name;
            parentWindow.player1Color = givenPlayer1Color;
            parentWindow.player2Color = givenPlayer2Color;
            dispose();
        } else {
            settingsLog.setText("");
            if (givenPlayer1Name.trim().length() == 0) {
                settingsLog.append("Error: Player 1 has no name." + System.lineSeparator());
            }
            if (givenPlayer2Name.trim().length() == 0) {
                settingsLog.append("Error: Player 2 has no name." + System.lineSeparator());
            }
            if (givenPlayer1Name.equals(givenPlayer2Name)) {
                settingsLog.append("Error: Both players have the same name." + System.lineSeparator());
            }
            if (givenPlayer1Color == givenPlayer2Color) {
                settingsLog.append("Error: Both players have the same color." + System.lineSeparator());
            }
            if (givenPlayer1Name.contains(";") || givenPlayer2Name.contains(";")) {
                settingsLog.append("Error: Player names can't contain the ; character." + System.lineSeparator());
            }
            return;     //Hiba esetén nem töltjük be a játékot.
        }

        System.out.println("Beállítottuk a játékot: ");
        System.out.println("Kiválasztott téma: " + parentWindow.selectedLevel);
        System.out.println("1. játékos neve: " + parentWindow.player1Name);
        System.out.println("1. játékos színe: " + parentWindow.player1Color);
        System.out.println("2. játékos neve: " + parentWindow.player2Name);
        System.out.println("2. játékos színe: " + parentWindow.player2Color);
        System.out.println("");

        parentWindow.startGame();
    }

    private int topX() {
        return (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
    }

    private int topY() {
        return (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
    }

}
