package com.clue.controller;

import com.clue.model.EosChainInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EosdApi {

    @GET("/v1/chain/{infoType}")
    Call<EosChainInfo> readInfo(@Path("infoType") String infoType);
}