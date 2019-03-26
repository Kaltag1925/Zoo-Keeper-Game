package Engine.Logic;

import CustomMisc.DataStructureUtils;
import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Moveable.CanOpen;
import Engine.GameObjects.Entities.Moveable.Moveable;
import Engine.GameObjects.Entities.Openable;
import Engine.GameObjects.GameMap;
import Engine.GameObjects.Tiles.Coordinate;
import Engine.GameObjects.Tiles.Tile;

import java.util.*;

public class AStarPathFinder implements Pathing {

    public class NodeComparator implements Comparator<Node> {
        public int compare(Node nodeFirst, Node nodeSecond) {
            if (nodeFirst.getFValue() > nodeSecond.getFValue()) return 1;
            if (nodeSecond.getFValue() > nodeFirst.getFValue()) return -1;
            return 0;
        }
    }

    private Moveable moveable;

    public AStarPathFinder(Moveable moveable) {
        this.moveable = moveable;
    }

    @Override
    public Path getPath(Tile start, Tile destination) {
        GameMap map = start.getMap();
        Node[][] nodeMap = mapToNode(map);

        Node startNode = nodeMap[start.getCoordinate().getX()][start.getCoordinate().getY()];
        Node destinationNode = nodeMap[destination.getCoordinate().getX()][destination.getCoordinate().getY()];

        PriorityQueue<Node> open = new PriorityQueue<>(11, new NodeComparator());
        startNode.setGValue(0);
        open.add(startNode);

        Map<Node, Node> nodePath = new HashMap<>();
        HashSet<Node> closed = new HashSet<>();

        if (startNode.equals(destinationNode)) {
            nodePath.put(destinationNode, startNode);
            return findPath(nodePath, destinationNode);
        }

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.equals(destinationNode)) {
                return findPath(nodePath, destinationNode); //TODO: Change destinationNode to current and you can use a generating path
            }

            open.remove(current);
            closed.add(current);

            for (Node neighbor : current.getNeighbors(nodeMap)) {

                if (closed.contains(neighbor)) continue;

                double distanceBetweenNeighAndCurrent = 1;

                distanceBetweenNeighAndCurrent *= (1 / neighbor.tile.getMovementFactor());

                if (distanceBetweenNeighAndCurrent == Double.POSITIVE_INFINITY) {
                    if (neighbor.tile.getMovementFactor() == 0 && CanOpen.class.isAssignableFrom(moveable.getClass())) {
                        Entity openable = DataStructureUtils.findObjectWithClass(neighbor.tile.getOccupiers(), Openable.class);
                        if (openable != null) {
                            distanceBetweenNeighAndCurrent = 1;
                        }
                    }
                }
                double tentativeG = distanceBetweenNeighAndCurrent + current.gValue;

                if (tentativeG < neighbor.gValue) {
                    neighbor.setGValue(tentativeG);
                    neighbor.calculateFValue(destinationNode);

                    nodePath.put(neighbor, current);
                    open.remove(neighbor);
                    open.add(neighbor);
                }
            }
        }

        return null;
    }

    private Node[][] mapToNode(GameMap map) {
        ArrayList<ArrayList<Tile>> tileMap = map.getMapTiles();
        Node[][] nodeMap = new Node[tileMap.size()][tileMap.get(0).size()];
        for (int x = 0; x < tileMap.size(); x++) {
            for (int y = 0; y < tileMap.size(); y++) {
                nodeMap[x][y] = new Node(tileMap.get(x).get(y));
            }
        }

        return nodeMap;
    }

    public Path findPath(Map<Node, Node> nodeMap, Node destination) {
        assert nodeMap != null;
        assert destination != null;

        List<Node> path = new ArrayList<>();
        path.add(destination);
        while (nodeMap.containsKey(destination)) {
            Node destinationHolder = nodeMap.get(destination);
            nodeMap.remove(destination);
            destination = destinationHolder;
            path.add(destination);
        }
        Collections.reverse(path);
        return nodeToTile(path);
    }

    private Path nodeToTile(List<Node> nodes) {
        Path path = new Path();
        for (Node node : nodes) {
            path.add(node.tile);
        }
        return path;
    }

    public class Node {

        final Coordinate coordinate;

        double gValue; //points from start
        double hValue; //linearDistance from target
        double fValue;

        Tile tile;

        Node(Tile tile) {
            this.tile = tile;
            coordinate = tile.getCoordinate();
            gValue = Integer.MAX_VALUE;
        }

        void setGValue(double amount) {
            gValue = amount;
        }

        double calculateHValue(Node destination) {
            Coordinate currentCoordinate = coordinate;
            Coordinate destinationCoordinate = destination.coordinate;
            hValue = (Math.abs(currentCoordinate.getX() - destinationCoordinate.getX()) + Math.abs(currentCoordinate.getY() - destinationCoordinate.getY()));
            return hValue;
        }

        void calculateFValue(Node destination) {
            fValue = gValue + calculateHValue(destination);
        }

        public double getFValue() {
            return fValue;
        }

        @Override
        public boolean equals(Object obj) {
            Node node = (Node) obj;

            if (!coordinate.equals(node.coordinate)) {
                return false;
            }

            return true;
        }

        List<Node> getNeighbors(Node[][] nodeMap) {
            int x = coordinate.getX();
            int y = coordinate.getY();
            List<Node> neighbors = new ArrayList<>();

            for (int i = -1; i <= 1; i += 2) {
                try {
                    neighbors.add(nodeMap[x + i][y]);
                } catch (IndexOutOfBoundsException ignored) {

                }

                try {
                    neighbors.add(nodeMap[x][y + i]);
                } catch (IndexOutOfBoundsException ignored) {

                }
            }

            return neighbors;
        }
    }
}