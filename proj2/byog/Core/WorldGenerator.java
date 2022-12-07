package byog.Core;

import java.util.*;
import java.io.*;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldGenerator implements Serializable {
    Random RANDOM;
    TETile[][] world;
    Player player;
    int xOffSet;
    int yOffSet;
    final int weightOfW;
    final int heightOfW;
    private final int numberOfRoom;
    ArrayList<Room> allRooms;
    Position positionOfDoor;
    Position positionOfKey;
    int numOfOpenedDoor;
    int minLengthOfRoom = 3;
    int maxLengthOfRoom = 9;
    int[][] typeMatrix;
    TETile[] mapToTile = StyleSet.getAStyle("default");
    Map<Integer, String> typeDescription = new HashMap<>();
    WorldGenerator(long seed, TETile[][] world, int xOffSet, int yOffSet) {
        RANDOM = new Random(seed);
        this.world = world;
        this.xOffSet = xOffSet;
        this.yOffSet = yOffSet;
        weightOfW = world.length;
        heightOfW = world[0].length;
        typeMatrix = new int[weightOfW][heightOfW];
        double numberOfRoomDouble = ((double) weightOfW * heightOfW) / (60 * 40) * 24;
        numberOfRoom = (int) Math.floor(numberOfRoomDouble);
        intiTypeDescription();
    }
    private void intiTypeDescription() {
        typeDescription.put(0, "Nothing");
        typeDescription.put(1, "Wall");
        typeDescription.put(2, "Floor");
        typeDescription.put(3, "LockedDoor");
        typeDescription.put(4, "UnlockedDoor");
    }
    void toGenerator() {
        getAllRoomsRandomly(numberOfRoom);
        ArrayList<Room> allRoomsCopy = new ArrayList<>(allRooms);
        drawListOfRooms(allRoomsCopy);
        drawAllHallWall(allRoomsCopy);
        getDoorAndDraw();
        transToWorld();
        intiKey();
        intiPlayer();
    }

    private void drawATile(TETile tile, Position position) {
        int x = position.xPos;
        int y = position.yPos;
        world[x][y] = tile;
    }
    /* restore a tile after player move away */
    void backAPoint(Position position) {
        int x = position.xPos;
        int y = position.yPos;
        TETile drawStored = mapToTile[typeMatrix[x][y]];
        drawATile(drawStored, position);
    }
    /* trans integer in typeMatrix to TETile in world*/
    private void transToWorld() {
        int typeOfTile = 0;
        for (int i = 0; i < weightOfW; i++) {
            for (int j = 0; j < heightOfW; j++) {
                typeOfTile = typeMatrix[i][j];
                world[i][j] = mapToTile[typeOfTile];
            }
        }
    }
    private Room getARoomFromAllRoom() {
        int randomIndex = RANDOM.nextInt(allRooms.size());
        return allRooms.get(randomIndex);
    }
    private Position getAPositionInALlRooms() {
        Room randomRoom = getARoomFromAllRoom();
        return getAPosInRoom(randomRoom);
    }
    private void intiKey() {
        //set up the key
        positionOfKey = getAPositionInALlRooms();
        if (numOfOpenedDoor == 0) {
            drawATile(Tileset.MOUNTAIN, positionOfKey);
        }
    }
    private void intiPlayer() {
        //set up the player
        player = new Player(getAPositionInALlRooms());
        player.drawPlayer();
    }
    /* get a Random room in the world */
    private Room getARandomRoom() {
        int xOrigin = RANDOM.nextInt(weightOfW);
        int yOrigin = RANDOM.nextInt(heightOfW);
        Position origin = new Position(xOrigin, yOrigin, this);
        int weightOfRoom = minLengthOfRoom + RANDOM.nextInt(maxLengthOfRoom - minLengthOfRoom);
        int heightOfRoom = minLengthOfRoom + RANDOM.nextInt(maxLengthOfRoom - minLengthOfRoom);
        return new Room(origin, weightOfRoom, heightOfRoom);
    }
    private void getAllRoomsRandomly(int number) {
        ArrayList<Room> allRooms1 = new ArrayList<>();
        while (number > 0) {
            int countOfFailContinue = 0;
            Room roomAdded = getARandomRoom();
            if (checkThNewRoom(allRooms1, roomAdded)) {
                allRooms1.add(roomAdded);
                number -= 1;
                countOfFailContinue = 0;
            } else {
                countOfFailContinue += 1;
                if (countOfFailContinue > 4) {
                    break;
                }
            }
        }
        this.allRooms = allRooms1;
    }
    /* drawAllHallWaLL destructively, it describes room as unconnected or connected */
    private void drawAllHallWall(ArrayList<Room> unconnectedRooms) {
        Room lastConnected = unconnectedRooms.get(0); //select a room as the start randomly
        unconnectedRooms.remove(lastConnected);
        while (unconnectedRooms.size() > 0) {
            Room willConnect = lastConnected.closedRoomIn(unconnectedRooms);
            drawHWFromTwoRooms(lastConnected, willConnect);
            lastConnected = willConnect;
            unconnectedRooms.remove(willConnect);
        }
    }
    /* take a arraylistOfRooms and draw all room in it */
    private void drawListOfRooms(ArrayList<Room> arrayListOfRoom) {
        for (Room room : arrayListOfRoom) {
            room.drawRoom();
        }
    }
    /* check the new room is in the world and not overlap with other room*/
    private boolean checkThNewRoom(ArrayList<Room> oldList, Room newRoom) {
        // check the new room is in the world
        if (!newRoom.inWorld()) {
            return false;
        }
        // check the new room weather overlap any room in oldList
        for (Room room : oldList) {
            if (newRoom.overLap(room)) {
                return false;
            }
        }
        return true;
    }
    /* get a position in a room */
    private Position getAPosInRoom(Room room) {
        int diffX = RANDOM.nextInt(1, room.weight);
        int diffY = RANDOM.nextInt(1, room.height);
        return room.origin.moveTo(diffX, diffY);
    }
    private Position getAPosOnWallOfRoom(Room room) {
        Position randomP = getAPosInRoom(room);
        Position toReturn;
        int toWard = RANDOM.nextInt(4);
        if (toWard == 0) {
            toReturn = new Position(randomP.xPos, room.origin.yPos, this); // Down
        } else if (toWard == 1) {
            toReturn = new Position(room.fasterP.xPos, randomP.yPos, this); // Right
        } else if (toWard == 2) {
            toReturn = new Position(randomP.xPos, room.fasterP.yPos, this); // Up
        } else {
            toReturn = new Position(room.origin.xPos, randomP.yPos, this); //Left
        }
        if (typeMatrix[toReturn.xPos][toReturn.yPos] != 1) {
            return getAPosOnWallOfRoom(room);
        }
        return toReturn;
    }
    private void getDoorAndDraw() {
        int indexOfRandomRoom = RANDOM.nextInt(allRooms.size());
        Room randomRoom = allRooms.get(indexOfRandomRoom);
        positionOfDoor = getAPosOnWallOfRoom(randomRoom);
        numOfOpenedDoor = RANDOM.nextInt(2);
        drawTheDoor();
    }
    private void drawTheDoor() {
        typeMatrix[positionOfDoor.xPos][positionOfDoor.yPos] = 3 + numOfOpenedDoor;
        world[positionOfDoor.xPos][positionOfDoor.yPos] = mapToTile[3 + numOfOpenedDoor];
    }
    void openTheDoor() {
        numOfOpenedDoor = 1;
        drawTheDoor();
    }
    private Hallway getHWFromTwoRooms(Room room1, Room room2) {
        Position pos1 = getAPosInRoom(room1);
        Position pos2 = getAPosInRoom(room2);
        return new Hallway(pos1, pos2);
    }
    private void drawHWFromTwoRooms(Room room1, Room room2) {
        Hallway hw = getHWFromTwoRooms(room1, room2);
        int wayOfDrawing = RANDOM.nextInt(2);
        hw.drawHW(wayOfDrawing);
    }
}
