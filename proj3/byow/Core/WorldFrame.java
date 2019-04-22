package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.*;


class WorldFrame {
    int width;
    int height;
    Queue<Room> roomQueue;
    Set<Room> roomSet;
    Set<Hallway> hallwaysSet;
    Random rand;
    int MaxSize;
    final int attempts = 50;
    TETile[][] tiles;


    WorldFrame(int w, int h, int seed) {
        tiles = new TETile[w][h];
        width = w;
        height = h;
        MaxSize = width / 8;
        rand = new Random(seed);
        //TODO
        int firstX = 10;
        int firstY = 10;
        int firstW = 5;
        int firstH = 5;
        roomQueue = new ArrayDeque<>();
        roomSet = new HashSet<>();
        hallwaysSet = new HashSet<>();
        Room root = new Room(firstX, firstY, firstW, firstH, this);
        roomQueue.add(root);

        /*
        while (!roomQueue.isEmpty()) {
            roomQueue.remove().expand(this);
        }
        */



        Room previous = root;
        for (int i = 0; i < attempts; i++) {
            int rw = Math.max(2, rand.nextInt(MaxSize));
            int rh = Math.max(2, rand.nextInt(MaxSize));
            int rx = rand.nextInt(width - rw - 2) + 1;
            int ry = rand.nextInt(height - rh - 2) + 1;
            Room newRoom = new Room(rx, ry, rw, rh, this);
            Boolean ok = true;
            for (Room oldRoom : roomSet) {
                if (Room.intersect(oldRoom, newRoom)) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                roomSet.add(newRoom);
                Room.connect(previous, newRoom, this);
                previous = newRoom;
            }
        }
        /*
        for (Hallway hall : hallwaysSet) {
            hall.draw(tiles);
        }
        */
        for (Room room : roomSet) {
            room.draw(tiles);
        }

    }



    TETile[][] tiles() {
        return null;
    }
    private boolean filled() {
        return false;
    }
}
