
**Pay attention to the top of each java file. Especially the statements using "import" and "package"

Board Class
    private data members:
        private Piece[][] board = new Piece[8][8];
        private List<Piece> capturedPieces = new ArrayList<>();
    Constructor:
        public Board();
    Methods:
        private void initialize(); // initializes board
        public void movePiece(Piece piece, int newRow, int newCol) // Version1
        public void movePiece(Piece piece, String positionString) // Version2 can use "a4" or "d2"
        public void displayPieceInPosition(int rowNum, int colNum) // if whiteRook, displays wR
        public Piece getPieceInPosition(int rowNum, int colNum) // returns "Piece" object
        public void displayBoard(boolean displayForDevs) // test for yourself what happens when you put true or false as argument!
        public int[] helperPositionStringToIntCoordinateArray(String positionString) // "a4" = array{3, 0}

Piece Class
    private data members:
        private String color;
        private boolean hasMoved;
        private Position position;
    Constructor:
        NONE because this is an abstract class
    Methods:   
        public abstract String getColor(); // returns color of piece
        public abstract Position getPosition(); // returns "Position" object of piece

Pawn Class extends Piece
    private data members:
        private String color;
        private boolean hasMoved; // ChessRule: Pawn's first move allows moving forward two spots
        private Position position;
    Constructor:
        public Pawn(String color, int rowNum, int colNum) // Must include rowNum and colNum for position
        public String getColor(); // implemented here
        public Position getPosition(); // implemented here
        @Override
        public String toString(); // if you System.out.print('An object of type: Pawn') prints "bp" or "wp"

Queen Class extends Piece   // similar to Pawn and all other Pieces
    private data members:
        private String color;
        private Position position;
    Constructor:
        public Queen(String color, int rowNum, int colNum);
    Methods:
        public String getColor();
        public Position getPosition();
        public String toString(); // if you System.out.print('An object of type: Queen') prints "bQ" or "wQ"

... All other Pieces are smiliar as of now ...

Position Class
    private data members:
        private int x;
        private int y;
    Constructor: 
        public Position(int rowNum, int colNum); // initializes with coordinates
    Methods: 
        public void setPosition(int x, int y); // sets new position
        public int getRowNum(); // gets rowNum/x-value (i.e. board[x][])
        public int getColNum(); // gets colNum/y-value (i.e. board[][y])
        public String getPositionString(); // if x = 3 and y = 0, returns "a4"

ChessMain
    //Some sample code
    Board gameBoard = new Board(); // creates new board
    gameBoard.displayBoard(false); // display board
    Piece whiteQueen = gameBoard.getPieceInPosition(0, 3); // retrieves piece in position "d1"
    gameBoard.movePiece(whiteQueen, 3, 3); // moving white Queen to position "d4"
    gameBoard.movePiece(whiteQueen, "d8"); // moving white Queen to position "d8" to attack
    gameBoard.displayBoard(false); // display board





    