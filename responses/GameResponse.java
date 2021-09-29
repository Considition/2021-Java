package responses;

import models.Package;
import models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class GameResponse {
    public String mapName;
    public Vehicle vehicle;
    public ArrayList<Package> dimensions;

    public GameResponse(String mapName, Vehicle vehicle, ArrayList<Package> dimensions){
        this.mapName = mapName;
        this.vehicle = vehicle;
        this.dimensions = dimensions;
    }
}
