package edu.hsutx;

import java.util.List;
import java.util.ArrayDeque;

public class WeightedDirectedGraph {
    // Adjacency matrix for storing edge weights
    private static int vertexCount;
    private static double[][] adjacencyMatrixData;

    /***
     *
     * @param vertexQuantity: Total number of vertices, as an int.  We will start counting at vertex 1, not 0.
     * @param edgeList: an List of Edges containing start and end vertex # and weight.
     ***/
    // Set up the graph with given edges
    public WeightedDirectedGraph(int vertexQuantity, List<Edge> edgeList) {
        vertexCount = Math.max(0, vertexQuantity);
        adjacencyMatrixData = new double[vertexCount + 1][vertexCount + 1];

        if (edgeList != null) {
            for (Edge edge : edgeList) {
                int start = edge.getStart();
                int end = edge.getEnd();
                double weight = edge.getWeight();
                // Only add valid edges
                if (start >= 1 && start <= vertexCount && end >= 1 && end <= vertexCount) {
                    adjacencyMatrixData[start][end] = weight;
                }
            }
        }
    }

    /***
     * returns true if vertex[start] has an edge to vertex[end], otherwise returns false
     * @param start
     * @param end
     */
    // Check if there's an edge from start to end
    public static boolean isAdjacent(int start, int end) {
        if (!isValidVertex(start) || !isValidVertex(end)) {
            return false;
        }
        return adjacencyMatrixData[start][end] != 0.0;
    }

    /***
     * returns a 2d matrix of adjacency weights, with 0 values for non-adjacent vertices.
     * @return matrix of doubles representing adjacent edge weights
     */
    // Get the adjacency matrix (for tests)
    public static double[][] adjacencyMatrix() {
        return adjacencyMatrixData;
    }

    /***
     * Conducts a Breadth First Search and returns the path from start to end, or null if not connected.
     * For accurate testing reproduction, add new vertices to the queue from smallest to largest.
     * @param start
     * @param end
     * @return an array of integers containing the path of vertices to be traveled, including start and end.
     */
    // Breadth First Search: shortest path from start to end
    public static int[] getBFSPath(int start, int end) {
        if (!isValidVertex(start) || !isValidVertex(end)) {
            return null;
        }

        boolean[] visited = new boolean[vertexCount + 1];
        int[] parent = new int[vertexCount + 1];
        for (int i = 0; i <= vertexCount; i++) {
            parent[i] = -1;
        }

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        visited[start] = true;
        queue.add(start);

        boolean found = false;
        while (!queue.isEmpty()) {
            int current = queue.remove();
            if (current == end) {
                found = true;
                break;
            }
            // Go through neighbors in order
            for (int neighbor = 1; neighbor <= vertexCount; neighbor++) {
                if (adjacencyMatrixData[current][neighbor] != 0.0 && !visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    queue.add(neighbor);
                }
            }
        }

        if (!found) {
            return null;
        }

        return buildPath(start, end, parent);
    }

    /***
     * Conducts a Depth First Search, and returns the path from start to end, or null if not connected.
     * Again, for accurate testing reproduction, add new vertices to the stack from smallest to largest.
     * @param start
     * @param end
     * @return an array of integers containing the path of vertices to be traveled, including start and end.
     */
    // Depth First Search: returns a path from start to end
    public static int[] getDFSPath(int start, int end) {
        if (!isValidVertex(start) || !isValidVertex(end)) {
            return null;
        }

        boolean[] visited = new boolean[vertexCount + 1];
        List<Integer> path = new java.util.ArrayList<>();
        boolean found = dfsHelper(start, end, visited, path);
        if (!found) {
            return null;
        }
        int[] result = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
            result[i] = path.get(i);
        }
        return result;
    }

    // Helper for DFS, builds the path as it goes
    private static boolean dfsHelper(int current, int end, boolean[] visited, List<Integer> path) {
        visited[current] = true;
        path.add(current);
        if (current == end) {
            return true;
        }
        for (int neighbor = vertexCount; neighbor >= 1; neighbor--) {
            if (adjacencyMatrixData[current][neighbor] != 0.0 && !visited[neighbor]) {
                boolean found = dfsHelper(neighbor, end, visited, path);
                if (found) {
                    return true;
                }
            }
        }
        path.remove(path.size() - 1);
        return false;
    }

    // Helpers
    // Checks if vertex is in valid range
    private static boolean isValidVertex(int v) {
        return v >= 1 && v <= vertexCount;
    }

    // Builds the path from parent array (used in BFS)
    private static int[] buildPath(int start, int end, int[] parent) {
        int length = 0;
        for (int cur = end; cur != -1; cur = parent[cur]) {
            length++;
            if (cur == start) {
                break;
            }
        }

        int[] path = new int[length];
        int index = length - 1;
        for (int cur = end; cur != -1; cur = parent[cur]) {
            path[index--] = cur;
            if (cur == start) {
                break;
            }
        }
        return path;
    }

}

