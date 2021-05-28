package com.example.gtrainer.Api;

import com.example.gtrainer.model.BookingPojo;
import com.example.gtrainer.model.CertificatePhotoPojo;
import com.example.gtrainer.model.PayPojo;
import com.example.gtrainer.model.TrainerListPojo;
import com.example.gtrainer.model.TrainerPicPojo;
import com.example.gtrainer.model.User;
import com.example.gtrainer.model.UserPojo;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class ApiClientInterface {

    //192.168.29.43
    //http://192.168.1.7 brondband
    //http://192.168.73.72 PocoX2
    //http://192.168.43.72:3000/ shivaa
    private static final String url = "http://192.168.1.6:3000/";

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


        @Multipart
        @POST("/v1/users/addcerti")
        Call<ResponseBody> uploadCertificate(@Header("Authorization") String Header, @Part MultipartBody.Part slipImage);

        @Multipart
        @POST("/v1/users/ownpic")
        Call<ResponseBody> uploadTrainerImage(@Header("Authorization") String Header, @Part MultipartBody.Part slipImage);

        //Payment
        @POST("/pay")
        Call<List<PayPojo>> getOrderId(@Header("Authorization") String Header, @Body JsonObject jo);

        @GET("/v1/users/getcerti")
        Call<List<CertificatePhotoPojo>> getCerti_Pic(@Header("Authorization") String Header);

        @GET("/v1/users/trainerpic")
        Call<List<TrainerPicPojo>> getTrainer_Pic(@Header("Authorization") String Header);


        @GET("/v1/toptrainers")
        Call<List<User>>  getTopTrainer(@Header("Authorization") String Header);

        @GET("/v1/trainers/get")
        Call<TrainerListPojo>  getAllTrainer(@Header("Authorization") String Header  , @Query("page") int page , @Query("limit") int limit );

        @POST("/v1/booking")
        Call<BookingPojo>  postBooking(@Header("Authorization") String Header , @Body BookingPojo bookingPojo);

        @GET("/v1/bookings")
        Call<List<BookingPojo>>  getBookings(@Header("Authorization") String Header );

        @GET("/v1/tranier/get")
        Call<User>  getBookedTrainer(@Header("Authorization")String Header, @Query("tranierid") String tranierid);






    }
}
