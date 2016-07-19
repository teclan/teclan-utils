package teclan.utils;

import org.junit.Test;

public class SortTest {
    int[] a = new int[] { 11, 4, 7, 1, 3, 8 };

    @Test
    public void bubbleSortASCTest() {

        Sort.bubbleSortASC(a);
    }

    @Test
    public void bubbleSortDESCTest() {
        Sort.bubbleSortDESC(a);
    }

    @Test
    public void InsertSortASCTest() {
        Sort.InsertSortASC(a);
    }

    @Test
    public void ShellSortTest() {
        Sort.ShellSortASC(a);
    }
}
