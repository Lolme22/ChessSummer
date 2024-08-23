/**
 * THIS HOLDS THE MECHANICS AND GRAPHIC USER INTERFACE USING JAVASWING FOR A REGULAR CHESS GAME.
 */

import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import board.Board;
import pieces.Piece;
import player.Player;
import position.Position;

/**
 * JavaSwing Chess Game
 */
public class JavaSwingChessGame{

    //Create JFrame to hold the Chess Game
    private final JFrame mainFrame;
    private JLabel top_text;

    private final Board gameBoard;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;

    private static final int ROWS = 8;
    private static final int COLS = 8;
    private final JPanel[][] gameBoardSquares = new JPanel[ROWS][COLS];

    private JPanel selectedPanel = null;                // The panel with the selectedPiece
    private Piece selectedPiece = null;                 // The piece being move
    private Piece previousPiece = null;                 // Tracks the last piece moved
    private List<Position> movementSquares = null;      // Used to show what possible moves a selectedPiece has!!!

    
    
    private static final String[] UNICODE_PIECES = {
        "\u2654", "\u2655", "\u2656", "\u2657", "\u2658", "\u2659",
        "\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F"
    };
    private final Color lightColor = new Color(240, 217, 181);
    private final Color darkColor = new Color(181, 136, 99);

    

    /**
     * Initializes gaming components.
     */
    public JavaSwingChessGame(){

        gameBoard = new Board();
        whitePlayer = new Player("white", gameBoard);
        blackPlayer = new Player("black", gameBoard);
        currentPlayer = whitePlayer;


        mainFrame = createMainFrame();
        JPanel boardPanel = createBoardPanel();
        JPanel topPanel = createTopPanel();

        mainFrame.add(boardPanel, BorderLayout.CENTER);
        mainFrame.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Starts and runs the game (Still working on properly ending the game).
     */
    public void start(){

        makeFrameVisible(mainFrame);

    }


    /**
     * Creates the Main Frame where the program takes place
     * @return JFrame
     */
    private JFrame createMainFrame(){

        // Create Frame
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    /**
     * Creates the board as a panel
     * @return JPanel
     */
    private JPanel createBoardPanel(){
        
        // Creates and Adjust layout and size
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLS));
        boardPanel.setPreferredSize(new Dimension(400, 400));

        //Sets up board by filling up boardPanel with 64 JPanels with JLabels
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JPanel panel = new JPanel();                                //Create new panel
                JLabel square = new JLabel(getPieceUnicode(i, j));          //Create new label and set to correct Unicode (Chess piece) 
                adjustPieceLabel(square);                                   //Adjust Chess Piece size (font size, font type...)
                panel.add(square);                                          //Add to panel
                panel.setBackground((i + j) % 2 == 0 ? lightColor : darkColor);
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Adding mouse listener
                final int finalI = i;
                final int finalJ = j;
                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleMouseClick(finalI, finalJ, panel);
                    }
                });

                // Add final product to 
                gameBoardSquares[i][j] = panel;                             //Place panel in gameBoardSquares 2D array
                boardPanel.add(panel);                                           //Add panel to boardPanel
            }
        }

        return boardPanel;

    }

    /**
     * Creates the panel at the top displaying text to the user
     * @return JPanel
     */
    private JPanel createTopPanel(){
        JPanel topPanel = new JPanel();
        top_text = new JLabel(currentPlayer.getColor() + " turn!");
        topPanel.add(top_text);

        return topPanel;
    }

    /**
     * Creates the Pawn Promotion Frame whenver a pawn gets promoted
     * @return JFrame
     */
    private JFrame createPawnPromotionFrame(){

        // Create Frame
        JFrame pawnPromotionFrame = new JFrame("pawnPromotionFrame!");
        pawnPromotionFrame.setLayout(new BorderLayout());

        // Create Panel and Add to Frame
        JPanel pawnPromotionPanel = createPawnPromotionPanel();
        pawnPromotionFrame.add(pawnPromotionPanel, BorderLayout.CENTER);
        return pawnPromotionFrame; 

    }

    /**
     * Creates the Pawn Promotion Panel that will be in the Pawn Promotion Frame
     * It consists of 4 buttons that are different promotion choices
     * @return JPanel
     */
    private JPanel createPawnPromotionPanel(){

        // Creates and Adjust layout and size
        JPanel pawnPromotionPanel = new JPanel();
        pawnPromotionPanel.setLayout(new GridLayout(1, 4));
        pawnPromotionPanel.setPreferredSize(new Dimension(325, 100));

        // Add buttons for promotion choices
        pawnPromotionPanel.add(makePawnPromoButton("Queen"));
        pawnPromotionPanel.add(makePawnPromoButton("Rook"));
        pawnPromotionPanel.add(makePawnPromoButton("Bishop"));
        pawnPromotionPanel.add(makePawnPromoButton("Knight"));

        return pawnPromotionPanel;
    }


    /**
     * Makes buttons for Pawn Promotion Panel
     * @param choice
     * @return
     */
    private JButton makePawnPromoButton(String choice){
        JButton button = new JButton(choice);
        ActionListener listener = new pawnPromoButtonListener(choice);
        button.addActionListener(listener);
        return button;
    }

    /**
     * Button listener for Pawn Promotion Buttons
     */
    class pawnPromoButtonListener implements ActionListener {
        private String choice;

        public pawnPromoButtonListener(String aChoice){
            choice = aChoice;
        }

        public void actionPerformed(ActionEvent event){

            // Promote pawn on gameBoard
            gameBoard.promotePawn(previousPiece.getPosition().toString(), choice);

            // Update boardPanel
            String piece_unicode = convertPieceToUnicode(choice, previousPiece.getColor());
            Position previousPos = previousPiece.getPosition();
            int x = previousPos.getRowNum();
            int y = previousPos.getColNum();

            // Grab JLabel from Panel
            JLabel label = (JLabel)gameBoardSquares[x][y].getComponent(0);
            label.setText(piece_unicode);

        }
    }

    /**
     * Event Handler (will clean up later, but seems to work so far)
     * @param row The row number of clicked panel in gameBoardSquares.
     * @param col The column number of clicked panel in gameBoardSquares.
     * @param clickedPanel The clickedPanel.
     */
    private void handleMouseClick(int row, int col, JPanel clickedPanel) {
        Piece clickedPiece = null;

        //Retrieves the piece on the clickedPanel if there is one and sets it to clickedPiece.
        if (gameBoard.getPieceInPosition(row, col) != null){
            clickedPiece = gameBoard.getPieceInPosition(row, col);
        }
        
        
        //If there is NO selectedPanel THEN if clickedPanel has a Chess Piece, then it becomes the selectedPanel.
        if (selectedPanel == null ) {
            if (clickedPiece != null && clickedPiece.getColor().equals(currentPlayer.getColor())){
                selectedPanel = clickedPanel;
                selectedPiece = clickedPiece;
                clickedPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3, true));
                movementSquares = clickedPiece.possibleMoves(gameBoard);
                for (Position pos : movementSquares){
                    gameBoardSquares[pos.getRowNum()][pos.getColNum()].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3, true));
                }
            }
        } 
        
        //If there IS a selectedPanel AND the clickedPanel results in a valid Chess move, then place 
        //   the contents of selectedPanel onto clickedPanel, otherwise, if clickedPanel is not a valid
        //   Chess move, then deselect the selectedPanel. 
        else {

            Position oldPos =  selectedPiece.getPosition();
            Position newPos = new Position(row, col);
            
            // Assures we HAVE NOT double clicked that same panel
            if(selectedPanel != clickedPanel){

                // Validates move
                if (gameBoard.isValidMove(oldPos.toString(), newPos.toString())){
                    gameBoard.movePiece(oldPos.toString(), newPos.toString());
                    String gamePiece;
                    JLabel panelLabel;

                    // Retrieves unicode from selectedPanel and leaves selectedPanel blank
                    panelLabel = (JLabel)selectedPanel.getComponent(0);
                    gamePiece = panelLabel.getText();
                    panelLabel.setText("");

                    // Replaces unicode on clickedPanel with gamePiece
                    panelLabel = (JLabel)clickedPanel.getComponent(0);
                    panelLabel.setText(gamePiece);
                    adjustPieceLabel(panelLabel);

                    // Checks for Pawn Promotion Case
                    if (gameBoard.pawnPromotion(selectedPiece)){
                        previousPiece = selectedPiece;
                        JFrame pawnPromotionFrame = createPawnPromotionFrame();
                        makeFrameVisible(pawnPromotionFrame);
                    }

                    // Creates text in case of Checkmate
                    String checkmate_text = "CheckMate! " + currentPlayer.getColor() + " WINS!!!";
                    switchPlayer();
                    
                    //Output to top Panel
                    if (gameBoard.inCheckMate(currentPlayer.getColor())){
                        top_text.setText(checkmate_text);
                    }
                    else{
                        top_text.setText(currentPlayer.getColor() + " turn!");
                    }
                    
                }
            }

            //Reset selectedPanel AND movementSquares (also panels) to default panels.
            selectedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            for (Position pos : movementSquares){
                gameBoardSquares[pos.getRowNum()][pos.getColNum()].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
            selectedPanel = null;
            selectedPiece = null;
            movementSquares = null;
            
        }
    }

    /**
     * Displays the frame to the user
     * @param frame
     */
    private void makeFrameVisible(JFrame frame){
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * Switches current player.
     */
    public void switchPlayer(){
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }

    /**
     * Bool: Checks for player resignation. (inProgress)
     * @return Bool
     */
    public Boolean checkResignation(){
        if (whitePlayer.getResignation() == true || blackPlayer.getResignation() == true){
            return true;
        }
        return false;
    }

    /**
     * Method: Find panel position.
     * @param panel Panel being searched for.
     * @return int[] = {row, col}.
     */
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

    /**
     * Adjust Chess Piece size (font size, font type...)
     * @param label Text that will be readjusted.
     */
    private void adjustPieceLabel(JLabel label){
        label.setFont(new Font("Serif", Font.BOLD, 32)); // Make the piece characters bigger
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
    }

    /**
     * Used for setting up pieces for a New Game
     * @param row The row number on Board.
     * @param col The column number on Board.
     * @return String: unicode for specific Chess Piece.
     */
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

    /**
     * Converts a piece using a name like "Queen" and color like "white" into its proper unicode.
     * @param pieceName The name of the piece capitalized.
     * @param pieceColor The color all lowercase.
     * @return String: unicode for Chess Piece.
     */
    private static String convertPieceToUnicode(String pieceName, String pieceColor){
        int offset = (pieceColor.equals("white")) ? 0 : 6; // Determines white or black pieces
        switch (pieceName){
            case "Queen":
                return UNICODE_PIECES[1 + offset];
            case "Bishop":
                return UNICODE_PIECES[3 + offset];
            case "Rook":
                return UNICODE_PIECES[2 + offset];
            case "Knight":
                return UNICODE_PIECES[4 + offset];
            case "King":
                return UNICODE_PIECES[0 + offset];
            case "Pawn":
                return UNICODE_PIECES[5 + offset];
        }
        return "";
    }


}
