package com.clue.controller;

import com.clue.crypto.ec.EosPrivateKey;
import com.clue.model.EosChainInfo;
import com.clue.util.RetrofitClient;
import com.google.gson.Gson;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {
    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    @RequestMapping("/v1/info")
    String home() {
        try {
            Gson gson = new Gson();
            EosChainInfo res = getChainInfo().execute().body();
            return gson.toJson(res);
        } catch (IOException e) {
            return "something goes wrong:" + e.getMessage();
        }
    }

    @RequestMapping("/key/k1/{count}")
    List<String> key(@PathVariable int count, Model model) {
        List<String> result = new ArrayList<>();
        if (count > 10) {
            return result;
        }

        for (int i = 0; i < count; i++) {
            EosPrivateKey key = new EosPrivateKey();
            result.add(key.toString());
        }
        return result;
    }

    String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    public Call<EosChainInfo> getChainInfo() {
        Retrofit retrofit = RetrofitClient.getClient("http://testnet1.eos.io");

        EosdApi eosdApi = retrofit.create(EosdApi.class);
        return eosdApi.readInfo("get_info");
    }
}
