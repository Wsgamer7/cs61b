package byog.Core;

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
    /* return ture if this room overlap room1*/
    boolean overLap(Room room1){
        return origin.isContain(room1) && fasterP.isContain(room1);
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
