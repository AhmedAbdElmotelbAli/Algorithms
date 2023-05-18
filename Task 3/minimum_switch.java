package quiz;


import java.util.Arrays;
import java.util.Scanner;


public class switch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

       int n = 50; // Example input value
        long result = minimumSwitch(n);
        System.out.println("Result: " + result);

               // Output: 8 -3 -3 6 -3 5




    }
     public static long minimumSwitch(int n) {
        long[] dp = new long[1000];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i-1] + 2*dp[i-2] + 1;
        }
        return dp[n];
    }
}
