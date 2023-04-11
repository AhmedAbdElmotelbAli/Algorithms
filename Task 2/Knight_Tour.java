//Problem : A knight is placed on the first block of an empty board and, moving according to the rules of chess, must visit each square exactly once.
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
//represents each 1*1 cell
class Square
{
    int x;
    int y;

    public Square(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

class Knight_Tour {
    //initialize the board
    public static final int N = 8;
    //define movements of the Knight.
    public static final int moveX[] = {1, 1, 2, 2, -1, -1, -2, -2}; //x coordinates
    public static final int moveY[] = {2, -2, 1, -1, 2, -2, 1, -1}; //y coordinates

    //A function that makes sure we are in the limits of the board
    boolean limits(int x, int y) {
        return ((x >= 0 && y >= 0) &&
                (x < N && y < N));
    }

    //Make sure current movement is valid
    boolean isvalid(int board[], int x, int y) {
        //The next cell is in limits
        //the next cell is empty (marked with -1) 
        boolean degree = (limits(x, y)) && (board[y * N + x] < 0);
        return degree;
    }

    //return number of adjacent empty squares
    int getDegree(int board[], int x, int y) {
        int count = 0;
        for (int i = 0; i < N; ++i)
            if (isvalid(board, (x + moveX[i]),
                    (y + moveY[i])))
                count++;


        return count;
    }

    //return the next Square we can move to greedy(by choosing the squares with min. degree early)!

    Square nextMove(int board[], Square square) {
        //initialize index of min degree(value to be changed later
        int min_idx = -1;
        //initialize min degree
        int min_deg = (N + 1);
        //number of adjacent possible squares
        int degree;
        //Possible values we could go to starting from our square
        int possibleX, possibleY;

        // Try all adjacents of (*x, *y) and choose the one with minimum degree.
        int start = ThreadLocalRandom.current().nextInt(1000) % N;
        for (int count = 0; count < N; ++count) {
            //To make sure that it is within the range of the board 
            int i = (start + count) % N;

            //start from a random possible neighbour
            possibleX = square.x + moveX[i];
            possibleY = square.y + moveY[i];
            //get number of possible valid squares adjacent to the neighbour
            degree = getDegree(board, possibleX, possibleY);

            //Proceed if that square is valid and its degree is smaller than smallest found degree.
            if ((isvalid(board, possibleX, possibleY)) && (degree < min_deg)) {
                //update min degree and its index(marking this square as the best candidate yet).
                min_idx = i;
                min_deg = degree;
            }

        }

        //if we finished the loop and min. index doesn't change then we didn't find any square
        if (min_idx == -1)
            return null;

        // Store coordinates of best square to go next
        possibleX = square.x + moveX[min_idx];
        possibleY = square.y + moveY[min_idx];

        // Mark this square as visited!
        board[possibleY * N + possibleX] = board[(square.y) * N + (square.x)] + 1;
        // Actually visiting the point
        square.x = possibleX;
        square.y = possibleY;

        return square;
    }
    //Check if the tour is closed or not yet by checking if current position is one knight's position from the start
    boolean neighbour(int x1, int y1, int x2, int y2) {
        for (int i = 0; i < N; ++i)
            if (((x1 + moveX[i]) == x2) &&
                    ((y1 + moveY[i]) == y2))
                return true;

        return false;
    }

    //Generates all possible moves using warnsdorff's heuristic until visiting all cells
    boolean findPossibleMoves(){
        //To begin, mark all the cells as unvisited by filling them all with -1
        int board[] = new int [N * N];
        for(int i = 0; i < N * N; ++i){
            board[i] = -1;
        }
        //Set initial positions of x and y  (start from random position)
        int sx = (int) (Math.random() * 8) ;
        int sy = (int) (Math.random() * 8) ;

        //Set the current position as same as the initial position
        Square sqaure = new Square(sx,sy);

        //Mark the first move
        board[sqaure.y * N + sqaure.x] = 1;

        //Keep tracking next points using warnsdorff's heuristic
        Square sq = null;
        for (int i = 0; i < N * N - 1; ++i){
            //Check if the next square is empty
            sq = nextMove(board,sqaure);
            if(sq == null)
                return false;
        }

        //Check if the knight's tour is closed, and it ends at the starting point
        if(!neighbour(sq.x, sq.y, sx, sy))
            return false;
        print(board);
        return true;

    }
    //Print Board and Count the number of movements
    void print(int board[])
    {
        int count=0;
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < N; ++j){
                System.out.printf("%d\t", board[j * N + i]); count++;
            }
            System.out.printf("\n");

        }
        //Print the count after printing the board
        System.out.printf("minimum number of moves is: %d ",count);
    }
    
    public static void main(String[] args){
        //While we can't find possible solution
        while(!new Knight_Tour().findPossibleMoves()){

        }

    }
}
    
