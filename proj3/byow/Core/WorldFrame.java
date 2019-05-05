package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.*;


//import java.awt.event.KeyListener;


class WorldFrame implements Serializable {


    private Creature p1;
    private Creature p2;
    private Set<Room> roomSet;
    private Set<Hallway> hallwaysSet;
    private Set<Creature> animalSet;
    private Set<List<IPoint>> pathSet;
    private Random rand;
    private String name1;
    private String name2;
    private DynamicKD pSet;
    private ArrayList<Room> roomArray;

    //The tiles
    private TETile[][] floortiles;
    private TETile[][] showtiles;
    private Creature[][] animals;
    private IPoint[][] nodes;

    private int w;
    private int h;

    private int count;
    private long seed;
    private String action;
    private  String winner;
    boolean gameOver;
    boolean thereIsWinner;

    private TileGraph graph;

    private boolean showPaths;
    Set<Creature> killList;

    WorldFrame(int w, int h, long seed, String namePlayer1, String namePlayer2) {

        final int attempts = 50;
        final int maxSize = w / 8;

        this.w = w;
        this.h = h;
        this.seed = seed;

        pSet = new DynamicKD();
        floortiles = new TETile[w][h];
        showtiles = new TETile[w][h];
        animals = new Creature[w][h];
        nodes = new IPoint[w][h];


        rand = new Random(seed);
        roomSet = new HashSet<>();
        hallwaysSet = new HashSet<>();
        animalSet = new HashSet<>();
        pathSet = new HashSet<>();
        roomArray = new ArrayList<>();
        killList = new HashSet<>();

        count = 0;
        name1 = namePlayer1;
        name2 = namePlayer2;
        action = "";
        gameOver = false;
        thereIsWinner = false;

        graph = new TileGraph();

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

        //root.addCreature(p1, this);
        //root.addCreature(p2, this);
        Room room = randRoom(roomArray);
        room.addCreature(p1, this);
        room = randRoom(roomArray);
        room.addCreature(p2, this);

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
            Room.draw(floortiles, hall, this);
        }
        for (Room room : roomSet) {
            Room.draw(floortiles, room, this);
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
                nodes[x][y] = new IPoint(x, y, floortiles, nodes);
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
        roomArray.add(newRoom);
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

    long getSeed() {
        return seed;
    }

    String getAction() {
        return action;
    }
    void setAction(String act) {
        action = act;
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
            case 't':
                //p1.path(p2.x, p2.y);
                showPaths = !showPaths;
                drawRooms();
                copytiles();
                if (showPaths) {
                    drawPaths();
                }
                drawAnimals();
                return;
            default:

        }

        step();

    }

    void setGameOver() {
        this.gameOver = true;
    }

    boolean getGameover() {
        return this.gameOver;
    }

    void setThereIsWinner() {
        this.thereIsWinner = true;
    }

    boolean getThereIsWinner() {
        return this.thereIsWinner;
    }

    private void step() {

        for (Creature cr : animalSet) {

            if (cr instanceof SmartBaddie && cr.alive()) {
                SmartBaddie sCr = (SmartBaddie) cr;
                Creature targ;
                if (p1.alive()) {
                    if (!p2.alive()) {
                        targ = p1;
                    } else {
                        //compare distances
                        int d1 = (int) (Math.pow(p1.x - sCr.x, 2) + Math.pow(p1.y - sCr.y, 2));
                        int d2 = (int) (Math.pow(p2.x - sCr.x, 2) + Math.pow(p2.y - sCr.y, 2));
                        if (d1 < d2) {
                            targ = p1;
                        } else {
                            targ = p2;
                        }
                    }
                } else if (p2.alive()) {
                    targ = p2;
                } else {
                    break;
                }
                if (count - sCr.birthday >= 1) {
                    sCr.path(targ.x, targ.y);
                }
                if ((count - sCr.birthday) % 2 == 1) {
                    sCr.move(1, sCr.guide);
                }
            }

            if (cr.digest > 0) {
                cr.digest--;
            }
        }

        for (Creature cr : killList) {
            cr.die();
        }


        copytiles();
        if (showPaths) {
            drawPaths();
        }
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
                Creature newBaddie;
                if (rand.nextDouble() < 0.3) {
                    newBaddie = new SmartBaddie(getCount(), this);
                } else {
                    newBaddie = new DumbBaddie();
                }
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

    IPoint[][] nodes() {
        return nodes;
    }

    TileGraph graph() {
        return graph;
    }

    void drawPaths() {
        for (Creature cr : animalSet) {
            List<IPoint> path = cr.path;
            if (path != null && path.size() > 1) {
                for (IPoint p : path) {
                    showtiles[p.x][p.y] = Tileset.PATH;
                }
            }
        }
    }

    Room randRoom(ArrayList<Room> ro) {
        int i = RandomUtils.uniform(rand, ro.size());
        while (ro.get(i).tile(this) == Tileset.GRASS) {
            i = RandomUtils.uniform(rand, ro.size());
        }
        return ro.get(i);
    }

    int getCount() {
        return count;
    }

    TETile changingFloor() {
        if (showPaths) {
            System.out.println("YAY");
            return Tileset.XRAY;
        } else {
            return Tileset.FLOOR;
        }
    }

}
