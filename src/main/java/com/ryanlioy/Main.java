package com.ryanlioy;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ImageConverter converter = new ImageConverter("./mazes/250x250.png");
        Graph graph = converter.toGraph();

        Solver solver = new Solver(graph);
        long start = System.nanoTime();
        ArrayList<Node> solution = solver.aStar(graph.getStart(), graph.getFinish()); // solve maze
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