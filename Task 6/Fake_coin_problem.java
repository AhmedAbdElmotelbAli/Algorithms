import java.util.Arrays;
import java.util.Scanner;
/*user can change the values of n,NORMAL_SIZE,HEAVY_COIN and LIGHT_COIN in the class Fake_coin_problem
 **user can change the values of fake_coin_index and fake_coin_weight also in the main function
 **changing those values will set the problem dynamically
 */
public class Fake_coin_problem {
    /*defining standard weights of coins we can change it or put any weights we want*/
    static int NORMAL_SIZE = 10;
    static int HEAVY_COIN = 11;
    static int LIGHT_COIN = 9;
    /*defining the number of coins dynamically to determine the fake coin*/
    static int n = 12;
    /*define dynamic array of coins weights with dynamic size n*/
    static int[] coins;
    /*define memory variables used to solve the problem using divide and conquer (dynamic programming technique)*/
    /*memory variable used by getFakeIndex function to store the weight of not fake coin if the weight is known by function automatically*/
    static int normalValue = -1;
    /*memory variable used by getFakeIndex function to store fake index and memorize it to stop recursive calls if found*/
    static int fakeIndex = -1;
    /*function to compare two coins*/
    static int balance(int x, int y){
        if(x>y){/*if the coin on the right side of the balance is bigger return 1*/
            return 1;
        }else if(y>x){/*if the coin on the left side of the balance is bigger return 1*/
            return -1;
        } else{/*if the two coins are equal return 0*/
            return 0;
        }
    }
    /*the function that calculates the fake index of the coin and store it in fakeIndex variable if found
     *if the fake coin not found, leave its value at -1
     * */
    private static void getFakeIndex(int startIndex,int endIndex) {
        if(fakeIndex == -1) {/*if the fake coin dont found do the following*/
            /*determining the number of items that may be 3 or 2 only in every divided problem to solve other wise divide*/
            int number_of_items = endIndex - startIndex + 1;
            if (number_of_items == 3) {/*if the number of items in the divided problem = 3*/
                int firstIndex, secondIndex, thirdIndex;
                firstIndex = startIndex;
                secondIndex = firstIndex + 1;
                thirdIndex = secondIndex + 1;

                if (normalValue != -1) {
                    if (balance(coins[firstIndex], normalValue) != 0) {
                        fakeIndex = firstIndex;
                    } else if (balance(coins[secondIndex], normalValue) != 0) {
                        fakeIndex = secondIndex;
                    } else if (balance(coins[thirdIndex], normalValue) != 0) {
                        fakeIndex = thirdIndex;
                    }
                } else if (normalValue == -1) {
                    if (balance(coins[firstIndex], coins[secondIndex]) == 0) {
                        normalValue = coins[firstIndex];
                        if (balance(coins[firstIndex], coins[thirdIndex]) != 0) {
                            fakeIndex = thirdIndex;
                        }
                    } else if (balance(coins[firstIndex], coins[thirdIndex]) == 0) {
                        normalValue = coins[firstIndex];
                        fakeIndex = secondIndex;
                    } else if (balance(coins[secondIndex], coins[thirdIndex]) == 0) {
                        normalValue = coins[secondIndex];
                        fakeIndex = firstIndex;
                    }
                }
            } else if (number_of_items == 2) {/*if the number of items in the divided problem = 2*/
                int firstIndex = startIndex;
                int secondIndex = firstIndex + 1;
                if (normalValue != -1) {
                    if (balance(coins[firstIndex], normalValue) != 0) {
                        fakeIndex = firstIndex;
                    } else if (balance(coins[secondIndex], normalValue) != 0) {
                        fakeIndex = secondIndex;
                    }
                } else if (normalValue == -1) {
                    /*cant predict so we assume third one exists*/
                    int thirdIndex = secondIndex+1;
                    if(balance(coins[firstIndex], coins[secondIndex]) == 0){
                        normalValue = coins[firstIndex];
                    }else if(balance(coins[firstIndex], coins[thirdIndex]) == 0){
                        normalValue = coins[firstIndex];
                        fakeIndex = secondIndex;
                    }else if(balance(coins[secondIndex], coins[thirdIndex]) == 0){
                        normalValue = secondIndex;
                        fakeIndex = firstIndex;
                    }
                }
            } else {//divide problem
                int midIndex = (startIndex + endIndex) / 2;
                getFakeIndex(startIndex, midIndex);
                getFakeIndex(midIndex, endIndex);
            }
        }/*if the fake coin found end the function*/
    }
    public static void main(String[] args) {
        /*get the necessary inputs from user*/
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of coins: ");
        int user_input_num_of_coins = scanner.nextInt();
        System.out.print("Enter the index of the fake coin: ");
        int user_input_Index_of_fake = scanner.nextInt();
        System.out.print("Enter the weight of normal coin: ");
        int user_input_Weight_of_normal_coin = scanner.nextInt();
        System.out.print("Enter the weight of fake coin: ");
        int user_input_Weight_of_fake_coin = scanner.nextInt();

        /*initialize program variables with the values from user*/
        n = user_input_num_of_coins;
        int fake_coin_index = user_input_Index_of_fake;
        int normal_coin_weight = user_input_Weight_of_normal_coin;
        int fake_coin_weight = user_input_Weight_of_fake_coin;
        /*calculating the size of the dynamic array of weight of coins from the input of user*/
        coins = new int[n];
        //fill the dynamic array with the values of normal_coin_weight variable to fill it with non-fake coins
        Arrays.fill(coins,normal_coin_weight);
        /*choose the index of fake coin and the weight of it */
        coins[fake_coin_index] = fake_coin_weight;
        /*getting the fake coin*/
        getFakeIndex(0,coins.length-1);
        /*printing the fake coin after putting its index to fakeIndex variable*/
        if(fakeIndex == -1){/*fake coin not found*/
            System.out.println("there is no fake coin");
        }else{
            if(fakeIndex == 0){/*first*/
                System.out.println("the fake coin is the first coin");
            } else if (fakeIndex == 1) {/*second*/
                System.out.println("the fake coin is the second coin");
            } else if (fakeIndex == 2) {/*third*/
                System.out.println("the fake coin is the third coin");
            } else{/*other*/
                System.out.print("the fake coin is the ");
                System.out.print(fake_coin_index+1);
                System.out.println("th coin");
            }
            System.out.print("the fake coin has index of ");
            System.out.println(fakeIndex);
        }
    }
}