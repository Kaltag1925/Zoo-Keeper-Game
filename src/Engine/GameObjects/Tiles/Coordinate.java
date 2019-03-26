package Engine.GameObjects.Tiles;

import CustomMisc.ICreateCopy;

public class Coordinate implements ICreateCopy {

    private int x; // left right
    private int y; // front back

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //<editor-fold desc="Get Methods">
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    //</editor-fold>

    //<editor-fold desc="Set Methods">
    public void setX(int x) {
        this.x = x;
    }

    public void addX(int x) {
        this.x += x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addY(int y) {
        this.y += y;
    }
    //</editor-fold>

    @Override
    public ICreateCopy createCopy() {
        return new Coordinate(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//
//        if (obj == null) {
//            return false;
//        }
//
//        if (!this.getClass().equals(obj.getClass())) {
//            return false;
//        }

        Coordinate coordinate = (Coordinate) obj;

        if (x != coordinate.x) {
            return false;
        }

        if (y != coordinate.y) {
            return false;
        }

        return true;
    }

    public static double linearDistance(Coordinate first, Coordinate other) {
        return Math.sqrt(Math.pow(other.x - first.x, 2) + Math.pow(other.y - first.y, 2));
    }

    public int gridDistance(Coordinate other) {
        return Math.abs(other.x - x) + Math.abs(other.y - y);
    }
}
