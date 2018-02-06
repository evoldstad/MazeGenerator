import java.util.ArrayList;

abstract public class MazeSolver {

    Maze maze;
    private boolean visualize;

    public MazeSolver(Maze maze, boolean visualize) {
        this.maze = maze;
        this.visualize = visualize;
    }

    public void markCellAsVisited(Cell toMark) {

        //Mark starting cell as part of the maze.
        toMark.setType(2);

        //If generation should be visualized...
        if (visualize) {
            visualizeUpdate();
        }
    }

    public void markCellAsSolution(Cell toMark) {
        //Mark starting cell as part of the solution.
        toMark.setType(3);

        //If generation should be visualized...
        if (visualize) {
            visualizeUpdate();
        }
    }

    private void visualizeUpdate() {
        //Redraw the maze to show changes made.
        maze.repaint();

        //Sleep to slow down maze generation
        try {
            Thread.sleep(1);
        } catch (InterruptedException E) {
            System.out.println("Failed to sleep!(1)");
        }
    }

    public abstract ArrayList<Cell> findShortestPath();

}
