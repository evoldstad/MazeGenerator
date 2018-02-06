import java.util.ArrayList;

public abstract class RandomMazeGenerator {

    public Maze maze;
    private boolean visualize;

    public RandomMazeGenerator(Maze maze, boolean visualize) {
        this.maze = maze;
        this.visualize = visualize;
    }

    public void addCellToMaze(Cell toAdd, ArrayList walls) {

        //Mark starting cell as part of the maze.
        toAdd.setType(1);

        //Add cell walls to wall-list
        for (Cell c : toAdd.getNeighbors().values()) {
            if (c != null) {
                walls.add(c);
            }
        }


        //If generation should be visualized...
        if (visualize) {
            //Redraw the maze to show changes made.
            maze.repaint();

            //Sleep to slow down maze generation
            try {
                Thread.sleep(1);
            } catch (InterruptedException E) {
                System.out.println("Failed to sleep!(1)");
            }
        }
    }

    public Cell[][] generateRandomMaze(int mazeWidth, int mazeHeight) {
        return generateRandomMaze(mazeWidth, mazeHeight, mazeWidth / 2, mazeHeight / 2);
    }

    abstract public Cell[][] generateRandomMaze(int mazeWidth, int mazeHeight, int startingX, int startingY);


}
