package byog.Core;

import java.util.ArrayList;
import java.util.Random;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldGenerator {
    Random RANDOM;
    TETile[][] world;
    int weightOfW;
    int heightOfW;
    int minLengthOfRoom = 3;
    int maxLengthOfRoom = 7;
    int[][] typeMatrix;
    TETile[] mapToTile = StyleSet.defaultStyle;
    WorldGenerator(long seed, TETile[][] world){
        RANDOM = new Random(seed);
        this.world = world;
        weightOfW = world.length;
        heightOfW = world[0].length;
        typeMatrix = new int[weightOfW][heightOfW];
    }
    void toGenerator() {
        Position testOrigin = new Position(10,10, this);
        Room testRoom= new Room(testOrigin, 2, 2);
        Position testOrigin1 = testOrigin.moveTo(9, 9);
        Room testRoom1 = new Room(testOrigin1, 5, 2);
        Position testOrigin2 = testOrigin.moveTo(- 9, - 9);
        Room testRoom2 = new Room(testOrigin2, 2, 2);
        testRoom.drawRoom();
        testRoom1.drawRoom();
        testRoom2.drawRoom();
        Hallway hw1 = getHWFromTwoRooms(testRoom1, testRoom);
        Hallway hw2 = getHWFromTwoRooms(testRoom, testRoom2);
//        Hallway hw3 = getHWFromTwoRooms(testRoom1, testRoom2);
        hw1.drawLHW();
        hw2.drawLHW();
//        hw3.drawLHW();
        transToWorld();
    }
    /* trans integer in typeMatrix to TETile in world*/
    void transToWorld(){
        int typeOfTile = 0;
        for (int i = 0; i < weightOfW; i++) {
            for (int j = 0; j < heightOfW; j++) {
                typeOfTile = typeMatrix[i][j];
                world[i][j]= mapToTile[typeOfTile];
            }
        }
    }
    /* get a Random room in the world */
    Room getARandomRoom() {
        int xOrigin = RANDOM.nextInt(weightOfW);
        int yOrigin = RANDOM.nextInt(heightOfW);
        Position origin = new Position(xOrigin, yOrigin, this);
        int weightOfRoom = RANDOM.nextInt(minLengthOfRoom, maxLengthOfRoom);
        int heightOfRoom = RANDOM.nextInt(minLengthOfRoom, maxLengthOfRoom);
        return new Room(origin, weightOfRoom, heightOfRoom);
    }
    /* get a position in a room */
    Position getAPosInRoom(Room room){
        int diffX = RANDOM.nextInt(1, room.weight);
        int diffY = RANDOM.nextInt(1, room.height);
        return room.origin.moveTo(diffX, diffY);
    }
    Hallway getHWFromTwoRooms(Room room1, Room room2) {
        Position pos1 = getAPosInRoom(room1);
        Position pos2 = getAPosInRoom(room2);
        return new Hallway(pos1, pos2);
    }
}
