// © 2026 Joseph Su. All rights reserved. For enrolled students only.

import java.util.*;

/**
 * DFS and BFS on a small graph, directed or undirected.
 *
 * The loops in dfs() and bfs() use the same lines as the "Code" panel in visualizer.html.
 * Lines marked "// trace" only print; delete them and the algorithm is unchanged.
 *
 * Directed vs undirected is only a matter of how GRAPH is built:
 *   undirected edge a-b : a appears in GRAPH[b] AND b appears in GRAPH[a]
 *   directed edge   a>b : b appears in GRAPH[a] only (GRAPH[a] = "out-neighbors of a")
 * The traversal code does not change. DFS only classifies edges when DIRECTED.
 */
public class Traversal {
    // Undirected: 0-1, 0-2, 1-3, 2-3, 2-5, 3-4 (every link listed from both ends).
    // This is the 6-service graph from the worksheet: 0 gateway, 1 auth, 2 orders,
    // 3 inventory, 4 warehouse-sync, 5 notifications.
    static final int[][] UNDIRECTED = {
        {1, 2},      // 0
        {0, 3},      // 1
        {0, 3, 5},   // 2
        {1, 2, 4},   // 3
        {3},         // 4
        {2}          // 5
    };

    // Directed: 0>1, 0>2, 0>3, 1>2, 2>0, 3>2 (each link listed once, from its source).
    // This is the 4-service call graph from worksheet Exercise 3.
    static final int[][] DIRECTED_GRAPH = {
        {1, 2, 3},   // 0 calls 1, 2, 3
        {2},         // 1 calls 2
        {0},         // 2 calls 0
        {2}          // 3 calls 2
    };

    static int[][] GRAPH;       // the graph being traversed
    static boolean DIRECTED;    // do edges have a direction?

    // Shared with classify(v, w) so that it can be called with two arguments, as in the panel.
    static Deque<Integer> stack;   // DFS: the current path from the start; top = current vertex
    static List<Integer> order;    // vertices in the order they were first reached

    // ------------------------------------------------------------------ DFS
    static void dfs(int start) {
        boolean[] visited = new boolean[GRAPH.length];   // visited[v]: v has been reached
        // next[v] = index into GRAPH[v] of the next neighbor to examine. It replaces the loop
        // variable a recursive DFS keeps on the call stack, so DFS can pause at v and resume later.
        int[] next = new int[GRAPH.length];
        stack = new ArrayDeque<>();
        order = new ArrayList<>();

        visited[start] = true;                           // mark on first reach
        order.add(start);
        stack.push(start);
        System.out.println("visit " + start + "   stack=" + path(stack));          // trace

        // ---- the loop below matches the visualizer's DFS code panel ----
        while (!stack.isEmpty()) {
            int v = stack.peek();                        // current vertex: look, don't remove yet
            if (next[v] < GRAPH[v].length) {             // does v still have unexamined neighbors?
                int w = GRAPH[v][next[v]++];             // take the neighbor at the bookmark, then advance it
                if (visited[w]) {
                    if (DIRECTED) classify(v, w);        // directed: back if on the stack, else forward/cross
                    else System.out.println("  at " + v + ": neighbor " + w + " already visited, skip"); // trace
                } else {
                    visited[w] = true; order.add(w); stack.push(w);    // first time reaching w: a tree edge
                    System.out.println("visit " + w + "   stack=" + path(stack));         // trace
                }
            } else {
                // bookmark ran past the end: v has no neighbors left, so v is finished
                stack.pop();                             // backtrack
                System.out.println("  " + v + " done, backtrack   stack=" + path(stack)); // trace
            }
        }
    }

    // Directed only. v > w was examined and w was already reached. Which kind of edge is it?
    //   back    : w is on the stack, so w is an ancestor of v. This closes a cycle.
    //   forward : w is finished and was reached after v (a descendant of v).
    //   cross   : w is finished and was reached before v (a different branch).
    // (A "tree" edge is the case where w was NOT reached yet; that happens in dfs() above.)
    // stack.contains and order.indexOf scan a list, so this costs O(V) per edge. Fine for a
    // small example; real code would keep discovery times in an array instead.
    static void classify(int v, int w) {
        String type = stack.contains(w) ? "back (cycle)"
                    : order.indexOf(w) > order.indexOf(v) ? "forward" : "cross";
        System.out.println("  at " + v + ": edge " + v + ">" + w + " is a " + type + " edge");   // trace
    }

    // ------------------------------------------------------------------ BFS
    // Direction does not change BFS: it follows GRAPH[v], which is out-neighbors when directed.
    static void bfs(int start) {
        boolean[] visited = new boolean[GRAPH.length];
        Queue<Integer> queue = new ArrayDeque<>();       // the frontier; front = next to process
        order = new ArrayList<>();

        visited[start] = true;                           // mark when enqueued, not when dequeued
        order.add(start);                                // (otherwise a vertex can be enqueued twice)
        queue.add(start);
        System.out.println("enqueue " + start + "   queue=" + queue);               // trace

        // ---- the loop below matches the visualizer's BFS code panel ----
        while (!queue.isEmpty()) {
            int v = queue.remove();                      // take the oldest vertex in the frontier
            System.out.println("dequeue " + v + "   queue=" + queue);               // trace
            for (int w : GRAPH[v]) {                     // all of v's neighbors in one go, so no next[] needed
                if (visited[w]) {
                    System.out.println("  at " + v + ": neighbor " + w + " already visited, skip");  // trace
                } else {
                    visited[w] = true; order.add(w); queue.add(w);
                    System.out.println("  at " + v + ": enqueue " + w + "   queue=" + queue);       // trace
                }
            }
        }
    }

    // Trace only: the stack as a list, bottom to top (ArrayDeque iterates top-first, so reverse).
    static List<Integer> path(Deque<Integer> stack) {
        List<Integer> p = new ArrayList<>(stack);
        Collections.reverse(p);
        return p;
    }

    static void run(String title, int[][] graph, boolean directed) {
        GRAPH = graph;
        DIRECTED = directed;
        System.out.println("##### " + title + " #####");
        System.out.println("=== DFS ===");
        dfs(0);
        System.out.println("order = " + order);
        System.out.println();
        System.out.println("=== BFS ===");
        bfs(0);
        System.out.println("order = " + order);
        System.out.println();
    }

    public static void main(String[] args) {
        run("Undirected: 0-1, 0-2, 1-3, 2-3, 2-5, 3-4", UNDIRECTED, false);
        run("Directed: 0>1, 0>2, 0>3, 1>2, 2>0, 3>2", DIRECTED_GRAPH, true);
    }
}
