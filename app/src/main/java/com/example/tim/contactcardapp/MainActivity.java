package com.example.tim.contactcardapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tim.contactcardapp.model.Person;
import com.example.tim.contactcardapp.model.ServerResponse;
import com.example.tim.contactcardapp.network.PersonService;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Wil je uitloggen?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        clearLocalPreferences();
                        Intent intent = new Intent(MainActivity.super.getApplication(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setupRetrofit();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");
        getSupportActionBar().setTitle("Welkom, " + username);
    }


    private void setupRetrofit(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(BuildConfig.API_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        PersonService service =  retrofit.create(PersonService.class);

        Call<ServerResponse> call = service.getPersons();
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                showPersons(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                createAndDisplayNoPersonsErrorMessage();
            }
        });
    }

    private void showPersons(List<Person> persons){
        recyclerView.setAdapter(new PersonAdapter(persons));
    }

    private void clearLocalPreferences(){
        SharedPreferences settings = getSharedPreferences("LoginPreferences", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USERNAME", "");
        editor.putString("PASSWORD", "");
        editor.putBoolean("REMEMBER_CREDENTIALS", false);
        editor.apply();
    }

    private void createAndDisplayNoPersonsErrorMessage(){
        TextView error = new TextView(MainActivity.super.getApplication());
        error.setText("KAN GEEN PERSONEN OPHALEN :(");
        error.setTextSize(48);
        error.setGravity(Gravity.CENTER);
        error.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.no_person_error_layout);
        frameLayout.addView(error);
    }
}
