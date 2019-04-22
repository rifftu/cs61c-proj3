package byow.Core;

public class Hallway extends Room {
    private Direction dir;
    private int length;
    private int endX;
    private int endY;
    private final int width = 1;

    Hallway(int x, int y, Direction d, int l) {
        dir = d;
        length = l;
        switch (d) {
            case UP:
                endY = y + l;
                endX = x;
            case DOWN:
                endY = y - l;
                endX = x;
            case LEFT:
                endX = x - l;
                endY = y;
            default:
                endX = x + l;
                endY = y;
        }
    }

    /*
    @Override
    void expand(WorldFrame world) {

    }

    private void newRoom(WorldFrame world) {

    }
    private void newHall(WorldFrame world) {

    }
    */

}
