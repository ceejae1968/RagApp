package programs;

import java.util.Arrays;

public class EquilibriumIndex {
    public static void main(String args[]) {
        int[] arr = new int[]{-7, 1, 5, 2, -4, 3, 0};
        System.out.println(getEquilibriumIndex(arr));
    }

    private static int getEquilibriumIndex(int[] arr) {
        int prefix = arr[0];
        for(int i=1; i < arr.length; i++){
            prefix += arr[i];
            arr[i] = prefix;
        }
        int sumAll = prefix;

        for(int i=1; i < arr.length; i++){
            if(arr[i-1] == (sumAll - arr[i])){
                return i;
            }
        }

        return -1;
    }
}
