import models.PointPackage;
import responses.FetchResponse;
import responses.GameResponse;
import responses.SubmitResponse;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String ApiKey = ""; //TODO Put your teams API Key here
    private static final String Map = "training1"; //TODO Enter what map you want to play, 
     //new ones will be released on considition.com/rules

    public static void main(String[] args) {
        GameLayer gameLayer = new GameLayer(ApiKey);
        GameResponse gameInformation = gameLayer.newGame(Map, ApiKey);
        GreedySolver greedySolver = new GreedySolver(gameInformation.dimensions, gameInformation.vehicle);
        //TODO Create your own solver with SubmitResponse as return value
        ArrayList<PointPackage> solution = greedySolver.Solve();
        SubmitResponse submitResponse = gameLayer.SubmitGame(solution, Map, ApiKey);


        System.out.println("Your score is: " + submitResponse.score);
        System.out.println("The game id is: " + submitResponse.gameId);
        System.out.println( submitResponse.link);

        // Here you can use FetchGame(ApiKey, gameId) to look at a specific game
        FetchResponse aGame = gameLayer.FetchGame(ApiKey, submitResponse.gameId);

        // Or just fetch them all in a list and then maybe compare them and find interesting ones
        ArrayList<FetchResponse> allYourGames = gameLayer.FetchGame(ApiKey);
    }
}
