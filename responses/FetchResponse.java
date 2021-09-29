package responses;

import java.util.Date;

public class FetchResponse {

    public String gameId;
    public String TeamName;
    public int score;
    public String map;
    public String completedAtTime;
    public String solution;


    public FetchResponse(String gameId, String teamId, int score, String map, String completedAtTime, String solution){
        this.gameId = gameId;
        this.TeamName = teamId;
        this.score = score;
        this.map = map;
        this.completedAtTime = completedAtTime;
        this.solution = solution;
    }
}
