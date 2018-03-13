package com.clue.controller;

import com.clue.model.EosChainInfo;
import com.clue.remote.api.AccountInfoRequest;
import com.clue.remote.api.PushTxnResponse;
import com.clue.remote.chain.GetRequiredKeys;
import com.clue.remote.chain.RequiredKeysResponse;
import com.clue.remote.chain.SignedTransaction;
import com.google.gson.JsonObject;
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

    @POST("/v1/chain/push_transaction")
    Call<PushTxnResponse> pushTransaction(@Body SignedTransaction body);

    @POST("/v1/chain/get_account")
    Call<JsonObject> getAccountInfo(@Body AccountInfoRequest body);
}