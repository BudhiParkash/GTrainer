package com.example.gtrainer.Api;

import com.example.gtrainer.model.User;
import com.example.gtrainer.model.UserPojo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public class ApiClientInterface {

    private static final String url = "http://192.168.43.72:3000/";

    public static GTrainerApiService gTrainerApiService = null;

    public  static GTrainerApiService getTrainerApiService(){
        if(gTrainerApiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            gTrainerApiService = retrofit.create(GTrainerApiService.class);
        }
        return gTrainerApiService;
    }



    public interface GTrainerApiService{

        @POST("/v1/users")
        Call<UserPojo>  createUser(@Body User number);

        @PATCH("/v1/app/users/me/update")
        Call<User>   postUserData(@Header("Authorization") String header , @Body User userData);

        @PATCH("/v1/app/user/trainer")
        Call<User>  applyfortrainer(@Header("Authorization") String header , @Body User userData);




    }
}
