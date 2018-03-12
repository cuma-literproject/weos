package com.clue.controller;

import com.clue.model.EosChainInfo;
import com.clue.remote.chain.GetRequiredKeys;
import com.clue.remote.chain.RequiredKeysResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EosdApi {
    @GET("/v1/chain/{infoType}")
    Call<EosChainInfo> readInfo(@Path("infoType") String infoType);

    @POST("/v1/chain/get_required_keys")
    Call<RequiredKeysResponse> getRequiredKeys(@Body GetRequiredKeys body);
}