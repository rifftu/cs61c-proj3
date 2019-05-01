package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


//import java.awt.event.KeyListener;


class WorldFrame implements Serializable {


    private Creature p1;
    private Creature p2;
    private Set<Room> roomSet;
    private Set<Hallway> hallwaysSet;
    private Set<Creature> animalSet;
    private Random rand;
    private String name1;
    private String name2;
    private Creature[][] animals;
    private DynamicKD pSet;

    //The tiles
    private TETile[][] floortiles;
    private TETile[][] showtiles;

    private int w;
    private int h;

    private int count;


    WorldFrame(int w, int h, long seed, String namePlayer1, String namePlayer2) {

        final int attempts = 50;
        final int maxSize = w / 8;

        this.w = w;
        this.h = h;

        pSet = new DynamicKD();
        floortiles = new TETile[w][h];
        showtiles = new TETile[w][h];
        animals = new Creature[w][h];

        rand = new Random(seed);
        roomSet = new HashSet<>();
        hallwaysSet = new HashSet<>();
        animalSet = new HashSet<>();

        count = 0;
        name1 = namePlayer1;
        name2 = namePlayer2;

        int firstX = 10; int firstY = 10; int firstW = 5; int firstH = 5;
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


        p1 = new Player("P1", namePlayer1, this);
        p2 = new Player("P2", namePlayer2, this);

        root.addCreature(p1, this);
        root.addCreature(p2, this);

        copytiles();

        drawAnimals();

    }

    String getName1() {
        return name1;
    }

    String getName2() {
        return name2;
    }
    void drawRooms() {


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


        Room newRoom = new Room(rx, ry, rw, rh);

        if (rand.nextDouble() < 0.4) {
            newRoom.makeTile(Tileset.GRASS);
        }

        return newRoom;
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
                p1.move(1, Direction.UP);
                break;
            case 'a':
                p1.move(1, Direction.LEFT);
                break;
            case 's':
                p1.move(1, Direction.DOWN);
                break;
            case 'd':
                p1.move(1, Direction.RIGHT);
                break;
            case 'i':
                p2.move(1, Direction.UP);
                break;
            case 'j':
                p2.move(1, Direction.LEFT);
                break;
            case 'k':
                p2.move(1, Direction.DOWN);
                break;
            case 'l':
                p2.move(1, Direction.RIGHT);
                break;
            default:

        }

        Step();

    }


    private void Step() {

        for (Creature cr : animalSet) {
            if (cr.digest > 0) {
                cr.digest--;
            }
        }

        copytiles();
        drawAnimals();


        count++;

    }

    private void copytiles() {
        for (int x = 0; x < w; x++) {
            System.arraycopy(floortiles[x], 0, showtiles[x], 0, h);
        }
    }

    void flip(int x, int y) {
        if (floortiles[x][y] == Tileset.GRASS && animals[x][y] == null) {
            if (rand.nextDouble() < 0.15) {
                Creature newBaddie = new dumbBaddie();
                newBaddie.x = x;
                newBaddie.y = y;
                animalSet.add(newBaddie);
                animals[x][y] = newBaddie;
            }
        }
    }

    int getW() {
        return this.w;
    }

    int getH() {
        return this.h;
    }
}
