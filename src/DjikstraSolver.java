import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DjikstraSolver extends MazeSolver {
    public DjikstraSolver(Maze maze, boolean visualize) {
        super(maze, visualize);
    }

    @Override
    public ArrayList<Cell> findShortestPath() {

        //Fetch starting node.
        int[] startingPos = maze.getStartingPos();
        Cell startingNode = maze.getMap()[startingPos[0]][startingPos[1]];

        //All unfinished nodes.
        ArrayList<Cell> openSet = new ArrayList<>();
        openSet.add(startingNode);

        //All finished nodes.
        HashSet<Cell> closedSet = new HashSet<>();

        //Map of which node from which to most effectively travel.
        HashMap<Cell, Cell> cameFrom = new HashMap<>();
        cameFrom.put(startingNode, null);

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

                }
                this.markCellAsVisited(current);
            }

            closedSet.add(current);
            openSet.remove(0);
        }

        //Backtrack from goal-node to get full list of nodes.
        ArrayList<Cell> chain = new ArrayList<>();
        while (end != null) {
            chain.add(end);
            this.markCellAsSolution(end);
            end = cameFrom.get(end);
        }
        return chain;
    }
}
