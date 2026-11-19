// © 2026 Joseph Su. All rights reserved. For enrolled students only.

import java.util.*;

public class QuadraticSorts {
    static List<String> log = new ArrayList<>();
    static int comparisons, moves;

    static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; moves++; }

    static void bubbleSort(int[] a) {
        int n = a.length;
        for (int pass = 0; pass < n - 1; pass++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - pass; j++) {
                comparisons++; log.add("CMP " + j + " " + (j + 1));
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1); swapped = true;
                    log.add("SWAP " + j + " " + (j + 1));
                }
            }
            if (!swapped) { log.add("STOP"); break; }
        }
    }

    static void selectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++; log.add("CMP " + j + " " + min);
                if (a[j] < a[min]) { min = j; log.add("MIN " + j); }
            }
            if (min != i) { swap(a, i, min); log.add("SWAP " + i + " " + min); }
            else log.add("KEEP " + i);
        }
    }

    static void insertionSort(int[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            int key = a[i];
            log.add("KEY " + i);
            int j = i - 1;
            while (j >= 0) {
                comparisons++; log.add("CMP " + j);
                if (a[j] <= key) break;
                a[j + 1] = a[j]; moves++;
                log.add("SHIFT " + j + " " + (j + 1));
                j--;
            }
            a[j + 1] = key;
            log.add("PLACE " + (j + 1));
        }
    }

    public static void main(String[] args) {
        String alg = args[0];
        int[] a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) a[i - 1] = Integer.parseInt(args[i]);
        switch (alg) {
            case "bubble": bubbleSort(a); break;
            case "selection": selectionSort(a); break;
            case "insertion": insertionSort(a); break;
            default: throw new IllegalArgumentException(alg);
        }
        for (String s : log) System.out.println(s);
        System.out.println("DONE " + Arrays.toString(a) + " cmp=" + comparisons + " moves=" + moves);
    }
}
