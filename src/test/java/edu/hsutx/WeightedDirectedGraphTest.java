package edu.hsutx;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class WeightedDirectedGraphTest {
    public static WeightedDirectedGraph graph;

    @BeforeAll
    public static void setUp() throws Exception {
        // Create a list of Edge objects.
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 2, 10));
        edges.add(new Edge(2, 1, 7));
        edges.add(new Edge(1, 3, 5));
        edges.add(new Edge(3, 1, 7));
        edges.add(new Edge(3, 5, 10));
        edges.add(new Edge(1, 4, 3));
        edges.add(new Edge(4, 1, 10));
        edges.add(new Edge(4, 2, 1));
        edges.add(new Edge(4, 5, 3));
        edges.add(new Edge(5, 4, 1));
        edges.add(new Edge(5, 3, 10));
        edges.add(new Edge(5, 7, 9));
        edges.add(new Edge(5, 6, 3));
        edges.add(new Edge(2, 6, 6));
        edges.add(new Edge(6, 2, 10));
        edges.add(new Edge(6, 5, 4));
        edges.add(new Edge(4, 7, 3));
        edges.add(new Edge(7, 4, 4));
        edges.add(new Edge(7, 2, 4));
        edges.add(new Edge(5, 8, 6));
        edges.add(new Edge(8, 5, 6));
        edges.add(new Edge(8, 9, 2));
        edges.add(new Edge(9, 8, 10));
        edges.add(new Edge(9, 1, 5));


        graph = new WeightedDirectedGraph(20,edges);
    }

    @Test
    public void testIsAdjacent() {
        assertTrue(graph.isAdjacent(1, 2), "1 should be adjacent to 2.");
        assertTrue(graph.isAdjacent(2, 1), "2 should be adjacent to 1 (bidirectional edge).");
        assertFalse(graph.isAdjacent(1, 9), "1 should not be directly adjacent to 9.");
        assertFalse(graph.isAdjacent(4, 3), "4 should not be directly adjacent to 3.");
    }

    @Test
    public void testAdjacencyMatrix() {
        double[][] matrix = graph.adjacencyMatrix();
        assertEquals(10.0, matrix[1][2], 0.01, "Edge weight from 1 to 2 should be 7.0.");
        assertEquals(7.0, matrix[2][1], 0.01, "Edge weight from 2 to 1 should be 9.0.");
        assertEquals(0.0, matrix[1][7], 0.01, "No edge from 1 to 7, should be 0.");
        assertEquals(3.0, matrix[4][5], 0.01, "Edge weight from 4 to 5 should be 2.8.");
    }

    @Test
    public void testGetBFSPath() {
        int[] expectedPath = {1, 3, 5, 8, 9};
        int[] actualPath = graph.getBFSPath(1, 9);
        assertArrayEquals(expectedPath, actualPath, "The BFS path from 1 to 9 should match the expected path.");
    }

    @Test
    public void testGetDFSPath() {
        int[] expectedPath = {1, 4, 7, 2, 6, 5, 8, 9};
        int[] actualPath = graph.getDFSPath(1, 9);
        assertArrayEquals(expectedPath, actualPath, "The DFS path from 1 to 9 should match the expected path.");
    }

}
