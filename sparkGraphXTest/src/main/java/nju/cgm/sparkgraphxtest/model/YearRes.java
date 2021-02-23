package nju.cgm.sparkgraphxtest.model;

import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/11/21 18:21
 * @description: YearRes
 */
public class YearRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String actorName;

    private int filmNum;

    public YearRes() {
    }

    public YearRes(String actorName, int filmNum) {
        this.actorName = actorName;
        this.filmNum = filmNum;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public int getFilmNum() {
        return filmNum;
    }

    public void setFilmNum(int filmNum) {
        this.filmNum = filmNum;
    }
}
