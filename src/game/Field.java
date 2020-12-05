package game;

public class Field {
    String type;
    int dimensions;

    public Field (String type, int dimensions){
        this.type = type;
        this.dimensions = dimensions;
    }
    public Field (String type){
        this.type = type;
    }

    public static Field createWater(String type){
        Field water = new Field(type);
        return water;
    }

    public static Field createMiss(String type){
        Field miss = new Field(type);
        return miss;
    }

    public static Field createHit(String type){
        Field hit = new Field(type);
        return hit;
    }

    public static Field createShip(String type, int dimensions){
        Field ship = new Field(type, dimensions);
        return ship;
    }

    public String getType(){
        return this.type;
    }

    public int getDimensions(){
        return this.dimensions;
    }
}
