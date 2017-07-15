package in.devdesk.findthefrog.MyPager.Other;

import java.util.List;

import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsResponse;

/**
 * Created by richardandrews on 11/07/17.
 */

public interface Other_MVP {
    public interface Model
    {
        public void acceptUserDataForFrogList(String username, String authToken);
    }

    public interface Presenter
    {
        public void sendUserDataForFrogList(String username, String authToken);
        public void returnUserDataForFrogListResponse(String message, List<AllFrogsResponse.Response> responsePojo, int streakLength,int totalScore,String nextWalkDate);
    }

    public interface View
    {
        public void showUserDataForFrogList(String message, List<AllFrogsResponse.Response> responsePojo, int streakLength,int totalScore, String nextWalkDate);
    }
}
