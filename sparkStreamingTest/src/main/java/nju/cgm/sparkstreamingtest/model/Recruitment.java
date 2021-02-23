package nju.cgm.sparkstreamingtest.model;

import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 13:42
 * @description: Recruitment
 */
public class Recruitment implements Serializable {

    private static final long serialVersionUID = 1L;

    private int infoId;

    private String positionClass;

    private int positionCount;

    public Recruitment() {
    }

    public Recruitment(String positionClass, int positionCount) {
        this.positionClass = positionClass;
        this.positionCount = positionCount;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
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
