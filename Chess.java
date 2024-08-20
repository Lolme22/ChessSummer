public class Chess {
    /**
     * Main method to start the Chess game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        //ConsoleChessGame game = new ConsoleChessGame();
        JavaSwingChessGame game = new JavaSwingChessGame();
        game.start();
    }
}
