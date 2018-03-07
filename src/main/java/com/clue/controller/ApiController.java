package com.clue.controller;

import com.clue.model.EosChainInfo;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;

@RestController
public class ApiController {
    @RequestMapping("/")
    String home() {
        try {
            return getChainInfo().execute().body().getHeadBlockNum().toString();
        } catch (IOException e) {
            return "something goes wrong:" + e.getMessage();
        }
    }

    public Call<EosChainInfo> getChainInfo() {
        Retrofit retrofit = RetrofitClient.getClient("http://testnet1.eos.io");

        EosdApi eosdApi = retrofit.create(EosdApi.class);
        return eosdApi.readInfo("get_info");
    }
}
