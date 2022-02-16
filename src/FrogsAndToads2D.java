public class FrogsAndToads2D {


    private static String[][] grid;
    private static final int ROWS = 5;
    private static final int COLS = 5;
    private static final String frog = "\u001b[31m" + "F" + "\u001b[0m";
    private static final String toad = "\u001b[33m" + "T" + "\u001b[0m";
    private final String EMPTY_TILE = "-";

    public FrogsAndToads2D(){
        grid = new String[ROWS][COLS];
        final int midpoint = grid.length / 2;
        grid[midpoint][midpoint] = EMPTY_TILE;

        // Initializes the starting configuration
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

    //Checks for any playable moves
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

        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                if(i == spaceRow) {
                    if (grid[i][spaceCol - 1].equals(frog) || grid[i][spaceCol + 1].equals(toad)){
                        return true;
                    }
                    if (grid[i][spaceCol - 2].equals(frog) && grid[i][spaceCol-1].equals(toad) ||
                    grid[i][spaceCol + 2].equals(toad) && grid[i][spaceCol + 1].equals(frog)){
                        return true;
                    }
                }
                if(j == spaceCol){
                    if (grid[spaceRow - 1][j].equals(frog) || grid[spaceRow + 1][j].equals(toad)){
                        return true;
                    }
                    if (grid[spaceRow - 2][j].equals(frog) && grid[spaceRow - 1][j].equals(toad) ||
                            grid[spaceRow + 2][j].equals(toad) && grid[spaceRow + 1][j].equals(frog)){
                        return true;
                    }
                }
            }
        }

        return false;
    }


    // Checks for winning configuration
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

    @Override
    public String toString(){
        String gamestate = "";
        for(String[] rows : grid){
            for(String cols : rows){
                gamestate += cols + "  ";
            }
            gamestate += "\n";
        }

        return gamestate;
    }
}
