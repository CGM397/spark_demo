package nju.cgm.sparkstreamingtest.model;

import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/11/7 9:26
 * @description: RecruitmentCount
 */
public class RecruitmentCountRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String positionClass;

    private int positionCount;

    public RecruitmentCountRes() {
    }

    public RecruitmentCountRes(String positionClass, int positionCount) {
        this.positionClass = positionClass;
        this.positionCount = positionCount;
    }

    public String getPositionClass() {
        return positionClass;
    }

    public void setPositionClass(String positionClass) {
        this.positionClass = positionClass;
    }

    public int getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(int positionCount) {
        this.positionCount = positionCount;
    }
}
