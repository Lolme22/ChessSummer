package player;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import pieces.*;
import board.*;

/**
 * Represents a Player in Chess.
 */
public class Player{
    private String color;
    private List<Piece> availablePieces;
    private Scanner scanner;
    private Boolean resigned;
    
    /**
     * Constructor: Initializes player's color and pieces.
     * @param color String: Either white or black for Chess.
     * @param gameBoard Board object being played on.
     */
    public Player(String color, Board gameBoard){
        this.color = color;
        this.availablePieces = new ArrayList<>();
        this.resigned = false;

        //Initializes pieces
        this.updateAvailablePieces(gameBoard);
    }

//////////////////////////////
// Methods                  //
//////////////////////////////

    ////////////////////
    // Getter Methods //
    ////////////////////

    /**
     * Get: Player's color.
     * @return String
     */
    public String getColor(){
        return this.color;
    }

    /**
     * Get: Player's available pieces.
     * @param gameBoard Board object being played on.
     * @return List<Piece>
     */
    public List<Piece> getAvailablePieces(Board gameBoard){
        this.updateAvailablePieces(gameBoard);
        return this.availablePieces;
    }

    public Boolean getResignation(){
        return this.resigned;
    }
    
    //////////////////////
    // makeMove Method  //
    //////////////////////

    /**
     * Method: Guarantees player will make a move. Handles invalidInput and invalidMoves. Repeats until move is made.
     * @param gameBoard Board object being played on.
     */
    public void makeMove(Board gameBoard){
        Boolean validMove = false;
        Boolean validInput = false;
        String[] moveArray = new String[2];

        scanner = new Scanner(System.in);

        while(!validMove){              // exit with valid move; if invalid move, validate new input again
            validInput = false;
            while(!validInput){         // exit with valid input ("a2 a5")
                validInput = true;

                String validColumn = "abcdefgh";
                String validRow = "12345678";
                System.out.println(this.color + " enter your move: ");
                String playerMove = scanner.nextLine();

                if (playerMove.equals("resign")){
                    System.out.println(this.color + " player has resigned.\n");
                    resigned = true;
                    return;
                }

                moveArray = playerMove.split(" ");
                if (!validColumn.contains(Character.toString(moveArray[0].charAt(0)))){
                    validInput = false;
                }
                else if (!validRow.contains(Character.toString(moveArray[0].charAt(1)))){
                    validInput = false;
                }
                else if (!validColumn.contains(Character.toString(moveArray[1].charAt(0)))){
                    validInput = false;
                }
                else if(!validRow.contains(Character.toString(moveArray[1].charAt(1)))){
                    validInput = false;
                }
                
                if (!validInput){
                    System.out.println("Invalid input!");
                }
            }

            //Validates Move
            if (this.color.equals(gameBoard.getPieceInPosition(moveArray[0]).getColor())){
                if (gameBoard.isValidMove(moveArray[0], moveArray[1])){
                    gameBoard.movePiece(moveArray[0], moveArray[1]);
                    validMove = true;
                }
                else{
                    System.out.println("Invalid move! Try again.");
                }
            }
            else{
                System.out.println("Invalid move! Try again.");
            }
        }

        //Handles Pawn Promotion
        if(gameBoard.pawnPromotion(gameBoard.getPieceInPosition(moveArray[1]))){
            String newPieceType = gameBoard.promptNewPieceType();
            gameBoard.promotePawn(moveArray[1], newPieceType);
        }
          
    } 

    /////////////////////
    // Display Methods //
    /////////////////////
    
    /**
     * Display: Displays player's available pieces.
     * @param gameBoard The board the player is playing on.
     */
    public void displayAvailablePieces(Board gameBoard){
        List<Piece> listOfPieces = this.getAvailablePieces(gameBoard);
        System.out.print(this.color + " Available Pieces: [");
        for (Piece piece : listOfPieces){
            System.out.print(piece + " ");
        }
        System.out.print("]");
    }

    ////////////////////
    // Helper Methods //
    ////////////////////

    /**
     * Helper: Updates player's available pieces by reading the board.
     * @param gameBoard The board being read.
     */
    private void updateAvailablePieces(Board gameBoard){
        List<Piece> newList = new ArrayList<>();
        for (int rowNum = 0; rowNum < 8; rowNum++){
            for (int colNum = 0; colNum < 8; colNum++){
                Piece selectedPiece = gameBoard.getPieceInPosition(rowNum, colNum);
                if (selectedPiece != null && this.color == selectedPiece.getColor()){
                    newList.add(selectedPiece);
                }
            }
        }
        this.availablePieces = newList;
    }
}