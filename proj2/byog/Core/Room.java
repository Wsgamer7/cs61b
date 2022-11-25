package byog.Core;

import java.util.ArrayList;

public class Room {
    int[][] typeMatrix;
    Position origin;
    Position fasterP;
    int weight;
    int height;
    /* the constructor of a room*/
    Room(Position origin, int weight, int height){
        this.origin = origin;
        typeMatrix = origin.typeMatrix;
        fasterP = origin.moveTo(weight, height);
        this.weight = weight;
        this.height = height;
    }
    /* return true if this room in the world*/
    boolean inWorld(){
        return origin.inWorld() && fasterP.inWorld();
    }
    /* return the distance bettwen this and room */
    int distanceWith(Room room) {
        int distanceX = Math.abs(origin.xPos - room.origin.xPos);
        int distanceY = Math.abs(origin.yPos - room.origin.yPos);
        return distanceX + distanceY;
    }
    /* find the most closed room in the arrayList */
    Room closedRoomIn(ArrayList<Room> roomArrayList) {
        Room mostClosedRoom = roomArrayList.get(0);
        for (Room room : roomArrayList) {
            if (this.distanceWith(mostClosedRoom) > this.distanceWith(room)) {
                mostClosedRoom = room;
            }
        }
        return mostClosedRoom;
    }
    /* return ture if this room overlap room1*/
    boolean overLap(Room room1){
        return origin.isContained(room1) || fasterP.isContained(room1);
    }
    /* draw wall(1) for this room*/
    void drawWall(){
        Position a = origin;
        a.drawARow(1, weight);
        a.drawAColum(1, height);
        Position b = a.moveTo(weight, 0);
        b.drawAColum(1, height);
        Position c = a.moveTo(0, height);
        c.drawARow(1, weight);
    }
    /* draw floor(2) for this room*/
    void drawFloor(){
        Position start = origin.moveTo(1, 1);
        for(int i = 0; i < height - 1; i++) {
            start.drawARow(2, weight - 2);
            start = start.moveTo(0, 1);
        }
    }
    /* draw this room*/
    void drawRoom(){
        if (height > 1 && weight > 1) {
            drawFloor();
            drawWall();
        }
    }
}
