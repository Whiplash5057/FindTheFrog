package in.devdesk.findthefrog.HeLpEr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by richardandrews on 06/07/17.
 */

public class RestManager {


    private WebServices mItemService;


    public WebServices getmItemService()
    {
        if(mItemService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mItemService = retrofit.create(WebServices.class);
        }

        return mItemService;
    }


}