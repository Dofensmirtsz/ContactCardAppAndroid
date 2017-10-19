package com.example.tim.contactcardapp.network;

import com.example.tim.contactcardapp.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by Gebruiker on 19-10-2017.
 */

public interface PersonService {

    @GET("?results=25")
    Call<ServerResponse> getPersons();



}
