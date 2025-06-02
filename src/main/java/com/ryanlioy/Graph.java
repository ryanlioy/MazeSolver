package com.ryanlioy;

/**
 * Wrapper for Node[][] so I don't have to keep typing []
 */
public class Graph {
    private final Node[][] graph;
    private final Node start;
    private final Node finish;
    private final int height;

    public Graph(Node[][] graph) {
        this.graph = graph;
        this.start = findStart();
        this.finish = findFinish();
        this.height = graph.length;
    }

    public Node getNode(int row, int col) {
        return graph[row][col];
    }

    public boolean isWall(int row, int col) {
        return getNode(row, col).isWall();
    }

    public Node getStart() {
        return start;
    }

    public Node getFinish() {
        return finish;
    }

    // TODO instead of assuming the start and end are on the top and bottom of image why not search the edges for two white pixels?
    private Node findStart() {
        for (int i = 1; i < height; i++) {
            if (!isWall(0, i)) {
                return getNode(0, i);
            }
        }
        return null;
    }

    private Node findFinish() {
        for (int i = 1; i < height; i++) {
            if (!isWall(height - 1, i)) {
                return getNode(height - 1, i);
            }
        }
        return null;
    }
}
