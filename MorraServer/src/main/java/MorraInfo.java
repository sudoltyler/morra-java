import java.io.Serializable;

public class MorraInfo implements Serializable {
    Boolean startGame, winner, ready;
    int p1Points, p2Points, play, guess, clientNum;
    String p1Plays, p2Plays, message;

    public MorraInfo(int client) {
        p1Points = p2Points = play = guess = 0;
        p1Plays = p2Plays = "";
        startGame = winner = ready = false;
        clientNum = client;
    }

    public void calculatePoints() {
    }

    public Boolean checkWinner() {
        return false;
    }
}
