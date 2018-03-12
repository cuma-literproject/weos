package com.clue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EosChainInfo {
    @SerializedName("server_version")
    @Expose
    @Getter
    @Setter
    private String serverVersion;

    @SerializedName("head_block_num")
    @Expose
    @Getter
    @Setter
    private Integer headBlockNum;

    @SerializedName("last_irreversible_block_num")
    @Expose
    @Getter
    @Setter
    private Integer lastIrreversibleBlockNum;

    @SerializedName("head_block_id")
    @Expose
    @Getter
    @Setter
    private String headBlockId;

    @SerializedName("head_block_time")
    @Expose
    @Getter
    @Setter
    private String headBlockTime;

    @SerializedName("head_block_producer")
    @Expose
    @Getter
    @Setter
    private String headBlockProducer;

    @SerializedName("recent_slots")
    @Expose
    @Getter
    @Setter
    private String recentSlots;

    @SerializedName("participation_rate")
    @Expose
    @Getter
    @Setter
    private String participationRate;

    public String getTimeAfterHeadBlockTime(int diffInMilSec) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = sdf.parse( this.headBlockTime);

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add( Calendar.MILLISECOND, diffInMilSec);
            date = c.getTime();

            return sdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return this.headBlockTime;
        }
    }
}
