package com.ryanlioy;

import java.util.ArrayList;

/**
 * Represents a pixel from the maze image when converted to a {@link Graph}
 */
public class Node {
    private double f, g, h;
    private final int row, col; // row and col
    private boolean isWall;
    private final ArrayList<Node> neighbors = new ArrayList<>();
    private Node previous = null; // default is null due to backtracking

    public Node(int r, int c, boolean isWall) {
        this.isWall = isWall;
        this.row = r;
        this.col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setIsWall(boolean isWall) {
        this.isWall = isWall;
    }

    public void addNeighbor(Node b) {
        neighbors.add(b);
    }

    public void setPrevious(Node n) {
        previous = n;
    }

    public Node getPrevious() {
        return previous;
    }

    @Override
    public String toString() { // for debugging
        return "[" + row + "][" + col + "]";
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Node n)) {
            return false;
        }
        return row == n.getRow() && col == n.getCol();
    }
}
