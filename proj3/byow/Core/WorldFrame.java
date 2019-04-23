package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

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
    DynamicKD pSet;


    WorldFrame(int w, int h, int seed) {

        pSet = new DynamicKD();
        tiles = new TETile[w][h];
        width = w;
        height = h;
        MaxSize = width / 5;
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
        roomSet.add(root);
        int roomCount = 0;
        int hallCount = 0;

        /*
        while (!roomQueue.isEmpty()) {
            roomQueue.remove().expand(this);
        }
        */



        Room previous = root;
        for (int i = 0; i < attempts; i++) {
            int rw = Math.max(2, rand.nextInt(MaxSize));
            int rh = Math.max(2, rand.nextInt(MaxSize));
            int rx = Math.max(2, rand.nextInt(width - rw - 3) + 1);
            int ry = Math.max(2, rand.nextInt(height - rh - 3) + 1);
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
                roomCount++;
                if (!newRoom.connected(hallwaysSet)) {
                    Room.connect(previous, newRoom, this);
                    hallCount++;
                } else {
                    System.out.println("hall avoided");
                }
                Room.connect(previous, newRoom, this);
                previous = newRoom;
            }
        }
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }


        for (Hallway hall : hallwaysSet) {
            //System.out.println("start x "+ hall.getStartX() + " start y " +hall.getStartY()+
            //            " h "+ hall.getL() + " Direction "+ hall.getD());
            hall.draw(tiles);
        }



        for (Room room : roomSet) {
            //System.out.println("start x "+ room.getX() + " start y " +room.getY()+
            //                "with "+ room.getW());
            room.draw(tiles);
        }

        System.out.println("rooms: " + roomCount);
        System.out.println("halls: " + hallCount);
    }



    TETile[][] tiles() {
        return tiles;
    }
    private boolean filled() {
        return false;
    }
}
