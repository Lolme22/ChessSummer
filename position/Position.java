package position;

/**
 * Represents a Position on Chess Board.
 */
public class Position {
    private int x;
    private int y;

/////////////////////////
// Constructors        //
/////////////////////////

    /**
     * Constructor: Initializes with rowNum and colNum integers.
     * @param rowNum Int: (0 - 7) for standard Chess Board however, can be any initialized with any integer.
     * @param colNum Int: (0 - 7) for standard Chess Board however, can be any initialized with any integer.
     */
    public Position(int rowNum, int colNum){
        this.x = rowNum;
        this.y = colNum;
    }

    /**
     * Constructor: Initializes a stringPosition for Chess (i.e. "a1", "d4").
     * @param stringPosition String: "a1" or "d4" are valid Chess Position Examples
     */
    public Position(String stringPosition){
        int rowNum;
        int colNum;

        switch(stringPosition.charAt(1)){
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

        switch(stringPosition.charAt(0)){
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
        this.x = rowNum;
        this.y = colNum;
    }

//////////////////////////
// Methods              //
//////////////////////////

    /**
     * Set: New position.
     * @param x Int: (0 - 7) for standard Chess Board however, can be any integer.
     * @param y Int: (0 - 7) for standard Chess Board however, can be any integer.
     */
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Bool: Validates 'this' position is a valid Chess position.
     * @return True if position is on the Board.
     */
    public Boolean validPosition(){
        if (this.x >= 0 && this.x < 8){
            if(this.y >= 0 && this.y < 8){
                return true;
            }
        }
        return false;
    }

    ////////////////////
    // Getter Methods //
    ////////////////////

    /**
     * Get: Position after traveling numSpaces below 'this' position.
     * @param numSpaces Number of spaces traveling.
     * @return Position
     */
    public Position getPositionBelowCurrent(int numSpaces){
        Position returnPos = new Position(x+numSpaces, y);
        return returnPos;
    }
    /**
     * Get: Position after traveling numSpaces above 'this' position.
     * @param numSpaces Number of spaces traveling.
     * @return Position
     */
    public Position getPositionAboveCurrent(int numSpaces){
        Position returnPos = new Position(x-numSpaces, y);
        return returnPos;
    }
    /**
     * Get: Position after traveling numSpaces left of 'this' position.
     * @param numSpaces Number of spaces traveling.
     * @return Position
     */
    public Position getPositionLeftOfCurrent(int numSpaces){
        Position returnPos = new Position(x, y-numSpaces);
        return returnPos;
    }
    /**
     * Get: Position after traveling numSpaces right of 'this' position.
     * @param numSpaces Number of spaces traveling.
     * @return Position
     */
    public Position getPositionRightOfCurrent(int numSpaces){
        Position returnPos = new Position(x, y+numSpaces);
        return returnPos;
    }

    /**
     * Get: Row number.
     * @return Int
     */
    public int getRowNum(){
        return this.x;
    }

    /**
     * Get: Column number.
     * @return Int
     */
    public int getColNum(){
        return this.y;
    }

    //////////////////////
    // Override Methods //
    //////////////////////

    /**
     * @Override equals(Obj object) from Object Class.
     * @param position Position object being tested for equivalence.
     * @return Bool: True if variables x and y are equivalent in both objects.
     */
    public Boolean equals(Position position){
        if (this.x == position.x && this.y == position.y){
            return true;
        }
        return false;
    }
    
    /**
     * @Override toString() from Object Class.
     */
    public String toString(){
        String returnString = "";
        
        switch(this.y){
            case 0: returnString += 'a'; break;
            case 1: returnString += 'b'; break;
            case 2: returnString += 'c'; break;
            case 3: returnString += 'd'; break;
            case 4: returnString += 'e'; break;
            case 5: returnString += 'f'; break;
            case 6: returnString += 'g'; break;
            case 7: returnString += 'h'; break;
            default: returnString += 'a'; break;
        }

        returnString += (this.x + 1);
        return returnString;
    }
}
