package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


class WorldFrame {
    //int width;
    //int height;
    Set<Hallway> hallwaysSet;
    Random rand;
    private TETile[][] tiles;
    DynamicKD pSet;


    WorldFrame(int w, int h, long seed) {
        Set<Room> roomSet;
        int attempts = 50;
        int maxSize = w / 8;
        System.out.println(seed);
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
            int rw = Math.max(2, rand.nextInt(maxSize));
            int rh = Math.max(2, rand.nextInt(maxSize));
            int rx = Math.max(2, rand.nextInt(w - rw - 3) + 1);
            int ry = Math.max(2, rand.nextInt(h - rh - 3) + 1);
            Room newRoom = new Room(rx, ry, rw, rh);


            boolean ok = true;
            for (Room oldRoom : roomSet) {
                if (Room.intersect(oldRoom, newRoom)) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                Room closest = pSet.nearest(rx + ((double) rw / 2), ry + ((double) rh / 2)).room();
                roomSet.add(newRoom);
                if (!newRoom.connected(hallwaysSet)) {

                    Room.connect(closest, newRoom, this);

                }
                newRoom.addPoints(pSet);
            }
        }
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        for (Hallway hall : hallwaysSet) {
            hall.draw(tiles);
        }
        for (Room room : roomSet) {
            room.draw(tiles);
        }
    }


    TETile[][] tiles() {
        return tiles;
    }
}
