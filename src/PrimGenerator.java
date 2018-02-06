import java.util.ArrayList;
import java.util.Random;

public class PrimGenerator extends RandomMazeGenerator {

    public PrimGenerator(Maze maze, boolean visualize) {
        super(maze, visualize);
    }

    /**
     * This method uses Randomized Prim to generate a random maze.
     * Algorithm is taken from Wikipedia's entry on Maze Generation.
     */

    @Override
    public Cell[][] generateRandomMaze(int mazeWidth, int mazeHeight, int startingX, int startingY) {
        ArrayList<Cell> walls = new ArrayList<>();
        Random rand = new Random();
        Cell newCellToAdd = null;

        Cell[][] map = maze.getMap();

        // Add cell to maze, and add its walls to wall-list.
        super.addCellToMaze(map[startingX][startingY], walls);

        // While there are walls in the list...
        while (walls.size() > 0) {

            //Pick a random wall from the list.
            int wallIndex = rand.nextInt(walls.size());
            Cell c = walls.get(wallIndex);

            //If only one of the two cells the wall divides is visited, add the other cell to the maze.
            if (c.neighborPaths.size() == 1) {
                int relativeX = c.x - c.neighborPaths.get(0).x;
                int relativeY = c.y - c.neighborPaths.get(0).y;

                int newCellX = c.x + relativeX;
                int newCellY = c.y + relativeY;

                if (0 < newCellX && newCellX < mazeHeight - 1 && 0 < newCellY && newCellY < mazeWidth - 1) {

                    newCellToAdd = map[c.x + relativeX][c.y + relativeY];
                    addCellToMaze(newCellToAdd, walls);
                    c.setType(1);
                }
            }
            walls.remove(wallIndex);
        }
        //Make the last added cell be the goal. An alternative to this is choosing the goal randomly, which might make
        //for more interesting searches since they won't always be on the edge of the map.
        newCellToAdd.setType(3);

        return map;
    }
}
