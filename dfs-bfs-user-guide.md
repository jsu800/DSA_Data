# DFS and BFS Kit: User Guide

This kit teaches depth-first and breadth-first search on small graphs, directed or undirected.

| File | Who uses it | What it is |
|---|---|---|
| `worksheet.md` | learners | A 45-minute worksheet, Exercises 1 to 6, built on a 6-service dependency scenario |
| `answer_key.md` | facilitators only | Answers, the full DFS trace, and facilitator notes |
| `visualizer.html` | everyone | Interactive step-by-step DFS and BFS, with a code panel and an adjacency table |
| `Traversal.java` | everyone | The same algorithms as runnable Java, with a printed trace |

Give learners `worksheet.md`, `visualizer.html` and `Traversal.java`. Keep `answer_key.md` back.

---

## 1. Running a session

1. Learners open `visualizer.html` in a browser (double-click it; no server or install).
2. They work Exercises 1 to 5 on paper, then use the visualizer in Exercise 6 and to check any earlier answer.
3. Suggested split: Exercises 1 and 2 as a pair, Exercise 3 together on a whiteboard, Exercises 4 to 6 as a short discussion. If time is short, keep Exercise 5 (the most common real-world BFS mistake) and cut Exercise 6.

---

## 2. The visualizer

### Controls

| Control | What it does |
|---|---|
| **DFS / BFS** | Choose the algorithm. |
| **Back / Next** | Step one action backward or forward. The left and right arrow keys do the same (not while the cursor is in a text box). |
| **Play / Pause** | Step automatically. |
| **Reset** | Return to step 0. |
| **Undirected / Directed** | Toggle whether edges have a direction. |
| **Vertices, Start** | Number of vertices (1 to 12) and the vertex the search starts from. Changing Vertices generates a new random graph, so set it before typing your own graph. |
| **Diamond, Path, Tree** | Preset graphs. |
| **New random graph** | A random graph with the current direction setting. |

### Entering your own graph

Type in the edit box and press **Apply** (or Ctrl/Cmd+Enter). Three formats work:

| Format | Example | Meaning |
|---|---|---|
| Undirected edges | `0-1, 0-2, 1-3` | Links go both ways |
| Directed edges | `0>1, 1>2, 2>0` | `a>b` means a points to b |
| Adjacency list, one line per vertex | `2: 0, 3, 5` | The vertex, then its neighbors. A tab or two or more spaces also work as the separator. A header line "Vertex Neighbors" is ignored. |

Notes:

- With an adjacency list, if every link is listed from both ends, the graph is undirected. If any link is listed from one end only, it is directed.
- The vertex count grows to fit the edges you type (so the Vertices field may change after Apply). The most is 12 vertices, numbered 0 to 11.
- Self-loops are ignored (with a note) and duplicate edges are merged.
- Do not mix `-` and `>` in one graph.
- Errors are shown under the box: unreadable text, a vertex above 11, or a start vertex that does not exist. When there is an error, the current graph stays unchanged.

### What you see

- **Graph.** Vertex colors: unseen, in the queue (BFS) or on the stack (DFS), current, finished. The legend under the graph names the colors for the chosen algorithm. A message line says what the algorithm just did.
- **Stack or queue.** Shown as a row under the graph.
- **Edge types (directed DFS).** Tree, back, forward and cross edges are labeled T, B, F and C. A back edge is a circular dependency. The visualizer also shows discovery and finish times in directed mode. Those are extras for the picture; the Java code does not compute them.
- **Neighbors table.** One row per vertex. The heading says "Out-neighbors" in directed mode.
  - The current vertex's row is blue.
  - The vertex being examined has a faint tint.
  - The neighbor being checked right now has a yellow badge.
- **Code panel.** The Java loop for the chosen algorithm, with the line being executed highlighted. The panel changes slightly in directed mode: it shows `classify(v, w)` where undirected mode just skips.

### Reading the table together with the code

Step through DFS and watch three things at once: the highlighted code line, the row in the table, and the yellow badge. Example on the worksheet graph: the badge on `0` in row 2 means "at vertex 2, checking neighbor 0". Because 0 is already visited, the code takes the `if (visited[w])` branch.

---

## 3. Traversal.java

### Run it

Any Java 11 or newer works:

```
javac Traversal.java
java Traversal
```

It runs DFS and BFS from vertex 0 on two graphs and prints a trace. `main` builds both:

- **Undirected:** `0-1, 0-2, 1-3, 2-3, 2-5, 3-4`, the worksheet's 6-service scenario. DFS gives `0, 1, 3, 2, 5, 4`; BFS gives `0, 1, 2, 3, 5, 4`.
- **Directed:** `0>1, 0>2, 0>3, 1>2, 2>0, 3>2`, the call graph from Exercise 3. DFS reports `2>0` as back, `0>2` as forward and `3>2` as cross.

### How it maps to the visualizer

- The loops in `dfs()` and `bfs()` use the same lines as the visualizer's code panel. Lines ending `// trace` only print; delete them and the algorithm is unchanged.
- Variables:

| Variable | Meaning |
|---|---|
| `visited[v]` | v has been reached |
| `next[v]` (DFS) | Index of the next neighbor of v to look at; lets DFS pause at v, go deeper, and resume |
| `stack` (DFS) | The current path from the start; top is the current vertex |
| `queue` (BFS) | The frontier |
| `order` | Vertices in the order first reached |

- BFS marks `visited` when a vertex is enqueued, not when it is dequeued (Exercise 5 is about this).

### Directed and undirected

The traversal code is the same. Only the way `GRAPH` is built changes:

- Undirected edge `a-b`: list b in `GRAPH[a]` and a in `GRAPH[b]`.
- Directed edge `a>b`: list b in `GRAPH[a]` only.

For a directed graph, DFS also calls `classify(v, w)` on an edge to an already visited vertex:

| Test | Type |
|---|---|
| `w` is on the stack | back (a cycle) |
| otherwise, `w` was reached after `v` | forward |
| otherwise | cross |

`classify` uses `stack.contains` and `order.indexOf`, which scan a list (O(V) per edge). That is fine for a small example; it is noted in a comment in the code.

### Trying your own graph

Edit the `UNDIRECTED` or `DIRECTED_GRAPH` array. Keep each vertex's neighbors in ascending order so the trace matches the visualizer and the worksheet. Compare your result with the visualizer by typing the same graph into its edit box.

---

## 4. The worksheet at a glance

| Exercise | Topic | Time |
|---|---|---|
| 1 | DFS trace on the 6-service graph | 10 min |
| 2 | BFS trace and fewest-hop distances | 8 min |
| 3 | Edge types in a directed call graph: visit order, then tree, back, forward or cross | 10 min |
| 4 | Pick DFS or BFS for six tasks | 6 min |
| 5 | Find the BFS bug (marking on dequeue) | 6 min |
| 6 | Check work in the visualizer: paste the scenario's table, watch the badge, predict orders, find a cross edge and a back edge | 8 min |

Convention throughout: neighbors are tried in ascending order, and every search starts at vertex 0.

---

## 5. Troubleshooting

| Problem | Fix |
|---|---|
| Nothing happens on Apply | Check for an error message under the edit box. The old graph stays until the new text is valid. |
| The Vertices field changed after Apply | Expected. The vertex count grows to fit the edges you typed. |
| An adjacency list became directed | Some link was listed from one end only. List it from both ends for an undirected graph. |
| Java trace differs from the visualizer | Check that each neighbor list is in ascending order, and that the start vertex and direction match. |
| Worksheet answers differ | Both programs and the key use ascending neighbor order. Check the order your learner used. |

---

© 2026 Joseph Su. All rights reserved. For enrolled students only.
