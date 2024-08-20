package pieces;

import java.util.ArrayList;
import java.util.List;

import position.Position;
import board.Board;

/**
 * Abstract class for: Pawn, King, Queen, Rook, Bishop, Knight
 */
public abstract class Piece {
  
  protected String color;
  protected boolean hasMoved = false;
  protected Position position;

  /**
   * Get: Piece's color.
   * @return String
   */
  public String getColor() { return this.color; }

  /**
   * Get: Piece's position.
   * @return Position
   */
  public Position getPosition() { return this.position; };

  /**
   * Set: hasMoved to 'moved' parameter.
   */
  public void hasMoved(Boolean moved) { this.hasMoved = moved; }

  /**
   * Get: hasMoved.
   * @return
   */
  public Boolean getHasMoved() { return this.hasMoved; }

  /**
   * Method: Returns list of possible moves for 'this' piece.
   * @param gameBoard Board object being played on.
   * @return List<Position>
   */
  public abstract List<Position> possibleMoves(Board gameBoard);
  
}
