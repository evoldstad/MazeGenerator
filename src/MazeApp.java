import javax.swing.*;
import java.util.*;

/**
 * This program generates a random maze using a modified version of Prim's algorithm, and displays it using Swing.
 * It then visualises finding the shortest path using a simple version of Djikstra's algorithm (since every step has the
 * same cost this is essentially just a flood-fill).
 */
public class MazeApp extends JFrame {

    // Size of frame in which the maze will be generated
    private static int frameSize = 550;

    public static void main(String[] args) {

        // Create JFrame 
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(frameSize, frameSize);

        //Create the maze and add it to the frame 
        Maze m = new Maze(101,101,frame);

        frame.add(m);
        frame.setVisible(true);
        solveMaze(m, frame);
    }

    /*
    Visualize solving the maze using Djikstra's algorithm.
     */
    private static void solveMaze(Maze m, JFrame frame) {

        //Fetch starting node.
        int[] startingPos = m.getStartingPos();
        Cell startingNode = m.map[startingPos[0]][startingPos[1]];

        //All unfinished nodes.
        ArrayList<Cell> openSet = new ArrayList<>();
        openSet.add(startingNode);

        //All finished nodes.
        HashSet<Cell> closedSet = new HashSet<>();

        //Map of which node from which to most effectively travel.
        HashMap<Cell, Cell> cameFrom = new HashMap<>();
        cameFrom.put(startingNode,null);

        //Final cell we will land on, used for backtracking.
        Cell end = null;

        //Used for breaking out of stacked loops.
        outerLoop:

        //While there are nodes that haven't been explored...
        while (!openSet.isEmpty()) {

            //Pick oldest node
            Cell current = openSet.get(0);

            for (Cell next : current.neighborPaths) {

                //If we haven't visited the node before...
                if (!closedSet.contains(next)) {

                    //If we are at the goal...
                    if (next.getType() == 3) {
                        end = current;
                        System.out.println("Finished!");
                        break outerLoop;
                    }

                    cameFrom.put(next, current);
                    openSet.add(next);

                    //Sleep for 1ms and update drawing.
                    try{
                        Thread.sleep(1);
                        SwingUtilities.updateComponentTreeUI(frame);
                    }catch (InterruptedException E ){
                        System.out.println("Failed to sleep!(1)");
                    }
                }
                current.setType(2);
            }

            closedSet.add(current);
            openSet.remove(0);
        }

        //Backtrack from goal-node and paint the path red.
        ArrayList<Cell> chain = new ArrayList<>();
        while (end != null) {

            end.setType(3);
            chain.add(end);
            end = cameFrom.get(end);

            try{
                Thread.sleep(1);
                SwingUtilities.updateComponentTreeUI(frame);
            }catch (InterruptedException E ){
                System.out.println("Failed to sleep!(2)");
            }

        }
        System.out.printf("The best path can be completed in %d steps!%n", chain.size());

    }



}

