package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import position.Position;

/**
 * SubClass for Queen
 */
public class Queen extends Piece{

    public Queen(String color, int rowNum, int colNum){
        this.color = color;
        this.position = new Position(rowNum, colNum);
    }

    public List<Position> possibleMoves(Board gameBoard){
        List<Position> returnArray = new ArrayList<>();
        List<Position> tempArray = new ArrayList<>();

        // retrieves Rook possibleMoves()
        returnArray = new Rook(this.color, position.getRowNum(), position.getColNum()).possibleMoves(gameBoard);
        // retrieves Bishop possibleMoves()
        tempArray = new Bishop(this.color, position.getRowNum(), position.getColNum()).possibleMoves(gameBoard);

        // combines both moveSets
        for (Position pos : tempArray){
            if (!returnArray.contains(pos)){
                returnArray.add(pos);
            }
        }

        return returnArray;
    }

    public String toString(){
        return color.charAt(0) + "Q";  // if white... returns wQ
    }
}
