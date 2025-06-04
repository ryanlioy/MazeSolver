package com.ryanlioy;

import java.util.ArrayList;

public class Solver {

    /**
     * Solve using A* pathfinding algorithm
     * @param start the node to start at
     * @param end the end node
     * @return {@link ArrayList} of {@link Node} which is the solution path, if no solution then null
     */
    public static ArrayList<Node> aStar(Node start, Node end) {
        if (start == null || end == null) { // no start/end node
            return null;
        }
        ArrayList<Node> closeSet = new ArrayList<>(); // the Nodes that are not useful
        ArrayList<Node> openSet = new ArrayList<>(); // the Nodes that might be useful
        openSet.add(start);
        ArrayList<Node> path = new ArrayList<>();
        while (!openSet.isEmpty()) {
            int bestF = 0;
            for (int i = 0; i < openSet.size(); i++) { // find next least costly node
                if (openSet.get(i).getF() < openSet.get(bestF).getF()) {
                    bestF = i;
                }
            }
            Node current = openSet.get(bestF);

            if (current.equals(end)) { // check if current node is end node
                Node temp = current;
                path.add(temp);
                while (temp.getPrevious() != null) { // backtracking path
                    path.add(temp.getPrevious());
                    temp = temp.getPrevious();
                }
                return path;
            }

            openSet.remove(current);
            closeSet.add(current);

            ArrayList<Node> neighbors = current.getNeighbors();
            for (Node n : neighbors) { // check neighbors
                if (n != null) {
                    if (!closeSet.contains(n) && !n.isWall()) {
                        double tempG = current.getG() + heuristic(current, n);
                        if (!openSet.contains(n)) {
                            openSet.add(n);
                        } else if (tempG >= n.getG()) {
                            continue;
                        }
                        n.setPrevious(current);
                        n.setG(tempG);
                        n.setF(n.getG() + heuristic(n, end));
                    }
                }
            }
        }
        return null; // no solution
    }

    /*
    This is the H in A* and the difference between A* and Dijkstra.
     */

    /**
     * This is the heuristic, the h in A*, and is the difference between A* and Dijkstra's algorithm.
     * <br>
     * This is just the Euclidean distance
     * @param a the node to start at
     * @param b the end node
     * @return the Euclidean distance between the two passed nodes
     */
    private static double heuristic(Node a, Node b) {
        int y1 = a.getCol();
        int x1 = a.getRow();
        int y2 = b.getCol();
        int x2 = b.getRow();
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }
}
