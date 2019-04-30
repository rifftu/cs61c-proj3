package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import java.awt.event.KeyListener;


class WorldFrame {


    Creature P1;
    Creature P2;
    private Set<Room> roomSet;
    Set<Hallway> hallwaysSet;
    Set<Creature> animalSet;
    Random rand;

    private Creature[][] animals;
    DynamicKD pSet;

    //The tiles
    private TETile[][] tiles;


    WorldFrame(int w, int h, long seed) {

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

        P1 = new Player();
        P2 = new Player();

    }

    private void drawRooms(int w, int h) {

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

    void keyCatcher(char c) {
        switch (c) {
            case 'w':
                P1.move(1, Direction.UP);
            case 'a':
                P1.move(1, Direction.LEFT);
            case 's':
                P1.move(1, Direction.DOWN);
            case 'd':
                P1.move(1, Direction.RIGHT);
            case 'i':
                P1.move(1, Direction.UP);
            case 'j':
                P1.move(1, Direction.LEFT);
            case 'k':
                P1.move(1, Direction.DOWN);
            case 'l':
                P1.move(1, Direction.RIGHT);
            default:

        }
    }

}
