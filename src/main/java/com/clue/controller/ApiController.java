package com.clue.controller;

import com.clue.crypto.ec.EosPrivateKey;
import com.clue.model.EosChainInfo;
import com.clue.remote.api.AccountInfoRequest;
import com.clue.remote.api.Message;
import com.clue.remote.api.PushTxnResponse;
import com.clue.remote.chain.GetRequiredKeys;
import com.clue.remote.chain.RequiredKeysResponse;
import com.clue.remote.chain.SignedTransaction;
import com.clue.remote.types.EosNewAccount;
import com.clue.remote.types.TypeChainId;
import com.clue.util.Consts;
import com.clue.util.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
            Gson gson = new GsonBuilder().setLenient().create();
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

    @RequestMapping("/{net}/get/account/{name}")
    String getAccount(@PathVariable String net, @PathVariable String name, Model model) throws IOException {
        EosdApi eosdApi = getEosdApi(net);

        AccountInfoRequest req = new AccountInfoRequest(name);
        Response<JsonObject> response = eosdApi.getAccountInfo(req).execute();
        return response.body().toString();
    }

    private EosdApi getEosdApi(String net) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getDomain(net))
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(EosdApi.class);
    }

    private String getDomain(String net) {
        if (net.equals("testnet1")) {
            return "http://testnet1.eos.io";
        }

        if (net.equals("testnet2")) {
            return "http://testnet2.eos.io";
        }

        return "http://localhost:8888";
    }

    @RequestMapping("/{net}/new/account/{name}/{ownerKey}/{activeKey}")
    String register(@PathVariable String net, @PathVariable String name, @PathVariable String ownerKey, @PathVariable String activeKey, Model model) throws IOException {
        EosChainInfo info = getChainInfo().execute().body();
        String creator = "inita";

        EosPrivateKey realOwnerKey = new EosPrivateKey(ownerKey);
        EosPrivateKey realActiveKey= new EosPrivateKey(activeKey);
        ArrayList<String> keys = new ArrayList<>();
        keys.add(realOwnerKey.getPublicKey().getBytesAsHexStr());
        keys.add(realActiveKey.getPublicKey().getBytesAsHexStr());

        ArrayList<String> keys2 = new ArrayList<>();
        keys2.add(realOwnerKey.getPublicKey().toString());
        keys2.add(realActiveKey.getPublicKey().toString());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        EosNewAccount newAccountData = new EosNewAccount(creator, name, keys.get(0), keys.get(1), creator);
        System.out.println(gson.toJson(newAccountData).toString());

        SignedTransaction txn = createTransaction(Consts.EOS_CONTRACT_NAME, newAccountData.getTypeName(), newAccountData.getAsHex()
                , new String[]{ newAccountData.getCreatorName(),Consts.EOS_CONTRACT_NAME }
                , getActivePermission( newAccountData.getCreatorName() ), info );


        EosdApi eosdApi = getEosdApi(net);
        GetRequiredKeys getRequiredKeys = new GetRequiredKeys(txn, keys2);
        System.out.println(gson.toJson(getRequiredKeys).toString());

        Response<RequiredKeysResponse> response = eosdApi.getRequiredKeys(getRequiredKeys).execute();

        // no-key return
        // signTransaction(txn);
        EosdApi eosdApi2 = getEosdApi(net);
        Response<PushTxnResponse> response2 = eosdApi2.pushTransaction(txn).execute();

        String res = gson.toJson(response2.body()).toString();
        System.out.println(res);
        return res;
    }


    String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    public Call<EosChainInfo> getChainInfo() {
        Retrofit retrofit = RetrofitClient.getClient("http://localhost:8888");

        EosdApi eosdApi = retrofit.create(EosdApi.class);
        return eosdApi.readInfo("get_info");
    }

    private SignedTransaction createTransaction(String contract, String action, String dataAsHex,
                                                String[] scopes, String[] permissions, EosChainInfo chainInfo) {
        Message msg = new Message();
        msg.setCode( contract );
        msg.setAuthorization(permissions);
        msg.setType( action );
        msg.setData( dataAsHex );

        SignedTransaction txn = new SignedTransaction();
        txn.setScope( scopes );
        txn.addMessage( msg );
        txn.setReadScopeList(new ArrayList<>(0));
        txn.setSignatures( new ArrayList<>());


        if ( null != chainInfo ) {
            txn.setReferenceBlock(chainInfo.getHeadBlockId());
            txn.setExpiration(chainInfo.getTimeAfterHeadBlockTime(Consts.TX_EXPIRATION_IN_MILSEC));
        }

        return txn;
    }

    public SignedTransaction signTransaction(final SignedTransaction txn, EosPrivateKey key){
        SignedTransaction stxn = new SignedTransaction( txn );
        stxn.sign(key, new TypeChainId());
        return stxn;
    }

    private String[] getActivePermission(String accountName ) {
        return new String[] { accountName + "@active" };
    }
}
