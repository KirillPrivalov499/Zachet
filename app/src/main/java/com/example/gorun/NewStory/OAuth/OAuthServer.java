package com.example.gorun.NewStory.OAuth;

import com.example.gorun.NewStory.ui.StravaActivity;
import com.example.gorun.NewStory.models.OAuthToken;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class OAuthServer {
    private static final String siteURL = "https://www.strava.com/";
    private static String code = StravaActivity.Authcode;

    public static OAuthServerIntface oAuthServerIntface = null;
    public static OAuthServerIntface getoAuthServerIntface(){
        if(oAuthServerIntface == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(siteURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            oAuthServerIntface = retrofit.create(OAuthServerIntface.class);
        }
        return oAuthServerIntface;
    }
    public interface OAuthServerIntface {

        @POST("oauth/token")
        @FormUrlEncoded
        Call<OAuthToken> getAccessToken(
                @Field("code") String code,
                @Field("client_id") String client_id,
                @Field("client_secret") String client_secret,
                @Field("grant_type") String grant_type
        );

    }
}
