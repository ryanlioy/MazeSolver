package com.ryanlioy;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ImageConverter converter = new ImageConverter("./mazes/250x250.png");
        Graph graph = converter.toGraph();

        long start = System.nanoTime();

        // all nodes are linked together by their neighbors. By passing in the
        // start and finish node the entire graph is effectively passed in
        ArrayList<Node> solution = Solver.aStar(graph.getStart(), graph.getFinish());
        long finish = System.nanoTime();
        if (solution == null){
            System.out.println("No solution found");
        }
        else{
            System.out.println("Time: "+(finish-start)/1e6+" ms ("+(finish-start)/1e9+"s)");
            converter.toImage(graph, solution); // write image with solution
        }
    }
}