import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class does all the hard work.
 */
class Maze extends JPanel {

    //Height and length of maze (number of cells).
    private int length;
    private int height;

    //Starting position for maze generation.
    private int startingX = 100;
    private int startingY = 100;

    //Used for scaling purposes.
    private JFrame frame;

    //Container for all the cells. Represents a map of the maze.
    public Cell[][] map;

    public Maze(int w, int h, JFrame frame) {
        this.length = w;
        this.height = h;
        this.map = new Cell[length][height];
        this.frame = frame;
        this.generateNewRandomMaze();
    }

    /**
     * Fill map with nodes.
     */
    public void generateNewRandomMaze(){
        fillMapWithLinkedNodes();
        this.generateMazeWithPrim(map[startingX][startingY]);
    }

    private void fillMapWithLinkedNodes(){
        //Create all cells and set them to type 0 (wall)
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++){
               map[i][j] = new Cell(0,i,j);
            }
        }

        //Link all cells so each cell know its neighbors.
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++){
                Cell currentCell = map[i][j];

                // Link node with it's neighbors in all directions. Ignore if node is at the edge.
                if (i != 0) {
                    currentCell.setEast(map[i-1][j]);
                }
                if (i != length-1) {
                    currentCell.setWest(map[i+1][j]);
                }
                if (j != 0) {
                    currentCell.setNorth(map[i][j-1]);
                }
                if (j != height-1) {
                    currentCell.setSouth(map[i][j+1]);
                }
            }
        }
    }

    /*
    This method uses Randomized Prim to generate a random maze.
    Algorithm is taken from Wikipedia's entry on Maze Generation.
     */
    private void generateMazeWithPrim(Cell startingCell){
        ArrayList<Cell> walls = new ArrayList<>();
        Random rand = new Random();
        Cell newCellToAdd = null;

        // Add cell to maze, and add its walls to wall-list.
        addCellToMaze(startingCell, walls);

        // While there are walls in the list...
        while(walls.size() > 0) {

            //Pick a random wall from the list.
            int wallIndex = rand.nextInt(walls.size());
            Cell c = walls.get(wallIndex);

            //If only one of the two cells the wall divides is visited, add the other cell to the maze.
            if (c.neighborPaths.size() == 1) {
                int relativeX = c.x-c.neighborPaths.get(0).x;
                int relativeY = c.y-c.neighborPaths.get(0).y;

                int newCellX = c.x+relativeX;
                int newCellY = c.y+relativeY;

                if(0 < newCellX && newCellX < height-1 && 0 < newCellY && newCellY < length-1) {

                    newCellToAdd = map[c.x+relativeX][c.y+relativeY];
                    addCellToMaze(newCellToAdd, walls);
                    c.setType(1);
                }
            }
            walls.remove(wallIndex);
        }
        //Make the last added cell be the goal.
        newCellToAdd.setType(3);
    }
    private void addCellToMaze(Cell toAdd, ArrayList walls){

        //Mark starting cell as part of the maze.
        toAdd.setType(1);
        this.repaint();

        //Add cell walls to wall-list
        for (Cell c : toAdd.neighbors){
           if (c != null) {
               walls.add(c);
           }
        }

    }
    protected void paintComponent(Graphics g) {

        //Draw all the cells.
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {

                // Make the cells have a size proportional to the window size.
                Rectangle r = frame.getBounds();
                int cellHeight= r.height/height;
                int cellWidth = r.width/length;

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
                }
            }
        }
    }

    public int[] getStartingPos(){
        return new int[]{startingX,startingY};
    }

}
