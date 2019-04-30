package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import java.awt.event.KeyListener;


class WorldFrame implements Serializable {


    private Creature P1;
    private Creature P2;
    private Set<Room> roomSet;
    private Set<Hallway> hallwaysSet;
    private Set<Creature> animalSet;
    private Random rand;
    private String name;
    private Creature[][] animals;
    private DynamicKD pSet;

    //The tiles
    private TETile[][] tiles;


    WorldFrame(int w, int h, long seed, String name) {

        final int attempts = 50;
        final int maxSize = w / 8;

        pSet = new DynamicKD();
        tiles = new TETile[w][h];

        rand = new Random(seed);
        int firstX = 10; int firstY = 10; int firstW = 5; int firstH = 5;
        roomSet = new HashSet<>();
        hallwaysSet = new HashSet<>();
        Room root = new Room(firstX, firstY, firstW, firstH);
        roomSet.add(root);
        root.addPoints(pSet);

        for (int i = 0; i < attempts; i++) {

            Room newRoom = randomRoom(w, h, maxSize);

            if (hasOverlap(newRoom)) {
                continue;
            }

            placeRoom(newRoom);
        }

        drawRooms(w, h);

        P1 = new Player(name);
        P2 = new Player("P2");

    }

    public void drawRooms(int w, int h) {

        clearTiles(w, h);

        for (Hallway hall : hallwaysSet) {
            Room.draw(tiles, hall);
        }
        for (Room room : roomSet) {
            Room.draw(tiles, room);
        }
    }

    private void clearTiles(int w, int h) {
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {

                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    private Room randomRoom(int w, int h, int maxSize) {
        int rw = Math.max(2, rand.nextInt(maxSize));
        int rh = Math.max(2, rand.nextInt(maxSize));
        int rx = Math.max(2, rand.nextInt(w - rw - 3) + 1);
        int ry = Math.max(2, rand.nextInt(h - rh - 3) + 1);
        return new Room(rx, ry, rw, rh);
    }

    private boolean hasOverlap(Room newRoom) {
        for (Room oldRoom : roomSet) {
            if (Room.intersect(oldRoom, newRoom)) {
                return true;
            }
        }
        return false;
    }

    private void placeRoom(Room newRoom) {
        roomSet.add(newRoom);
        if (!newRoom.connected(hallwaysSet)) {
            Room.connect(closest(newRoom), newRoom, this);
        }
        newRoom.addPoints(pSet);
    }

    private Room closest(Room newRoom) {
        return pSet.nearest(newRoom.midX(), newRoom.midY()).room();
    }

    TETile[][] tiles() {
        return tiles;
    }

    Creature[][] animals() {
        return animals;
    }

    Set<Hallway> hallwaysSet() {
        return hallwaysSet;
    }
    Set<Creature> animalSet() {
        return animalSet;
    }
    Random rand() {
        return rand;
    }
    DynamicKD pSet() {
        return pSet;
    }

    void keyCatcher(char c) {
        switch (c) {
            case 'W':
                P1.move(1, Direction.UP);
            case 'A':
                P1.move(1, Direction.LEFT);
            case 'S':
                P1.move(1, Direction.DOWN);
            case 'D':
                P1.move(1, Direction.RIGHT);
            case 'I':
                P1.move(1, Direction.UP);
            case 'J':
                P1.move(1, Direction.LEFT);
            case 'K':
                P1.move(1, Direction.DOWN);
            case 'L':
                P1.move(1, Direction.RIGHT);
            default:

        }
    }

}
