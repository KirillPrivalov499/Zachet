package com.example.gorun.NewStory.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gorun.NewStory.OAuth.OAuthServer;
import com.example.gorun.NewStory.models.OAuthToken;
import com.example.gorun.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StravaActivity extends AppCompatActivity {


    public static final String CLIENT_ID = "44988";
    static final String CLIENT_SECRET = "cba9e04f158bb6f5cd877806812917368f602c0f";
    private static final String OAUTH_SCOPE = "profile:read_all,activity:read_all,activity:write,read_all";
    private static final String CODE = "code";
    private static final String REDIRECT_URI = "http://example.com/gorun/";
    private static final String GRANT_TYPE = "authorization_code";
    //Response
    public static String Authcode;
    //Authorization
    static String AUTHORIZATION_CODE;
    static String Tokentype;
    static String Refreshtoken;
    static Long Expiresin, ExpiryTime;

    private Button btnAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strava);
        btnAuth = findViewById(R.id.but_auth);


        //Get data from SharedPreference

//TODO:do normal layoute

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.strava.com/oauth/mobile/authorize" + "?client_id=" + CLIENT_ID + "&response_type=" + CODE + "&redirect_uri=" + REDIRECT_URI + "&scope=" + OAUTH_SCOPE));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri data = getIntent().getData();

        if (data != null && !TextUtils.isEmpty(data.getScheme())) {
            String code = data.getQueryParameter(CODE);


            if (!TextUtils.isEmpty(code)) {
                Toast.makeText(StravaActivity.this, "Авторизация успешна пройдена" + code, Toast.LENGTH_LONG).show();
                AUTHORIZATION_CODE = code;

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://www.strava.com/")
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();

                OAuthServer.OAuthServerIntface oAuthServerIntface = retrofit.create(OAuthServer.OAuthServerIntface.class);
                final Call<OAuthToken> accessTokenCall = oAuthServerIntface.getAccessToken(
                        AUTHORIZATION_CODE,
                        CLIENT_ID,
                        CLIENT_SECRET,
                        GRANT_TYPE
                );

                accessTokenCall.enqueue(new Callback<OAuthToken>() {
                    @Override
                    public void onResponse(Call<OAuthToken> call, Response<OAuthToken> response) {
                        if (response.body() != null) {
                            Authcode = response.body().getAccessToken();
                            Tokentype = response.body().getTokenType();
                            Expiresin = response.body().getExpiresIn();
                            Refreshtoken = response.body().getRefreshToken();
                            ExpiryTime = System.currentTimeMillis() + (Expiresin * 1000);
                        } else {
                            try {
                                Toast.makeText(StravaActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                return;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        saveData();

                        Intent i = new Intent(StravaActivity.this, NavigationActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<OAuthToken> call, Throwable t) {
                        Toast.makeText(StravaActivity.this, "произошла ошибка", Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (TextUtils.isEmpty(code)) {
                Toast.makeText(this, "Запрос отклонен!!!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void saveData() {
        SharedPreferences.Editor sharedPref = getSharedPreferences("authInfo", Context.MODE_PRIVATE).edit();
        sharedPref.putString("AuthCode", AUTHORIZATION_CODE);
        sharedPref.putString("secCode", Authcode);
        sharedPref.putString("refresh", Refreshtoken);
        sharedPref.putLong("expiry", ExpiryTime);
        sharedPref.apply();
    }


}
