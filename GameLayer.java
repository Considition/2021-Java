import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import responses.FetchResponse;
import responses.GameResponse;
import responses.SubmitResponse;

import java.util.ArrayList;

public class GameLayer {
    private String apiKey;
    private static final Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }


    public GameLayer(String apiKey){
        this.apiKey = apiKey;
    }

    /**
     * Creates a new game.
     * @param map Specify which map to play on
     * @return The game specifics
     */
    public GameResponse newGame(String map, String apiKey) {
         var state =  Api.NewGame(map, apiKey);

         return state;
    }

    /**
     * Submit your solution to be scored
     * @param solution list of packages with your solution
     * @param mapName string of chosen map
     * @param apiKey your teams api-key
     * @return The game specifics
     */
    public SubmitResponse SubmitGame (ArrayList<PointPackage> solution, String mapName, String apiKey){
        var state = Api.SubmitGame( solution,mapName, apiKey);
        return state;
    }

    public ArrayList<FetchResponse> FetchGame(String apiKey){
        var state = Api.FetchGame(apiKey);
        return state;
    }

    public FetchResponse FetchGame(String apiKey, String gameId){
        var state = Api.FetchGame(apiKey, gameId);
        return state;
    }
}
