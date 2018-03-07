package com.clue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

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
}
