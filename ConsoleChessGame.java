/**
 * THIS HOLDS THE MECHANICS AND USES THE CONSOLE FOR A REGULAR CHESS GAME (inProgress).
 */

import java.util.*;

import board.Board;
import pieces.Piece;
import player.Player;
import position.Position;

/**
 * Console Game of Chess
 */
public class ConsoleChessGame{

    private Board gameBoard;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentPlayer;

    /**
     * Initializes gaming components.
     */
    public ConsoleChessGame(){
        gameBoard = new Board();
        whitePlayer = new Player("white", gameBoard);
        blackPlayer = new Player("black", gameBoard);
        currentPlayer = blackPlayer;
    }

    /**
     * Starts and runs the game (Still working on properly ending the game).
     */
    public void start(){
        System.out.println("\nWelcome to a Console Game of Chess");
        System.out.println("  Type your moves as such 'a2 a4'");
        System.out.println("  To resign type 'resign'\n");

        Piece p1 = gameBoard.getPieceInPosition("a2");  // pawn
        Piece p2 = gameBoard.getPieceInPosition("d1");  // queen


        System.out.println(p1 + ", " + p2);
        System.out.println(p1.getPosition() + ", " + p2.getPosition());

        gameBoard.movePiece("a2", "a3");
        gameBoard.movePiece("d1", "d5");

        p1 = gameBoard.getPieceInPosition("a3");
        p2 = gameBoard.getPieceInPosition("d5");

        System.out.println(p1.getPosition() + ", " + p2.getPosition());
        

        displayBoard();
        //displayBoardPlus();

        while(true){

            if (inCheckMate()){
                break;
            }

            currentPlayer.makeMove(gameBoard);

            if (checkResignation()){
                break;
            }

            switchPlayer();
            displayBoard();
            //displayBoardPlus();
        }
    }

    /**
     * Switches current player.
     */
    public void switchPlayer(){
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }

    /**
     * Bool: Checks for player resignation.
     * @return Bool
     */
    public Boolean checkResignation(){
        if (whitePlayer.getResignation() == true){
            System.out.println("Black Player Wins!\n");
            return true;
        }
        if (blackPlayer.getResignation() == true){
            System.out.println("White Player Wins!\n");
            return true;
        }
        return false;
    }

    public Boolean inCheckMate(){
        if (gameBoard.inCheckMate(currentPlayer.getColor())){
            switchPlayer();
            System.out.println("CheckMate! " + currentPlayer.getColor() + " WINS!!!");
            return true;
        }
        System.out.println("At least 1 valid move found");
        return false;
    }

    /**
     * Display gameBoard.
     */
    public void displayBoard(){
        gameBoard.displayBoard(false);
    }

    /**
     * Display gameBoard with extra information.
     */
    public void displayBoardPlus(){
        gameBoard.displayBoard(false); //input false or true and see what it prints!!!
        List<Piece> capturedList = gameBoard.getCapturedPieces();
        System.out.print("Caputred List: [");
        for (Piece piece : capturedList){
            System.out.print(piece + " ");
        }
        System.out.println("]");
        Player whitePlayer = new Player("white", gameBoard);
        Player blackPlayer = new Player("black", gameBoard);
        whitePlayer.displayAvailablePieces(gameBoard); System.out.println();
        blackPlayer.displayAvailablePieces(gameBoard); System.out.println(); System.out.println();
    }


}