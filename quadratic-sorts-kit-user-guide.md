# Quadratic Sorts Kit: User Guide

This kit teaches bubble sort, selection sort and insertion sort on short arrays, with step-by-step traces and operation counts.

| File | Who uses it | What it is |
|---|---|---|
| `worksheet.md` | learners | A worksheet, Exercises 1 to 6, built on a six-job build queue |
| `visualizer.html` | everyone | Interactive step-by-step bars for all three sorts, with a code panel, counters, a comparison table and a Predict mode |
| `QuadraticSorts.java` | everyone | The same sorts as runnable Java, printing a trace |


Give learners `worksheet.md`, `visualizer.html` and `QuadraticSorts.java`. The folder also has two zip files: `learner_kit.zip` (worksheet, visualizer, Java) and `full_kit.zip` (the learner files plus this guide and the facilitator materials).

## 1. Running a session

1. Learners open `visualizer.html` in a browser (double-click it; no server, no install, no internet needed).
2. They work Exercises 1 to 5 on paper, then use the visualizer in Exercise 6 and to check earlier answers.
3. Before checking a trace, learners can turn on **Predict mode** and call each step before it is shown. This is a good warm-up for Exercises 1 to 3.
4. Suggested split: Exercises 1 to 3 as a pair, then compare totals on a whiteboard; Exercise 4 before opening the visualizer; Exercises 5 and 6 as a short discussion.


## 2. The visualizer

### Controls

| Control | What it does |
|---|---|
| **Bubble / Selection / Insertion** | Choose the algorithm. |
| **Back / Next** | Step one action backward or forward. The left and right arrow keys do the same. |
| **Play / Pause** | Step automatically. In Predict mode, Play pauses at each question. |
| **Reset** | Return to step 0. |
| **Size** | Number of values (3 to 12). Changing it generates a new random array. |
| **Example [5 2 4 6 1 3]** | The worksheet array (always 6 values). |
| **Random, Already sorted, Reversed, Nearly sorted, Many duplicates** | Generate an array of the current Size with that shape. Click again for a new one. |
| **Predict mode** | Ask before each step instead of just showing it (see below). The score next to the checkbox counts right answers. |

You cannot type your own values; use the Java file for that (see section 3).

### What you see

- **Bars.** Each bar is a value; its index is under it. Colors: light blue unsorted, yellow being compared, red just swapped or shifted, green in its final place (bubble and selection), purple the current minimum (selection), pale green the sorted prefix and blue "key in hand" with a dashed hole (insertion).
- **Message line.** What the algorithm just did, in words.
- **Counters.** Comparisons so far, and swaps so far (bubble, selection) or shifts so far (insertion).
- **Code panel.** The Java loop for the chosen algorithm, with the line being executed highlighted.
- **Totals table.** For the current array, the full-run comparisons and swaps/shifts of all three sorts, plus n(n-1)/2. This is the quickest way to compare algorithms on the same input.

### Predict mode

Tick **Predict mode** to answer a question before each step is shown:

| Algorithm | When it asks | Choices |
|---|---|---|
| Bubble | Before each comparison | Swap / Leave them |
| Selection | Before each comparison | Yes, new min / No |
| Insertion | When a key is picked up | The index where the key will land |
| Insertion | Before each comparison | Shift it right / Stop here |

After an answer, the message line shows **✔ Right** or **✘ Not quite**, then the usual explanation. Next and the right arrow key wait until the question is answered, and Back still works. Choosing another algorithm, pressing Reset or loading a new array clears the answers and the score.

### Reading the bars together with the code

Step through bubble sort and watch the yellow pair and the highlighted line `if (a[j] > a[j + 1])`. Each yellow pair is one comparison. A red pair means a swap followed. After each pass the right end turns green.


## 3. QuadraticSorts.java

### Run it

Any Java 11 or newer works:

```
javac QuadraticSorts.java
java QuadraticSorts bubble 5 2 4 6 1 3
java QuadraticSorts selection 5 2 4 6 1 3
java QuadraticSorts insertion 5 2 4 6 1 3
```

The first argument is the algorithm, the rest are the values. Any integers work, so this is the way to try your own array. The program prints one line per event and a last `DONE` line with the sorted array and the totals.

### Event lines

| Line | Meaning |
|---|---|
| `CMP i j` | Bubble: compare a[i] and a[j]. Selection: compare a[i] with the current min a[j]. |
| `CMP j` | Insertion: compare a[j] with the key. |
| `SWAP i j` | Swap a[i] and a[j]. |
| `MIN j` | Selection: index j is the new min. |
| `KEEP i` | Selection: the min was already at index i, no swap. |
| `STOP` | Bubble: a pass with no swaps, so stop early. |
| `KEY i` | Insertion: pick up a[i] as the key. |
| `SHIFT j k` | Insertion: copy a[j] one place right (to index k). |
| `PLACE k` | Insertion: put the key at index k. |
| `DONE [..] cmp=C moves=M` | Final array, comparisons, and swaps (bubble, selection) or shifts (insertion). |

The visualizer is built on the same logic, and its counters equal `cmp` and `moves` in the Java output.

### Comparison with the worksheet

`java QuadraticSorts bubble 5 2 4 6 1 3` ends with `DONE [1, 2, 3, 4, 5, 6] cmp=15 moves=9`, which matches Exercise 1. Selection gives `cmp=15 moves=3`; insertion gives `cmp=12 moves=9`.


## 4. The worksheet at a glance

| Exercise | Topic |
|---|---|
| 1 | Bubble sort trace, pass by pass, with early exit |
| 2 | Selection sort trace, round by round |
| 3 | Insertion sort trace, key by key, with shifts |
| 4 | Predict best case and worst case counts, then check them |
| 5 | Pick an algorithm for six situations, including stability and cost of moves |
| 6 | Part A: find the `j > 0` bug in insertion sort. Part B: check work and explore in the visualizer |

Conventions throughout: ascending order, indexes from 0, and the strict tests used in the code.


## 5. Troubleshooting

| Problem | Fix |
|---|---|
| Changing Size replaced my array | Expected. Size always generates a new array; use the Example button for the worksheet array. |
| Measured counts differ from the worksheet | Check the strict comparisons (`>` in bubble and insertion, `<` in selection) and that you counted only comparisons, not moves. |
| Bubble sort shows fewer than 15 comparisons on 6 values | The early-exit check stopped it. That is correct for an array that gets sorted early. |
| Java trace differs from the visualizer | Check the algorithm name and the values. The `CMP` lines should match one for one. |
| Next does nothing | Predict mode is on and a question is waiting. Answer it, or untick Predict mode. |
| Many duplicates looks unsorted | Equal values are the same color height, so bars may look alike. Read the numbers above the bars. |

---

© 2026 Joseph Su. All rights reserved. For enrolled students only.
