#include <iostream>
using namespace std;
int sum(int w1, int w2, int w3, int w4);
int sum(int w1, int w2);
/*macros for states*/
#define unknown -1
#define fake    0
#define good    1
/*any weights we can change it as we want*/
#define normalWeight    10
#define heavyWeight     11
#define loghtWeight     9
/*the weights of coins we can change it as we want*/
int x;
int coins[12] = { normalWeight,normalWeight,normalWeight,normalWeight,normalWeight,normalWeight,normalWeight,normalWeight,normalWeight,normalWeight,normalWeight,heavyWeight};
int groupsState[3] = { unknown,unknown,unknown };
int coinsInGroupState[4] = { unknown,unknown,unknown,unknown };
int group[3] = { sum(coins[0], coins[1], coins[2], coins[3]) ,sum(coins[4], coins[5], coins[6], coins[7]),sum(coins[8], coins[9], coins[10], coins[11]) };
int fakeIndex = -1;
int sum(int w1, int w2, int w3, int w4) {
    return w1 + w2 + w3 + w4;
}
int sum(int w1, int w2) {
    return w1 + w2;
}
bool balance(int w1, int w2) {
    if (w1 == w2) return true;
    else return false;
}
int getFakeIndex() {
    /*calculating fake group*/
    if (balance(group[0], group[1])) {//no fake in group 0 or 1
        groupsState[0] = good;
        groupsState[1] = good;
        if (group[0] != group[2]) {
            groupsState[2] = fake;
        }
    }
    if (balance(group[1], group[2])) {//no fake in group 1 or 2
        groupsState[1] = good;
        groupsState[2] = good;
        if (group[0] != group[2]) {
            groupsState[0] = fake;
        }
    }
    if (balance(group[2], group[0])) {//no fake in group 2 or 0
        groupsState[2] = good;
        groupsState[0] = good;
        if (!balance(group[0], group[1])) {
            groupsState[1] = fake;
        }
    }
    /*fake group calculated if exists*/
    /*finding fake coin*/
    for (int i = 0; i < 4; i++) {
        if (groupsState[i] == fake) {
            if (coins[i * 4 + 0] == coins[i * 4 + 1]) {
                coinsInGroupState[0] = good;
                coinsInGroupState[1] = good;
            }
            if (balance(coins[i * 4 + 1], coins[i * 4 + 2])) {
                coinsInGroupState[1] = good;
                coinsInGroupState[2] = good;
            }
            if (balance(coins[i * 4 + 2], coins[i * 4 + 3])) {
                coinsInGroupState[2] = good;
                coinsInGroupState[3] = good;
            }
            if (balance(coins[i * 4 + 3], coins[i * 4 + 0])) {
                coinsInGroupState[3] = good;
                coinsInGroupState[0] = good;
            }
            for (int j = 0; j < 4; j++) {
                if (coinsInGroupState[j] == unknown) {
                    coinsInGroupState[j] = fake;
                    return i * 4 + j;
                }
            }
            return -1;
        }


    }
    //no coin is fake
    return -1;
}
int main() {
    int inputWeight;
    for (int i = 0; i < 12; i++) {
        cout << "enter the weight of coin " << i + 1 << ":" << endl;
        cin >> inputWeight;
        if (inputWeight == -1) break;
        coins[i] = inputWeight;
    }
    int fakeIndex = getFakeIndex();
    int groupNumber;
    int coinNumber;
    if (fakeIndex == -1) {
        cout << "no fake coin";
    }
    else {
        groupNumber = (fakeIndex / 4) + 1;
        coinNumber = (fakeIndex % 4) + 1;
        cout << "the fake coin is the number " << fakeIndex + 1 << " it is in group " << groupNumber << " and it is the number " << coinNumber << " in the group. (the numbers start from 1 not 0 in printing)" << endl;
    }
    cin >> x;
    return 0;
}