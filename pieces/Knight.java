package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import position.Position;

/**
 * SubClass for Knight
 */
public class Knight extends Piece {

    public Knight(String color, int rowNum, int colNum){
        this.color = color;
        this.position = new Position(rowNum, colNum);
    }

    public List<Position> possibleMoves(Board gameBoard){
        List<Position> returnArray = new ArrayList<>();
        Position currentPos = this.position;
        Position tempPos;

        int [][] moves = {
            {2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}
        };

        for(int [] move : moves){
            tempPos = new Position(currentPos.getRowNum() + move[0], currentPos.getColNum()+ move[1]);
                if(tempPos.validPosition()){
                    if(gameBoard.getPieceInPosition(tempPos) == null){// if position is empty
                        returnArray.add(tempPos); //add to list
                    }else if(!gameBoard.getPieceInPosition(tempPos).getColor().equals(this.color)){
                        returnArray.add(tempPos); //enemy piece & move is possible
                    }
             }
        }
        return returnArray;
    }

    public String toString(){
        return color.charAt(0) + "N";  // if white... returns wN
    }
}
