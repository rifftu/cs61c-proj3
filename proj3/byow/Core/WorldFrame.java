package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class WorldFrame {


    private Creature P1;
    private Creature P2;
    private Set<Room> roomSet;
    private Set<Hallway> hallwaysSet;
    private Set<Creature> animalSet;
    private Random rand;

    private Creature[][] animals;
    private DynamicKD pSet;

    //The tiles
    private TETile[][] floortiles;
    private TETile[][] showtiles;

    private int w;
    private int h;


    WorldFrame(int w, int h, long seed) {

        final int attempts = 50;
        final int maxSize = w / 8;

        this.w = w;
        this.h = h;

        pSet = new DynamicKD();
        floortiles = new TETile[w][h];
        showtiles = new TETile[w][h];
        animals = new Creature[w][h];

        rand = new Random(seed);
        int firstX = 10; int firstY = 10; int firstW = 5; int firstH = 5;
        roomSet = new HashSet<>();
        hallwaysSet = new HashSet<>();
        animalSet = new HashSet<>();
        Room root = new Room(firstX, firstY, firstW, firstH);
        roomSet.add(root);
        root.addPoints(pSet);

        for (int i = 0; i < attempts; i++) {

            Room newRoom = randomRoom(maxSize);

            if (hasOverlap(newRoom)) {
                continue;
            }

            placeRoom(newRoom);
        }

        drawRooms();


        P1 = new Player("P1", this);
        P2 = new Player("P2", this);

        root.addCreature(P1, this);
        root.addCreature(P2, this);

        copytiles();

        drawAnimals();


    }

    private void drawRooms() {

        clearTiles();

        for (Hallway hall : hallwaysSet) {
            Room.draw(floortiles, hall);
        }
        for (Room room : roomSet) {
            Room.draw(floortiles, room);
        }


    }

    private void drawAnimals() {
        for (Creature cr : animalSet) {
            int x = cr.getX();
            int y = cr.getY();
            if (floortiles[x][y] == Tileset.WALL) {
                throw new RuntimeException("This " + cr.name() + " is in a wall wtf");
            } else {
                showtiles[x][y] = cr.tile();
            }
        }
    }

    private void clearTiles() {
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                floortiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    private Room randomRoom(int maxSize) {
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
        return showtiles;
    }


    TETile[][] getFloortiles() {
        return floortiles;
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
            case 'w':
                P1.move(1, Direction.UP);
                System.out.println("W");
            case 'a':
                P1.move(1, Direction.LEFT);
                System.out.println("A");

            case 's':
                P1.move(1, Direction.DOWN);
                System.out.println("S");

            case 'd':
                P1.move(1, Direction.RIGHT);
                System.out.println("D");

            case 'i':
                P2.move(1, Direction.UP);
            case 'j':
                P2.move(1, Direction.LEFT);
            case 'k':
                P2.move(1, Direction.DOWN);
            case 'l':
                P2.move(1, Direction.RIGHT);
            default:

        }

        step();

    }

    private void step() {
        copytiles();
        drawAnimals();
    }

    private void copytiles() {
        for (int x = 0; x < w; x++) {
            System.arraycopy(floortiles[x], 0, showtiles[x], 0, h);
        }
    }

    void flip(int x, int y) {

    }
}
