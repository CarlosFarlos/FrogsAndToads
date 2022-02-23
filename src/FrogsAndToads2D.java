/**
 * Backend for the game of Frogs and Toads 2D.
 *
 * The game consists of a 5x5 gameboard with Frog game pieces occupying the left half of the board,
 * Toads occupying the right half of the board, and an empty tile at the center of the board. The
 * objective of the game is to move every Frog game piece into the positions originally occupied by
 * Toad game pieces, and every Toad game piece into the positions originally occupied by Frog game
 * pieces. Frogs can only move down and to the right, while Toads can only move up and to the left.
 * A game piece can "hop" over the opposing game piece to place itself into the empty space.
 *
 * @author Ivan Oquendo-Pagan
 */

public class FrogsAndToads2D {

    private static String[][] grid;
    private static final int ROWS = 5;
    private static final int COLS = 5;
    private static final String frog = "\u001b[31m" + "F" + "\u001b[0m";
    private static final String toad = "\u001b[33m" + "T" + "\u001b[0m";
    private final String EMPTY_TILE = "-";

    /**
     * Initializes a new game of Frogs and Toads
     */
    public FrogsAndToads2D(){
        grid = new String[ROWS][COLS];
        final int midpoint = grid.length / 2;
        grid[midpoint][midpoint] = EMPTY_TILE;

        // Fills the gameboard with game pieces in their respective starting positions, skipping the center tile.
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i == midpoint && j == midpoint){
                    continue;
                }
                if(i <= 2 && j <= 2 || i > 2 && j <= 1){
                    grid[i][j] = frog;
                } else {
                    grid[i][j] = toad;
                }
            }
        }
    }

    /**
     * Moves the game piece at the user specified position (row, column)
     * Invalid moves leave the game unchanged.
     */
    public void move(int r, int c){
        String tile = grid[r][c];
        int spaceRow = 0;
        int spaceCol = 0;

        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                if(grid[i][j].equals(EMPTY_TILE)){
                    spaceRow = i;
                    spaceCol = j;
                }
            }
        }

        // Movement instruction for a Frog game piece
        if(tile.equals(frog)) {
            if((Math.abs(r - spaceRow) == 2 && grid[r + 1][c].equals(toad))
                    || (Math.abs(r - spaceRow) == 1)){
                grid[spaceRow][spaceCol] = tile;
                grid[r][c] = EMPTY_TILE;
            }
            if((Math.abs(c - spaceCol) == 2 && grid[r][c + 1].equals(toad)
                    || (Math.abs(c - spaceCol) == 1))){
                grid[spaceRow][spaceCol] = tile;
                grid[r][c] = EMPTY_TILE;
            }
        }

        // Movement instruction for a Toad game piece
        if(tile.equals(toad)) {
            if((Math.abs(r - spaceRow) == 2 && grid[r - 1][c].equals(frog))
                    || (Math.abs(r - spaceRow) == 1)){
                grid[spaceRow][spaceCol] = tile;
                grid[r][c] = EMPTY_TILE;
            }
            if((Math.abs(c - spaceCol) == 2 && grid[r][c - 1].equals(frog)
                    || (Math.abs(c - spaceCol) == 1))){
                grid[spaceRow][spaceCol] = tile;
                grid[r][c] = EMPTY_TILE;
            }
        }
    }

    /**
     * Checks the current gamestate for valid moves. Return true if there remain any playable moves,
     * and false if not.
     */
    public boolean canMove(){
        int spaceRow = 0;
        int spaceCol = 0;

        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                if(grid[i][j].equals(EMPTY_TILE)){
                    spaceRow = i;
                    spaceCol = j;
                }
            }
        }

        // A move is valid if it is within 2 tiles of the empty space (not including diagonals) and meets the
        // following criteria
        for (int i = 0; i <= ROWS - 1; i++){
            for (int j = 0; j <= COLS - 1; j++){

                if(i == spaceRow && spaceCol - 1 <= COLS - 1 && spaceCol + 1 <= COLS - 1) {
                    // 1. A Frog or Toad game piece can move directly into the empty space from the east or west.
                    if (grid[i][spaceCol - 1].equals(frog) || grid[i][spaceCol + 1].equals(toad)){
                        return true;
                    }
                    // 2. A Frog or Toad two tiles away can hop over an opposing piece into the empty space
                    // from the east or west.
                    if (grid[i][spaceCol - 2].equals(frog) && grid[i][spaceCol-1].equals(toad) ||
                    grid[i][spaceCol + 2].equals(toad) && grid[i][spaceCol + 1].equals(frog)){
                        return true;
                    }
                }
                if(j == spaceCol && spaceRow - 1 <= ROWS - 1 && spaceRow + 1 <= ROWS - 1){
                    // 3. A Frog or Toad game piece can move directly into the empty space from the north or south.
                    if (grid[spaceRow - 1][j].equals(frog) || grid[spaceRow + 1][j].equals(toad)){
                        return true;
                    }
                    // 4. A Frog or Toad two tiles away can hop over an opposing piece into the empty space
                    // from the north or south.
                    if (grid[spaceRow - 2][j].equals(frog) && grid[spaceRow - 1][j].equals(toad) ||
                            grid[spaceRow + 2][j].equals(toad) && grid[spaceRow + 1][j].equals(frog)){
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /**
     * Checks the game to see if the board is in the winning configuration. Returns true if the user has won,
     * or false if they have lost and there are no more valid moves.
     */
    public boolean inWinningConfig() {
        final int midpoint = grid.length / 2;
        int frogCount = 0;
        int toadCount = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i != midpoint && j != midpoint) {
                    if (i <= midpoint && j <= midpoint && grid[i][j].equals(toad)
                            || i > midpoint && j <= midpoint - 1 && grid[i][j].equals(toad)) {
                        toadCount++;
                    } else {
                        frogCount++;
                    }
                }
            }
        }
        return frogCount == (ROWS * COLS) - 1 && toadCount == (ROWS * COLS) - 1;
    }

    /**
     * Returns the current game state.
     */
    @Override
    public String toString(){
        String gamestate = "  0  1  2  3  4 \n";
        int rowCounter = 0;
        for(String[] rows : grid){
            gamestate += rowCounter + " ";
            for(String cols : rows){
                gamestate += cols + "  ";
            }
            rowCounter++;
            gamestate += "\n";
        }

        return gamestate;
    }
}
