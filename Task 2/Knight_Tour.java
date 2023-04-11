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
//        //The next cell is in limits
//        boolean inlimit = limits(x, y);
//        //the next cell is empty (not marked with 1) //######not sure what this line do!
//        boolean empty = board[y * N + x] < 0;
//        return (inlimit && empty);

        boolean degree = (limits(x, y)) && (board[y * N + x] < 0);
        return degree;
    }

    //return number of adjacent empty squares
    //####We can adjust it to return those empty squares!
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
            //####Don't get it!!!
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
        //####Don't get the logic###### I think it has something to do with the board being 1D
        board[possibleY * N + possibleX] = board[(square.y) * N + (square.x)] + 1;
        // Actually visiting the point
        square.x = possibleX;
        square.y = possibleY;

        return square;
    }


