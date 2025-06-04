package com.ryanlioy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageConverter {
    private BufferedImage image;
    private int width;
    private int height;
    private final String path;

    public ImageConverter(String path) {
        try {
            image = ImageIO.read(new FileInputStream(path));
        }
        catch (IOException e) {
            System.err.println("File does not exist, check path or filename");
            System.exit(0);
        }
        this.path = path;
        this.width = image.getWidth(); // done for readability of to2Darray()
        this.height = image.getHeight(); // please don't pass an empty image
    }

    /**
     * Converts a BufferedImage into a {@link Graph}
     * @return a Graph representation of the image
     * @throws Exception
     */
    public Graph toGraph() throws Exception { // nested loop does [j][i] as [i][j] reflects along line from top left to bot right
        Node[][] nodes = new Node[width][height];

        for (int i = 0; i < nodes.length; i++) { // initial assignment/null pointer
            for (int j = 0; j < nodes.length; j++) {
                nodes[i][j] = new Node(i, j, false); // the [j][i] thing doesn't matter here
            }
        }

        for (int i = 0; i < width; i++) { // build the array
            for (int j = 0; j < height; j++) {
                Color t = new Color(image.getRGB(i, j));
                if (t.equals(Color.BLACK)) {
                    nodes[j][i].setIsWall(true); //black pixels are walls
                }
                else if (t.equals(Color.WHITE)) {
                    nodes[j][i].setIsWall(false); //white pixels are paths
                }
                else { // is not black or white
                    throw new Exception("Pixel at [" + i + "][" + j + "]" + " is not black or white");
                }
            }
        }

        for (int row = 0; row < width; row++) { // add neighbors, if neighbor does not exist (out of bounds) it makes it null
            for (int col = 0; col < height; col++) {
                if (row != 0) {
                    nodes[row][col].addNeighbor(nodes[row - 1][col]); // node above
                }
                else {
                    nodes[row][col].addNeighbor(null);
                }

                if (col != width - 1) {
                    nodes[row][col].addNeighbor(nodes[row][col + 1]); // node to the right
                }
                else {
                    nodes[row][col].addNeighbor(null);
                }

                if (row != width - 1) { // node below
                    nodes[row][col].addNeighbor(nodes[row + 1][col]);
                }
                else {
                    nodes[row][col].addNeighbor(null);
                }

                if (col != 0) { // node to the left
                    nodes[row][col].addNeighbor(nodes[row][col - 1]);
                }
                else {
                    nodes[row][col].addNeighbor(null);
                }
            }
        }
        return new Graph(nodes);
    }

    /*
    This method was originally done by copying the global image into a new variable
    and writing the solution over the image. This however made the entire image
    black and white, and the solution wasn't obvious. So here we are, rebuilding
    the image.
     */
    public void toImage(Graph graph, ArrayList<Node> solution) throws IOException { // converts to image and saves it at location from constructor
        BufferedImage ret = new BufferedImage(graph.getWidth(), graph.getHeight(), BufferedImage.TYPE_INT_RGB);

        // find OS because windows is a special cookie
        int index = System.getProperty("os.name").contains("Windows") ? path.lastIndexOf("\\") : path.lastIndexOf("/");

        // change this according to your OS path convention
        File file = new File(path.substring(0, index) + "/solved.png"); // remove the filename from filepath
        final int RED = new Color(255, 0, 0).getRGB(); // for readability
        final int BLACK = new Color(0, 0, 0).getRGB();
        final int WHITE = new Color(255, 255, 255).getRGB();

        for (int row = 0; row < width; row++) { // rebuild image
            for (int col = 0; col < height; col++) {
                if (graph.getNode(col, row).isWall()) {
                    ret.setRGB(row, col, BLACK);
                }
                if (!graph.getNode(col, row).isWall()) {
                    ret.setRGB(row, col, WHITE);
                }
            }
        }
        try { // make sure
            for (Node n : solution) { // put solution
                ret.setRGB(n.getCol(), n.getRow(), RED);
            }
        }
        catch (NullPointerException e) {
            // this is here so the output is just the input
        }

        ImageIO.write(ret, "png", file);
    }
}
