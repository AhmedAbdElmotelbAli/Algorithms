import java.util.Scanner;

public class HitTarget {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        if (n == 2) {
            // Special case for n = 2
            System.out.println("1 1");
            return;
        }

        // Start shooting at spot 2
        int shot = 2;
        while (shot <= n - 1) {
            // Shoot at consecutive spots until the target is hit or guaranteed to be hit
            System.out.print(shot + " ");
            shot++;
        }

        // Shoot at spot n-1 to force the target to move to an even-numbered spot
        System.out.print((n - 1) + " ");

        // Continue shooting at consecutive even-numbered spots until the target is hit
        shot = n - 2;
        while (shot >= 2) {
            System.out.print(shot + " ");
            shot -= 2;
        }
        System.out.println();
    }
}
