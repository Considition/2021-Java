package responses;

public class SubmitResponse {
    public int score;
    public String gameId;
    public boolean valid;
    public String link;


    public SubmitResponse(int score, String gameId, boolean valid, String link){
    this.score = score;
    this.gameId = gameId;
    this.valid = valid;
    this.link = link;
    }
}


