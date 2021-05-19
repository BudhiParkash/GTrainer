package com.example.gtrainer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.R;
import com.example.gtrainer.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Apply_For_Trainer extends AppCompatActivity {

    Button mApply;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply__for__trainer);

        mApply = findViewById(R.id.applybtn);

        preferences = getSharedPreferences("ProfileData" ,MODE_PRIVATE);

        String token = preferences.getString("tokken" , "");


        mApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Apply_For_Trainer.this, Apply_For_Trainer_2.class);
                startActivity(intent);


                User trainerData =  new User(true, "shivam" , "s@gmail.com" ,
                        "hisar","akfkljf","s@gmail,com","s@gmail,com",
                        "s@gmail,com","s@gmail,com");

                Call<User> call = ApiClientInterface.getTrainerApiService().applyfortrainer(token , trainerData);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.code() == 200){
                            Toast.makeText(Apply_For_Trainer.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Apply_For_Trainer.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}