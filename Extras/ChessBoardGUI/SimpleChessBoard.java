package Extras.ChessBoardGUI;
/**
 * THIS IS JUST A SIMPLE CHESS BOARD. PIECES CAN MOVE AROUND WITHOUT RESTRICTIONS AND NO GAME RULES ARE APPLIED.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimpleChessBoard {

    private static final int ROWS = 8;
    private static final int COLS = 8;
    private final JPanel[][] gameBoardSquares = new JPanel[ROWS][COLS];
    private JPanel selectedPiece = null;

    private static final String[] UNICODE_PIECES = {
        "\u2654", "\u2655", "\u2656", "\u2657", "\u2658", "\u2659",
        "\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F"
    };
    private final Color lightColor = new Color(240, 217, 181);
    private final Color darkColor = new Color(181, 136, 99);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleChessBoard().createAndShowGUI());
    }

    private void createAndShowGUI() {

        //Create JFrame to hold the Chess Game
        JFrame frame = new JFrame("Simple Game Board with Multiple Pieces");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(ROWS, COLS));
        frame.setSize(500, 500);
        //frame.setPreferredSize(new Dimension(400, 400));
        
    
        //Sets up board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JPanel panel = new JPanel();                                //Create new panel
                JLabel square = new JLabel(getPieceUnicode(i, j));          //Create new label and set to correct Unicode (Chess piece) 
                adjustPieceLabel(square);                                   //Adjust Chess Piece size (font size, font type...)
                panel.add(square);                                          //Add to panel
                panel.setBackground((i + j) % 2 == 0 ? lightColor : darkColor);
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleMouseClick(panel);
                    }
                });
                gameBoardSquares[i][j] = panel;                             //Place panel in gameBoardSquares 2D array
                frame.add(panel);                                           //Add panel to frame
            }
        }

        //setupGamePieces();

        frame.pack();
        frame.setVisible(true);
    }

    //May delete
    private void setupGamePieces() {
        gameBoardSquares[0][0].setBackground(Color.GREEN);
        gameBoardSquares[0][1].setBackground(Color.BLUE);
        gameBoardSquares[0][2].setBackground(Color.RED);
    }

    //Event Handler
    private void handleMouseClick(JPanel clickedPanel) {
        String panelLabel = "";

        //Retrieves the text on clickedPanel
        for (Component jc : clickedPanel.getComponents()){
            if (jc instanceof JLabel) {
                JLabel label = (JLabel) jc;
                panelLabel = label.getText();
            }
        }
        
        //If no piece has been selected AND if clickedPanel has a Chess Piece, then it becomes the selectedPiece
        if (selectedPiece == null) {
            if (panelLabel != "") {
                selectedPiece = clickedPanel;
                clickedPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
            }

        //If we have a selectedPiece AND clickedPanel doesn't have a Chess Piece, then selectedPiece's
        //Chess Piece moves to clickedPanel
        } else {
            //if (panelLabel == "") {
            //if ( !(selectedPiece.getUnicode() - panelLabel.getUnicode() < absoluteValue(6)) )
            //Psuedo: if (selectedPiece.color() != clickedPiece.color() OR clickedPiece == "" OR selectedPiece == clickedPiece)
                int[] oldPosition = findPanelPosition(selectedPiece);
                String gamePiece = "";
                
                for (Component jc : selectedPiece.getComponents()){
                    if (jc instanceof JLabel) {
                        JLabel label = (JLabel) jc;
                        gamePiece = label.getText();
                        label.setText("");
                    }
                }
                for (Component jc : clickedPanel.getComponents()){
                    if (jc instanceof JLabel) {
                        JLabel label = (JLabel) jc;
                        label.setText(gamePiece);
                        adjustPieceLabel(label);
                    }
                }
                selectedPiece.setBackground((oldPosition[0] + oldPosition[1]) % 2 == 0 ? lightColor : darkColor);
                selectedPiece.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                selectedPiece = null;
            //}
        }
    }

    //Find panel position
    private int[] findPanelPosition(JPanel panel) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (gameBoardSquares[i][j] == panel) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Not found
    }

    //Adjust Chess Piece size (font size, font type...)
    private void adjustPieceLabel(JLabel label){
        label.setFont(new Font("Serif", Font.BOLD, 32)); // Make the piece characters bigger
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
    }

    //Used for setting up pieces for a New Game
    private static String getPieceUnicode(int row, int col) {
        if (row == 0 || row == 7) {
            int offset = (row == 0) ? 0 : 6; // Determines white or black pieces
            switch (col) {
                case 0:
                case 7:
                    return UNICODE_PIECES[2 + offset]; // Rook
                case 1:
                case 6:
                    return UNICODE_PIECES[4 + offset]; // Knight
                case 2:
                case 5:
                    return UNICODE_PIECES[3 + offset]; // Bishop
                case 3:
                    return (row == 0) ? UNICODE_PIECES[1] : UNICODE_PIECES[7]; // Queen
                case 4:
                    return (row == 0) ? UNICODE_PIECES[0] : UNICODE_PIECES[6]; // King
            }
        } else if (row == 1 || row == 6) {
            return (row == 1) ? UNICODE_PIECES[5] : UNICODE_PIECES[11]; // Pawns
        }
        return ""; // Empty space for non-piece areas
    }
}

