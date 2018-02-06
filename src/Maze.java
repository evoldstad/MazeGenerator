import javax.swing.*;
import java.awt.*;

/**
 * This class is the representation of the maze. It generates a random maze using Pim's algorithm and keeps track
 * of the mazes cells/nodes.
 *
 */

class Maze extends JPanel {

    //Height and mazeWidth of maze (number of cells).
    private int mazeWidth;
    private int mazeHeight;

    //Starting position for maze generation.
    private int startingX;
    private int startingY;

    //Used for scaling purposes.
    private JFrame frame;

    //Container for all the cells. Represents a map of the maze.
    private Cell[][] map;

    public Maze(int mazeWidth, int mazeHeight, int startingX, int startingY, JFrame frame) {
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        this.startingX = startingX;
        this.startingY = startingY;

        this.map = newEmptyMap();
        this.frame = frame;
    }

    public Cell[][] newEmptyMap() {
        Cell[][] map = new Cell[mazeWidth][mazeHeight];
        fillMapWithLinkedNodes(map);

        return map;
    }

    private void fillMapWithLinkedNodes(Cell[][] map) {
        if (map.length == 0) {
            throw new IllegalArgumentException("Map can't be empty.");
        }

        int mazeWidth = map.length;
        int mazeHeight = map[0].length;

        //Create all cells and set them to type 0 (wall)
        for (int i = 0; i < mazeWidth; i++) {
            for (int j = 0; j < mazeHeight; j++) {
                map[i][j] = new Cell(0, i, j);
            }
        }

        //Link all cells so each cell know its neighbors.
        for (int i = 0; i < mazeWidth; i++) {
            for (int j = 0; j < mazeHeight; j++) {
                Cell currentCell = map[i][j];

                // Link node with it's neighbors in all directions. Ignore if node is at the edge.
                if (i != 0) {
                    currentCell.setEast(map[i-1][j]);
                }
                if (i != mazeWidth - 1) {
                    currentCell.setWest(map[i+1][j]);
                }
                if (j != 0) {
                    currentCell.setNorth(map[i][j-1]);
                }
                if (j != mazeHeight - 1) {
                    currentCell.setSouth(map[i][j+1]);
                }
            }
        }
    }

    /**
     * Fills the map with nodes, links all nodes to their neighbors and generates the maze.
     */
    public void generateNewRandomMaze(RandomMazeGenerator gen) {
        map = gen.generateRandomMaze(mazeWidth, mazeHeight, startingX, startingY);
    }

    protected void paintComponent(Graphics g) {

        //Draw all the cells.
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {

                // Make the cells have a size proportional to the window size.
                Rectangle r = frame.getBounds();
                int cellHeight = r.height / mazeHeight;
                int cellWidth = r.width / mazeWidth;

                // Calculate each cells actual x,y position in pixels.
                int cellX = i * cellWidth;
                int cellY = j * cellHeight;

                switch (map[i][j].getType()) {

                    case 0: {
                        g.setColor(Color.black);
                        g.fillRect(cellX, cellY, cellWidth, cellHeight);
                    }
                    break;

                    case 1: {
                        g.setColor(Color.white);
                        g.fillRect(cellX, cellY, cellWidth, cellHeight);
                    }
                    break;

                    case 2: {
                        g.setColor(Color.blue);
                        g.fillRect(cellX, cellY, cellWidth, cellHeight);
                    }
                    break;

                    case 3: {
                        g.setColor(Color.red);
                        g.fillRect(cellX, cellY, cellWidth, cellHeight);
                    }
                    break;
                    //More colors can be added for more types. An idea could be to always show the starting node with a
                    //green color to make it easier to see how far from the start the algorithm has searched so far.
                }
            }
        }
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    public int getMazeHeight() {
        return mazeHeight;
    }

    public Cell[][] getMap() {
        return map;
    }
    public int[] getStartingPos(){
        return new int[]{startingX,startingY};
    }

}
