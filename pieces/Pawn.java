package pieces;

import java.util.ArrayList;
import java.util.List;

import position.Position;
import board.Board;

/**
 * SubClass for Pawn
 */
public class Pawn extends Piece {

    public Pawn(String color, int rowNum, int colNum){
        this.color = color;
        this.hasMoved = false;
        this.position = new Position(rowNum, colNum);
    }


    public List<Position> possibleMoves(Board gameBoard){
        List<Position> returnArray = new ArrayList<>();
        Position currentPos = this.position;
        Position tempPos; // just a holder for object of type position (makes for cleaner code)

        //for white pieces
        if(this.color == "white"){
            //not attacking moves
            tempPos = currentPos.getPositionBelowCurrent(1); // 1 space ahead
            if (tempPos.validPosition()){
                if (gameBoard.getPieceInPosition(tempPos) == null){
                    returnArray.add(tempPos);
                }
            }
            if (!hasMoved){                                             // if pawn's first move, 2 spaces ahead
                tempPos =  currentPos.getPositionBelowCurrent(2);
                if (tempPos.validPosition() && gameBoard.getPieceInPosition(currentPos.getPositionBelowCurrent(1)) == null){ // makes sure pawn can't jump over a piece
                    if (gameBoard.getPieceInPosition(tempPos) == null){
                        returnArray.add(tempPos);
                    } 
                }
            }
            //attacking moves
            tempPos = currentPos.getPositionBelowCurrent(1).getPositionLeftOfCurrent(1); // down 1 left 1
            if (tempPos.validPosition()){                                                  // if valid position
                if (gameBoard.getPieceInPosition(tempPos) != null){                       // if a piece is in position
                    if (gameBoard.getPieceInPosition(tempPos).getColor() != this.color){ // if its an enemy piece
                        returnArray.add(tempPos);
                    }
                }
                
            }
            tempPos = currentPos.getPositionBelowCurrent(1).getPositionRightOfCurrent(1); // down 1 right 1
            if (tempPos.validPosition()){                                                // if valid position
                if (gameBoard.getPieceInPosition(tempPos) != null){                     // if a piece is in position
                    if (gameBoard.getPieceInPosition(tempPos).getColor() != this.color){ // if its an enemy piece
                        returnArray.add(tempPos);
                    }
                }
            }
        }
        //for black pieces
        else{
            //not attacking moves
            tempPos = currentPos.getPositionAboveCurrent(1);    // 1 space ahead
            if (tempPos.validPosition()){
                if (gameBoard.getPieceInPosition(tempPos) == null){
                    returnArray.add(tempPos);
                }
            }
            if (!hasMoved){                                             // if pawn's first move, 2 spaces ahead
                tempPos = currentPos.getPositionAboveCurrent(2);
                if (tempPos.validPosition() && gameBoard.getPieceInPosition(currentPos.getPositionAboveCurrent(1)) == null){ // makes sure pawn can't jump over a piece
                    if (gameBoard.getPieceInPosition(tempPos) == null){
                        returnArray.add(tempPos);
                    }
                }
            }
            //attacking moves
            tempPos = currentPos.getPositionAboveCurrent(1).getPositionLeftOfCurrent(1); // up 1 left 1
            if (tempPos.validPosition()){
                if (gameBoard.getPieceInPosition(tempPos) != null){
                    if (gameBoard.getPieceInPosition(tempPos).getColor() != this.color){ // if its an enemy piece
                        returnArray.add(tempPos);
                    }
                }
            }
            tempPos = currentPos.getPositionAboveCurrent(1).getPositionRightOfCurrent(1); // up 1 right 1
            if (tempPos.validPosition()){
                if (gameBoard.getPieceInPosition(tempPos) != null){
                    if (gameBoard.getPieceInPosition(tempPos).getColor() != this.color){ // if its an enemy piece
                        returnArray.add(tempPos);
                    }
                }
            }
        }

        return returnArray;
    }

    @Override
    public String toString(){
        return color.charAt(0) + "p";  // if white... returns wp
    }


}
