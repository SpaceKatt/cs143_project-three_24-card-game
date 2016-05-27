/*
 * Copyright (C) 2016 thomas.kercheval
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package card_game_24.utilities;

/**
 *
 * @author thomas.kercheval
 */
public class SortingAlgorithms {
    /**
     * This method swaps two specified elements of an integer array.
     * @param arr int array
     * @param indexOne Index of first item we want to swap
     * @param indexTwo Index of second item we want to swap
     */
    public static void swap(int[] arr, int indexOne, 
                            int indexTwo) {
        int temp = arr[indexOne];
        arr[indexOne] = arr[indexTwo];
        arr[indexTwo] = temp;
    }
    
    /**
     * Compares object one to comparable object two and determines if 
     * object one is lesser in the order of the sort. Compares Parcel
     * objects by ID only.
     * @param one Object to compare to two, is this object less?
     * @param two Object to compare to one.
     * @return true if one is less than two in their ordering.
     */
    public static boolean less(int one, int two) {
        return one < two;
    }
    
    /**
     * Implements a simple insertion sort to sort an ArrayList of Comparable
     * objects. Sort by Parcel ID.
     * @param arr The ArrayList we want to sort.
     */
    public static void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0 && less(arr[j], arr[j - 1]); j--) {
                swap(arr, j, j - 1);
            }
        }
    }
}
