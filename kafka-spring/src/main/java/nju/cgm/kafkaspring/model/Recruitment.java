package nju.cgm.kafkaspring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 13:42
 * @description: Recruitment
 */

@Entity
@Table(name = "recruitment_info")
public class Recruitment implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "info_id")
    private int infoId;

    @Column(name = "position_class")
    private String positionClass;

    @Column(name = "position_count")
    private int positionCount;

    public Recruitment() {
    }

    public Recruitment(int infoId, String positionClass, int positionCount) {
        this.infoId = infoId;
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
