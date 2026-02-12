import java.util.*;

class Node implements Comparable<Node> {
    int[][] puzzle;
    int x, y;          // position of blank (0)
    int g, h;          // g = cost so far, h = heuristic cost
    Node parent;

    Node(int[][] p, int x, int y, int g, Node parent) {
        this.puzzle = p;
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = calculateHeuristic(p);
        this.parent = parent;
    }

    int calculateHeuristic(int[][] p) {
        int count = 0;
        int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (p[i][j] != 0 && p[i][j] != goal[i][j])
                    count++;

        return count;
    }

    public int compareTo(Node n) {
        return (this.g + this.h) - (n.g + n.h);
    }
}

public class EightPuzzle {

    static void printPuzzle(int[][] p) {
        for (int[] row : p) {
            for (int val : row)
                System.out.print(val + " ");
            System.out.println();
        }
        System.out.println();
    }

    static boolean isGoal(int[][] p) {
        int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        return Arrays.deepEquals(p, goal);
    }

    static void solve(int[][] start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        int x = 0, y = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (start[i][j] == 0) {
                    x = i;
                    y = j;
                }

        Node root = new Node(start, x, y, 0, null);
        pq.add(root);

        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (isGoal(current.puzzle)) {
                printSolution(current);
                return;
            }

            visited.add(Arrays.deepToString(current.puzzle));

            for (int[] d : directions) {
                int newX = current.x + d[0];
                int newY = current.y + d[1];

                if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
                    int[][] newPuzzle = copyPuzzle(current.puzzle);

                    newPuzzle[current.x][current.y] = newPuzzle[newX][newY];
                    newPuzzle[newX][newY] = 0;

                    if (!visited.contains(Arrays.deepToString(newPuzzle))) {
                        Node child = new Node(newPuzzle, newX, newY, current.g + 1, current);
                        pq.add(child);
                    }
                }
            }
        }

        System.out.println("No Solution Found");
    }

    static int[][] copyPuzzle(int[][] p) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++)
            copy[i] = p[i].clone();
        return copy;
    }

    static void printSolution(Node node) {
        if (node == null)
            return;

        printSolution(node.parent);
        printPuzzle(node.puzzle);
    }

    public static void main(String[] args) {
        int[][] start = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };

        System.out.println("Initial State:");
        printPuzzle(start);

        System.out.println("Solution Steps:");
        solve(start);
    }
}
