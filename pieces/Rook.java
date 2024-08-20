package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import position.Position;

/**
 * SubClass for Rook
 */
public class Rook extends Piece{

    public Rook(String color, int rowNum, int colNum){
        this.color = color;
        this.position = new Position(rowNum, colNum);
        this.hasMoved = false;
    }

    public List<Position> possibleMoves(Board gameBoard){
        List<Position> returnArray = new ArrayList<>();
        Position currentPos = this.position;
        Position tempPos; // just a holder for object of type position (makes for cleaner code)
        Boolean reachedBorder = false;
        Boolean reachedFriendly = false;
        Boolean reachedEnemy = false;
        int numSpaces = 1;

        while(!reachedBorder && !reachedFriendly && !reachedEnemy){     // going north direction
            tempPos = currentPos.getPositionAboveCurrent(numSpaces);
            if (tempPos.validPosition()){
                if (gameBoard.getPieceInPosition(tempPos) == null){     // if position is empty (must check if null FIRST because next else if statement attempts to use .getColor())
                    returnArray.add(tempPos);                           // add to list
                }
                else if (gameBoard.getPieceInPosition(tempPos).getColor() == this.color){    // if friendly piece
                    reachedFriendly = true;                                             // its not a possible position to move to
                }
                else{                                                               // if enemy piece
                    reachedEnemy = true;                                            // its a possible position to move to
                    returnArray.add(tempPos);
                }
            }
            else{                                                                       // if not valid Position, reached border
                reachedBorder = true;
            }
            numSpaces++;
        }

        // reset helper values
        reachedBorder = false; reachedFriendly = false; reachedEnemy = false; numSpaces = 1;

        while(!reachedBorder && !reachedFriendly && !reachedEnemy){     // going east direction
            tempPos = currentPos.getPositionRightOfCurrent(numSpaces);
            if (tempPos.validPosition()){
                if (gameBoard.getPieceInPosition(tempPos) == null){     // if position is empty (must check if null FIRST because next else if statement attempts to use .getColor())
                    returnArray.add(tempPos);                           // add to list
                }
                else if (gameBoard.getPieceInPosition(tempPos).getColor() == this.color){    // if friendly piece
                    reachedFriendly = true;                                             // its not a possible position to move to
                }
                else{                                                               // if enemy piece
                    reachedEnemy = true;                                            // its a possible position to move to
                    returnArray.add(tempPos);
                }
            }
            else{                                                                       // if not valid Position, reached border
                reachedBorder = true;
            }
            numSpaces++;
        }

        // reset helper values
        reachedBorder = false; reachedFriendly = false; reachedEnemy = false; numSpaces = 1;

        while(!reachedBorder && !reachedFriendly && !reachedEnemy){     // going south direction
            tempPos = currentPos.getPositionBelowCurrent(numSpaces);
            if (tempPos.validPosition()){
                if (gameBoard.getPieceInPosition(tempPos) == null){     // if position is empty (must check if null FIRST because next else if statement attempts to use .getColor())
                    returnArray.add(tempPos);                           // add to list
                }
                else if (gameBoard.getPieceInPosition(tempPos).getColor() == this.color){    // if friendly piece
                    reachedFriendly = true;                                             // its not a possible position to move to
                }
                else{                                                               // if enemy piece
                    reachedEnemy = true;                                            // its a possible position to move to
                    returnArray.add(tempPos);
                }
            }
            else{                                                                       // if not valid Position, reached border
                reachedBorder = true;
            }
            numSpaces++;
        }

        // reset helper values
        reachedBorder = false; reachedFriendly = false; reachedEnemy = false; numSpaces = 1;

        while(!reachedBorder && !reachedFriendly && !reachedEnemy){     // going east direction
            tempPos = currentPos.getPositionLeftOfCurrent(numSpaces);
            if (tempPos.validPosition()){
                if (gameBoard.getPieceInPosition(tempPos) == null){     // if position is empty (must check if null FIRST because next else if statement attempts to use .getColor())
                    returnArray.add(tempPos);                           // add to list
                }
                else if (gameBoard.getPieceInPosition(tempPos).getColor() == this.color){    // if friendly piece
                    reachedFriendly = true;                                             // its not a possible position to move to
                }
                else{                                                               // if enemy piece
                    reachedEnemy = true;                                            // its a possible position to move to
                    returnArray.add(tempPos);
                }
            }
            else{                                                                       // if not valid Position, reached border
                reachedBorder = true;
            }
            numSpaces++;
        }


        return returnArray;
    }

    public String toString(){
        return color.charAt(0) + "R";  // if white... returns wR
    }
}
