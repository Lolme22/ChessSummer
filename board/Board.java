package board;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import pieces.*;
import player.Player;
import position.Position;

/**
 * Represents a Chess Board
 */
public class Board {

    /**
     * Physical board representation as a 2-D array (i.e. matrix).
     */
    private Piece[][] board = new Piece[8][8];
    
    /**
     * List<Piece> of captured pieces.
     */
    private List<Piece> capturedPieces = new ArrayList<>();

    /**
     * Scanner object.
     */
    private Scanner scanner;

    /**
     * Constructor to initialize starting board.
     */
    public Board(){
        this.Initialize();
    }

    /**
     * Initializes starting board.
     */
    private void Initialize(){
        for (int x = 0; x<board.length; x++){
            for (int y = 0; y<board[0].length; y++){
                board[x][y] = null; // all spots on board are empty
            }
        }

        //Now we add pieces on the board
        
        //Pawns
        for(int x=0; x<8; x++){
            board[1][x] = new Pawn("white", 1, x);
        }

        for(int x=0; x<8; x++){
            board[6][x] = new Pawn("black", 6, x);
        }        
        
        //Rooks
        board[0][0] = new Rook("white", 0, 0);
        board[0][7] = new Rook("white", 0, 7);
        board[7][7] = new Rook("black", 7, 7);
        board[7][0] = new Rook("black", 7, 0);

        //Knights
        board[0][1] = new Knight("white", 0, 1);
        board[0][6] = new Knight("white", 0, 6);
        board[7][6] = new Knight("black", 7, 6);
        board[7][1] = new Knight("black", 7, 1);

        //Bishops
        board[0][2] = new Bishop("white", 0, 2);
        board[0][5] = new Bishop("white", 0, 5);
        board[7][2] = new Bishop("black", 7, 2);
        board[7][5] = new Bishop("black", 7, 5);

        //Queens
        board[0][3] = new Queen("white", 0, 3);
        board[7][3] = new Queen("black", 7, 3);

        //Kings
        board[0][4] = new King("white", 0, 4);
        board[7][4] = new King("black", 7, 4);
    }

//////////////////////////////
// Methods                  //
//////////////////////////////

    /**
     * Bool: Checks whether a move if valid based on the movingPiece's possibleMoves() method.
     * @param oldPositionString The current position of the movingPiece.
     * @param newPositionString The ending position.
     * @return True if validMove.
     */
    public boolean isValidMove(String oldPositionString, String newPositionString){
        Boolean inMoveSet = false;
        Boolean causesCheck = false;
        Piece erasedContents = null;
        Piece movingPiece = getPieceInPosition(oldPositionString);
        List<Position> possibleMoves = movingPiece.possibleMoves(this);
        
        //Checks if newPositionString is a possibleMove for this movingPiece
        for (Position pos : possibleMoves){
            if (pos.toString().equals(newPositionString)){
                inMoveSet = true;
            }
        }
        
        //Checks if this move causes a check by executing the move
        if (inMoveSet){
            
            
            Boolean pieceHasMoved = movingPiece.getHasMoved(); // Retains information on whether its the movingPiece's first move
            erasedContents = this.getPieceInPosition(newPositionString); // Retains hashCode for the piece being captured
            
            // Execute move and check if it causes inCheck
            this.movePiece(oldPositionString, newPositionString);
            if (this.inCheck(movingPiece.getColor())){
                causesCheck = true;
            }

            //Undo the move
            this.movePiece(newPositionString, oldPositionString);
            Position tempPos = new Position(newPositionString);
            board[tempPos.getRowNum()][tempPos.getColNum()] = erasedContents;
            
            //Reinstates pawns' abilty to move 2 spots in initial move
            if(pieceHasMoved == false){
                movingPiece.hasMoved(false);
            }
        }

        //If move is in this piece's moveSet AND it doesn't cause a check, it is Valid.
        if (inMoveSet == true && causesCheck == false){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Bool: Check if a player is in check based on playerColor
     * @param colorOfPlayer The color of the player we're checking.
     * @return Bool: True if is in check.
     */
    private Boolean inCheck(String colorOfPlayer){
        Position kingPosition = kingPosition(colorOfPlayer);
        String enemyColor = (colorOfPlayer.equals("white")) ? "black" : "white";
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board[i][j] != null && board[i][j].getColor().equals(enemyColor)){
                    List<Position> possibleMovesForPiece = board[i][j].possibleMoves(this);
                    for (Position pos : possibleMovesForPiece){
                        if (pos.equals(kingPosition)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Position of the king based on playerColor.
     * @param colorOfKing The color of the King we're looking for.
     * @return Position: king's Position on the Board.
     */
    private Position kingPosition(String colorOfKing){
        Position returnPosition = new Position(-1, -1);
        Piece king = new King(colorOfKing, -1, -1); //Dummy king piece used to locate actual king
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board[i][j] != null && board[i][j].toString().equals(king.toString())){ // "wK" == "wK" (whiteKing)
                    returnPosition.setPosition(i, j);                                       // "bK" == "bK" (blackKing)
                } 
            }
        }
        return returnPosition;
        
    }

    /**
     * Checks if the a player is inCheckMate based on their color.
     * @param playerColor The colorPlayer we're checking.
     * @return Bool: True if player is in CheckMate.
     */
    public Boolean inCheckMate(String playerColor){

        // We need just 1 validMove to ensure we're not inCheckMate
        Player thisPlayer = new Player(playerColor, this);
        List<Piece> thisPlayerPieces = thisPlayer.getAvailablePieces(this);
        for (Piece p : thisPlayerPieces){
            List<Position> possibleMoves = p.possibleMoves(this);
            for (Position pos : possibleMoves){
                if (isValidMove(p.getPosition().toString(), pos.toString())){
                    return false;
                }
            }
        }
        return true;
    }

    ////////////////////////////
    // Pawn Promotion Methods //
    ////////////////////////////

    /**
     * Bool: Checks if the piece is a pawn ready for promotion.
     * @param piece The piece being checked for pawn promotion.
     * @return Bool
     */
    public Boolean pawnPromotion(Piece piece){
        return piece instanceof Pawn && ((piece.getColor().equals("white") && piece.getPosition().getRowNum() == 7) || (piece.getColor().equals("black") && piece.getPosition().getRowNum() == 0));
    }

    /**
     * Prompts the user to choose a piece to promote their pawn into.
     * @return
     */
    public String promptNewPieceType(){
        scanner = new Scanner(System.in);
        System.out.println("Your pawn is ready for promotion! What piece would you like to promote it to? (Queen/Rook/Bishop/Knight)");
        String newPieceType = scanner.nextLine();
        return newPieceType;
    }
    
    /**
     * Promotes the pawn into a different Chess Piece
     * @param positionString The position the piece is in.
     * @param newPieceType The type that the pawn will promote into.
     */
    public void promotePawn(String positionString, String newPieceType){
        int[] coordinates = helperPositionStringToIntCoordinateArray(positionString);
        int row = coordinates[0];
        int col = coordinates[1];
        Piece piece = board[row][col];
            Piece newPiece;
            switch (newPieceType) {
                case "Queen":
                    newPiece = new Queen(piece.getColor(), row, col);
                    break;
                case "Rook":
                    newPiece = new Rook(piece.getColor(), row, col);
                    break;
                case "Bishop":
                    newPiece = new Bishop(piece.getColor(), row, col);
                    break;
                case "Knight":
                    newPiece = new Knight(piece.getColor(), row, col);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid piece for promotion");
            }
            board[row][col] = newPiece;
    }

    ///////////////////////
    // movePiece Methods //
    ///////////////////////

    /**
     * Method: Moves a piece to a new position. Can move ontop of an empty space or enemy piece. (Version 1)
     * @param oldPositionString The String that represents the position the movingPiece is on.
     * @param newPositionString The String that represents the new position (i.e. "a1", "c4").
     */
    public void movePiece(String oldPositionString, String newPositionString){
        
        //Retrieving x-y coordinates from oldPositionString and newPositionString
        int[] oldCoordinates = new int[2];
        oldCoordinates = helperPositionStringToIntCoordinateArray(oldPositionString);
        int oldRow = oldCoordinates[0];
        int oldCol = oldCoordinates[1];
        Piece movingPiece = board[oldRow][oldCol];

        int[] newCoordinates = new int[2];
        newCoordinates = helperPositionStringToIntCoordinateArray(newPositionString);
        int newRow = newCoordinates[0];
        int newCol = newCoordinates[1];

        //If there is already an existing piece in the new position
        if (board[newRow][newCol] != null){
            Piece blockingPiece = board[newRow][newCol];
            //If friendly piece {NOTE: Friendly fire code most likely omitted once possibleMoves() is implemented}
            if (movingPiece.getColor() == blockingPiece.getColor()){
                System.out.println("Friendly Fire!!! Try again.\n");
                return;
            }
            //If enemy piece
            else{
                capturedPieces.add(blockingPiece);
                // blackPlayer.lostPiece(blockingPiece) {potential code from Player class}
                System.out.println("\n" + movingPiece + " attacks " + blockingPiece + "!!!\n");
                board[newRow][newCol] = null;
            }
        }

        this.board[newRow][newCol] = movingPiece;
        
        this.board[oldRow][oldCol] = null;
        movingPiece.getPosition().setPosition(newRow, newCol);
        movingPiece.hasMoved(true); // for pawns
    }

    /**
     * Method: Moves a piece to a new position. Can move ontop of an empty space or enemy piece. (Version 2)
     * @param piece The movingPiece.
     * @param newRow The row for the new position.
     * @param newCol The column for the new position.
     */
    public void movePiece(Piece piece, int newRow, int newCol){
        
        //If there is already an existing piece in the new position
        if (board[newRow][newCol] != null){
            Piece blockingPiece = board[newRow][newCol];
            //If friendly piece {NOTE: Friendly fire code most likely omitted possibleMoves() is implemented}
            if (piece.getColor() == blockingPiece.getColor()){
                System.out.println("Friendly Fire!!! Try again.\n");
                return;
            }
            //If enemy piece {NOTE: will have to add code for Player's list of available pieces HERE!!!}
            else{
                capturedPieces.add(blockingPiece);
                // blackPlayer.lostPiece(blockingPiece) {potential code from Player class}
                System.out.println(piece + " attacks " + blockingPiece + "!!!\n");
                board[newRow][newCol] = null;
            }
        }

        this.board[newRow][newCol] = piece;
        
        int oldRow = piece.getPosition().getRowNum();
        int oldCol = piece.getPosition().getColNum();
        this.board[oldRow][oldCol] = null;
        piece.getPosition().setPosition(newRow, newCol);
        piece.hasMoved(true); // for pawns
    }

    /**
     * Method: Moves a piece to a new position. Can move ontop of an empty space or enemy piece. (Version 3)
     * @param piece The movingPiece.
     * @param positionString The String that represents the new position (i.e. "a1", "c4").
     */
    public void movePiece(Piece piece, String positionString){
        
        //Retrieving x-y coordinates from String positionString
        int[] numericalCoordinates = new int[2];
        numericalCoordinates = helperPositionStringToIntCoordinateArray(positionString);
        int newRow = numericalCoordinates[0];
        int newCol = numericalCoordinates[1];

        //If there is already an existing piece in the new position
        if (board[newRow][newCol] != null){
            Piece blockingPiece = board[newRow][newCol];
            //If friendly piece {NOTE: Friendly fire code most likely omitted possibleMoves() is implemented}
            if (piece.getColor() == blockingPiece.getColor()){
                System.out.println("Friendly Fire!!! Try again.\n");
                return;
            }
            //If enemy piece {NOTE: will have to add code for Player's list of available pieces HERE!!!}
            else{
                capturedPieces.add(blockingPiece);
                // blackPlayer.lostPiece(blockingPiece) {potential code from Player class}
                System.out.println("\n" + piece + " attacks " + blockingPiece + "!!!\n");
                board[newRow][newCol] = null;
            }
        }

        this.board[newRow][newCol] = piece;
        
        int oldRow = piece.getPosition().getRowNum();
        int oldCol = piece.getPosition().getColNum();
        this.board[oldRow][oldCol] = null;
        piece.getPosition().setPosition(newRow, newCol);
        piece.hasMoved(true); // for pawns

    }

    

    ////////////////////
    // Getter Methods //
    ////////////////////

    /**
     * Get: List of capturedPieces.
     * @return List<Piece>
     */
    public List<Piece> getCapturedPieces(){
        return this.capturedPieces;
    }
        ////////////////////////////////
        // getPieceInPosition Methods //
        ////////////////////////////////

    /**
     * Get: Piece in specific position.
     * @param rowNum Row number for position.
     * @param colNum Column number for position.
     * @return Piece
     */
    public Piece getPieceInPosition(int rowNum, int colNum){
        return this.board[rowNum][colNum];
    }

    /**
     * Get: Piece in specific position.
     * @param position Position object we're using to find a spot on the board.
     * @return Piece
     */
    public Piece getPieceInPosition(Position position){
        return this.board[position.getRowNum()][position.getColNum()];
    }

    /**
     * Get: Piece in specific position.
     * @param positionString The String that represents the position (i.e. "a1", "c4").
     * @return Piece
     */
    public Piece getPieceInPosition(String positionString){
        int[] coordinates = new int[2];
        coordinates = helperPositionStringToIntCoordinateArray(positionString);
        return this.board[coordinates[0]][coordinates[1]];
    }

    /////////////////////
    // Display Methods //
    /////////////////////

    /**
     * Display Piece in Position.
     * @param rowNum Row number for position.
     * @param colNum Column number for position.
     */
    public void displayPieceInPosition(int rowNum, int colNum){
        Piece piece = getPieceInPosition(rowNum, colNum);
        System.out.println(piece);
    }


    /**
     * Display board.
     * @param displayForDevs If true, rows = (0-7), columns = (0-7).
     */
    public void displayBoard(boolean displayForDevs){
        String colIndex = "  a  b  c  d  e  f  g  h \n";
        if (displayForDevs == true) colIndex = "  0  1  2  3  4  5  6  7 \n";
        System.out.print(colIndex);
        int rowNum = 0;
        for (Piece[] pieces : board){
            int colNum = 0;
            String rowIndex = rowNum+1 + " ";
            if (displayForDevs == true) rowIndex = rowNum + " ";
            System.out.print(rowIndex);
            for (Piece piece : pieces){
                if (piece == null) { 
                    if (rowNum % 2 == 0 ){ // if row# is even
                        if (colNum % 2 == 0 ){// if col# is also even
                            System.out.print("## ");  
                        }
                        else{
                            System.out.print("   ");
                        }
                    }
                    if (rowNum % 2 == 1) { // if row# is odd
                        if (colNum % 2 == 1){ // if col# is also odd
                            System.out.print("## ");
                        }
                        else{
                            System.out.print("   ");
                        }
                    }
                }
                else{
                    System.out.print(piece + " ");
                }
                colNum++;
                
            }
            System.out.println();
            rowNum++;
        }
    }

    ////////////////////
    // Helper Methods //
    ////////////////////

    /**
     * HelperMethod: Converts valid Chess Move ("d4") into an int_array = {3, 3}.
     * @param positionString The string being converted.
     * @return Int[2]
     */
    private int[] helperPositionStringToIntCoordinateArray(String positionString){
        int[] returnArray = new int[2];
        int rowNum;
        int colNum;

        switch(positionString.charAt(1)){
            case '1': rowNum = 0; break;
            case '2': rowNum = 1; break;
            case '3': rowNum = 2; break;
            case '4': rowNum = 3; break;
            case '5': rowNum = 4; break;
            case '6': rowNum = 5; break;
            case '7': rowNum = 6; break;
            case '8': rowNum = 7; break;
            default: rowNum = 0; break;
        }

        switch(positionString.charAt(0)){
            case 'a': colNum = 0; break;
            case 'b': colNum = 1; break;
            case 'c': colNum = 2; break;
            case 'd': colNum = 3; break;
            case 'e': colNum = 4; break;
            case 'f': colNum = 5; break;
            case 'g': colNum = 6; break;
            case 'h': colNum = 7; break;
            default: colNum = 0; break;
        }
        returnArray[0] = rowNum;
        returnArray[1] = colNum;
        return returnArray;
    }

}
