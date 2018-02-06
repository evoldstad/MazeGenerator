import javax.swing.*;
import java.util.ArrayList;

/**
 * This program generates a random maze using a modified version of Prim's algorithm, and displays it using Swing.
 * It then visualises finding the shortest path using a simple version of Djikstra's algorithm (since every step has the
 * same cost this is essentially just a flood-fill).
 * @author Erik Voldstad
 * @version 2.0
 */
public class MazeApp extends JFrame {
    public static void main(String[] args) {

        // Size of frame (pixels) in which the maze will be generated
        int frameWidth = 540;
        int frameHeight = 540;

        //Size of maze in number of cells. Will be adjusted to fit frame.
        int mazeHeight = 101;
        int mazeWidth = 101;

        //Starting position of both maze generation and of optimal path search.
        int startingX = 50;
        int startingY = 50;

        //Choose whether to visualize the generation and solving of the maze.
        boolean visualizeGeneration = true;
        boolean visualizeSolution = true;

        // Create JFrame
        JFrame frame = new JFrame("Maze");

        //Create the maze.
        Maze m = new Maze(mazeWidth, mazeHeight, startingX, startingY, frame);

        //Choose the generator and the solver to use for the simulation.
        RandomMazeGenerator gen = new PrimGenerator(m, visualizeGeneration);
        MazeSolver solver = new DjikstraSolver(m, visualizeSolution);

        // Configure JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(frameWidth, frameHeight);

        //Create the maze and add it to the frame 
        frame.add(m);
        frame.setVisible(true);

        //Generate and solve the maze.
        m.generateNewRandomMaze(gen);
        solveMaze(solver);

    }

    /**
     * This method is responsible for visualising and solving for the shortest path using a simple version of Djikstra's
     * algorithm.
     * @param solver  Which maze-solving solver to use (which in practice means which algorithm to use).
     */

    private static void solveMaze(MazeSolver solver) {
        ArrayList<Cell> optimalRoute = solver.findShortestPath();
        System.out.printf("The maze can be solved in %d steps%n", optimalRoute.size());
    }





}

