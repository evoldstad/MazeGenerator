import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class is used to represent the cells/nodes used in the other classes. It is mostly used for storing information,
 * and does very little computation. Worth noting is the neighbors and neighborPaths ArrayLists used for keeping track
 * of both all neighboring cells (neighbors) and all connected paths (neighborPaths). Also worth noting is the behavior
 * of setType, which interacts with neighboring nodes and "lets them know" this node is traversable.
 */
class Cell {

    /*
      Type of cells:
      0: wall
      1: path (part of maze)
      2: visited path
      3: goal
     */
    private int type;

    // x,y position for cell
    public int x,y;

    //List of all known neighbors. 
    private HashMap<Direction, Cell> neighbors = new HashMap<>();

    //List of all known neighbors of type 1 (valid paths).
    public ArrayList<Cell> neighborPaths = new ArrayList<>();

    public Cell(int type, int x, int y){
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Cell getNorth(){
        return neighbors.get(Direction.north);
    }

    public void setNorth(Cell c) {
        neighbors.put(Direction.north, c);
    }

    public Cell getEast(){
        return neighbors.get(Direction.east);
    }

    public void setEast(Cell c) {
        neighbors.put(Direction.east, c);
    }

    public int getType(){
        return type;
    }

    public Cell getSouth() {
        return neighbors.get(Direction.south);
    }

    public void setSouth(Cell c) {
        neighbors.put(Direction.south, c);
    }

    public Cell getWest() {
        return neighbors.get(Direction.west);
    }

    public void setWest(Cell c) {
        neighbors.put(Direction.west, c);
    }

    public HashMap<Direction, Cell> getNeighbors() {
        return neighbors;
    }

    public void setType(int i){
        if(i == 1) {
            //If cell becomes part of the maze, notify surrounding cells.
            for (Cell c : neighbors.values()) {
                if (c != null){
                    c.neighborPaths.add(this);
                }
            }
        }
        this.type = i;
    }
}
