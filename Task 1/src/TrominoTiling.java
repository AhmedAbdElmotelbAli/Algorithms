import java.lang.Math;
import java.util.*;

public class TrominoTiling {
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;

    public static void main(String[] args) {
        int n = 2;
        // Initialize the board with random missing square
        int size =(int) Math.pow(2, n);
        int[][] board = new int[size][size];
        int missingRow = (int) (Math.random() * size); // random row for the missing square
        int missingCol = (int) (Math.random() * size); // random column for the missing square
        board[missingRow][missingCol] = -1;

        printBoard(tile(board, size/2, 0, 0));
    }

    //tile the board recursively using dynamic programming
    private static int[][] tile(int[][] board, int n, int x, int y) {
        // base case when the board is successfully tiled
        if (n == 1) {
            return board;
        }
        // divide the board into 4 sub-boards (sub-probelms)
        int[][] subBoard1 = new int[n][n];
        int[][] subBoard2 = new int[n][n];
        int[][] subBoard3 = new int[n][n];
        int[][] subBoard4 = new int[n][n];
        //fill each subboard
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                subBoard1[i][j] = board[x+i][y+j];
                subBoard2[i][j] = board[x+i][y+n+j];
                subBoard3[i][j] = board[x+n+i][y+j];
                subBoard4[i][j] = board[x+n+i][y+n+j];
            }
        }
        //coordinates of missing square in each sub-board
        int[] missing1 = Missing(subBoard1);
        int[] missing2 = Missing(subBoard2);
        int[] missing3 = Missing(subBoard3);
        int[] missing4 = Missing(subBoard4);

        // tile the sub-boards recursively
        subBoard1 = tile(subBoard1, n/2, missing1[0], missing1[1]);
        subBoard2 = tile(subBoard2, n/2, missing2[0], missing2[1]);
        subBoard3 = tile(subBoard3, n/2, missing3[0], missing3[1]);
        subBoard4 = tile(subBoard4, n/2, missing4[0], missing4[1]);
        // Merge the sub-boards into one board
//        int size =(int)Math.pow(2,n);
        int[][] mergedBoard = new int[2*n][2*n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mergedBoard[x+i][y+j] = subBoard1[i][j];
                mergedBoard[x+i][y+n+j] = subBoard2[i][j];
                mergedBoard[x+n+i][y+j] = subBoard3[i][j];
                mergedBoard[x+n+i][y+n+j] = subBoard4[i][j];
            }
        }
        //if it needs to be rotated
        if (missing1[0] == missing2[0] && missing3[0] == missing4[0]) {
            if (missing1[1] == missing3[1] && missing2[1] == missing4[1]) {
                //no rotation
                mergedBoard[missing1[0]+x+n][missing1[1]+y+n] = GREEN;
                mergedBoard[missing1[0]+x+n][missing1[1]+y+n-1] = RED;
                mergedBoard[missing1[0]+x+n-1][missing1[1]+y+n] = BLUE;
            } else {
                //rotate 90 degrees
                mergedBoard[missing1[0]+x+n][missing1[1]+y+n] = BLUE;
                mergedBoard[missing1[0]+x+n][missing1[1]+y+n-1] = RED;
                mergedBoard[missing1[0]+x+n-1][missing1[1]+y+n-1] = GREEN;
            }
        } else {
            //rotate 180 degrees
            mergedBoard[missing1[0]+x+n][missing1[1]+y+n] = RED;
            mergedBoard[missing1[0]+x+n-1][missing1[1]+y+n] = BLUE;
            mergedBoard[missing1[0]+x+n][missing1[1]+y+n-1] = GREEN;
        }
        return mergedBoard;
    }

    //find the coordinates of the missing square in a sub-board
    private static int[] Missing(int[][] board) {
        int[] missing = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == -1) {
                    missing[0] = i;
                    missing[1] = j;
                }
            }
        }
        return missing;
    }

    //print the board
    private static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == -1) { //missing square
                    System.out.print(" ");
                } else if (board[i][j] == RED) {
                    System.out.print("R");
                } else if (board[i][j] == GREEN) {
                    System.out.print("G");
                } else if (board[i][j] == BLUE) {
                    System.out.print("B");
                }
            }
            System.out.println();
        }
    }
}
