import java.util.ArrayList;


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

    // Will be used for referencing connected nodes. 
    private Cell north,east,south,west = null;

    //List of all known neighbors. 
    public ArrayList<Cell> neighbors = new ArrayList<>();

    //List of all known neighbors of type 1 (valid paths).
    public ArrayList<Cell> neighborPaths = new ArrayList<>();

    public Cell(int type, int x, int y){
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Cell getNorth(){
        return north;
    }

    public Cell getEast(){
        return east;
    }

    public Cell getSouth(){
        return south;
    }

    public Cell getWest(){
        return west;
    }

    public int getType(){
        return type;
    }

    public void setNorth(Cell c) {
        neighbors.add(c);
        this.north = c;
    }

    public void setEast(Cell c) {
        neighbors.add(c);
        this.east = c;
    }

    public void setSouth(Cell c) {
        neighbors.add(c);
        this.south = c;
    }

    public void setWest(Cell c) {
        neighbors.add(c);
        this.west = c;
    }

    public void setType(int i){
        if(i == 1) {
            //If cell becomes part of the maze, notify surrounding cells.
            for (Cell c : neighbors) {
                if (c != null){
                    c.neighborPaths.add(this);
                }
            }
        }
        this.type = i;
    }
}
