package in.devdesk.findthefrog.MyPager.Map.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by richardandrews on 10/07/17.
 */

public class FrogFoundResponse implements Serializable {
    @Expose
    private String message;
    @Expose
    private int totalScore;
    @Expose
    private int streakLength;

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getStreakLength() {
        return streakLength;
    }

    public void setStreakLength(int streakLength) {
        this.streakLength = streakLength;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
