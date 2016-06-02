/*
 * Copyright (C) 2016 Thomas Kercheval
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

import card_game_24.objects.Player;
import java.util.ArrayList;

/**
 * Provides an assortment of static sorting algorithms.
 *
 * Project: 24 Card Game
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143 
 * Created on May 2, 2016, 2:31 PM 
 * Revised on Jun 1, 2016, 10:33 PM
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
     * Compares two ints.
     * @param one integer one
     * @param two integer two
     * @return true if one is less than two in their ordering.
     */
    public static boolean less(int one, int two) {
        return one < two;
    }
    
    /**
     * Implements a simple insertion sort to sort an int[].
     * @param arr The int[] we want to sort.
     */
    public static void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0 && less(arr[j], arr[j - 1]); j--) {
                swap(arr, j, j - 1);
            }
        }
    }
    
    /**
     * Determines if one Player has a lesser score than a second Player.
     * @param one Player one.
     * @param two Player two.
     * @return True if Player one has a lower high score than Player two.
     */
    public static boolean less(Player one, Player two) {
        return one.getHighScore() < two.getHighScore();
    }
    
    /**
     * Swaps two elements in an ArrayList of Objects which implement the
     * Comparable interface.
     * @param <E> A type which implements the Comparable interface.
     * @param arr The ArrayList of Comparable objects we wish to sort.
     * @param one Index of the first item we wish to swap.
     * @param two Index of the second item we wish to swap.
     */
    public static <E extends Comparable> void swap(ArrayList<E> arr, 
            int one, int two) {
        E temp = arr.get(one);
        arr.set(one, arr.get(two));
        arr.set(two, temp);
    }
    
    /**
     * Implements insertion sort for an ArrayList of Comparable objects.
     * @param <E> A Type which implements the Comparable interface.
     * @param arr The ArrayList of Comparable objects that we wish to sort.
     */
    public static <E extends Comparable> void insertionSort(ArrayList<E> arr) {
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j > 0 
                    && (arr.get(j).compareTo(arr.get(j - 1)) > 0); j--) {
                swap(arr, j, j - 1);
            }
        }
    }
}
