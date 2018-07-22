package com.example.aleksejkocergin.testapp.service;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WatsonService {

    @Headers("Content-Type: text/plain")
    @POST("v3/identify?version=2018-05-01")
    Observable<IdentifyModel> getData(@Body String data);
}
