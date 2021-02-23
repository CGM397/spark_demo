package nju.cgm.sparkgraphxtest.model;

import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/11/21 18:21
 * @description: TotalRes
 */
public class TotalRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String actorName;

    private Double rankNum;

    public TotalRes() {
    }

    public TotalRes(String actorName, Double rankNum) {
        this.actorName = actorName;
        this.rankNum = rankNum;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Double getRankNum() {
        return rankNum;
    }

    public void setRankNum(Double rankNum) {
        this.rankNum = rankNum;
    }
}
