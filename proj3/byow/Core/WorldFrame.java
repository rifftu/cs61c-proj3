package byow.Core;

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

    WorldFrame(int w, int h, int seed) {
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
        Room root = new Room(firstX, firstY, firstW, firstH);
        roomQueue.add(root);

        /*
        while (!roomQueue.isEmpty()) {
            roomQueue.remove().expand(this);
        }
        */
        Room previous = root;
        for (int i = 0; i < attempts; i++) {
            int rw = rand.nextInt(MaxSize);
            int rh = rand.nextInt(MaxSize);
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
                Room.connect(previous, newRoom);
                previous = newRoom;
            }
        }

    }



    TETile[][] tiles() {
        return null;
    }
    private boolean filled() {
        return false;
    }
}
