package in.devdesk.findthefrog.MyPager.Map.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by richardandrews on 09/07/17.
 */

public class MapHomeUpdateResponse implements Serializable{
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
