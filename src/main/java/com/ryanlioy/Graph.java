package com.ryanlioy;

/**
 * Wrapper for Node[][] so I don't have to keep typing []
 */
public class Graph {
    private final Node[][] graph;
    private final Node start;
    private final Node finish;
    private final int height;
    private final int width;

    public Graph(Node[][] graph) {
        this.graph = graph;
        this.height = graph.length;
        this.width = graph[0].length;
        this.start = findStart();
        this.finish = findFinish();
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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
