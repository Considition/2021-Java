package models;

public class Package {
    public int id;
    public int length;
    public int width;
    public int height;
    public int weightClass;
    public int orderClass;


    public Package(int id, int length, int width, int height, int weightClass, int orderClass) {
        this.id = id;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weightClass = weightClass;
        this.orderClass = orderClass;

    }
}