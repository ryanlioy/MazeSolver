package com.ryanlioy;

import java.util.ArrayList;

public class Solver {
    private Graph graph;

    public Solver(Graph graph) {
        this.graph = graph;
    }

    public ArrayList<Node> aStar(Node start, Node finish) {
        if (start == null || finish == null) { // no start/end node
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

            if (current.equals(finish)) { // check if current node is end node
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
                try {
                    if (!closeSet.contains(n) && !n.isWall()) {
                        double tempG = current.getG() + heuristic(current, n);
                        if (!openSet.contains(n)) {
                            openSet.add(n);
                        } else if (tempG >= n.getG()) {
                            continue;
                        }
                        n.setPrevious(current);
                        n.setG(tempG);
                        n.setF(n.getG() + heuristic(n, finish));
                    }
                } catch (NullPointerException e) { // neighbors that do not exist are set to null and requires this
                }

            }
        }
        return null; // no solution
    }

    /*
    This is the H in A* and the difference between A* and Dijkstra.
     */
    private static double heuristic(Node end, Node finish) {
        int y1 = end.getCol();
        int x1 = end.getRow();
        int y2 = finish.getCol();
        int x2 = finish.getRow();
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)); // Euclidean distance
    }
}
