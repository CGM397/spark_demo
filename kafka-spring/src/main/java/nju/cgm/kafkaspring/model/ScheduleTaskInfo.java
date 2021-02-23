package nju.cgm.kafkaspring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/11/18 21:01
 * @description: ScheduleTaskInfo
 */

@Entity
@Table(name = "kafka_schedule_task")
public class ScheduleTaskInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "info_id")
    private int infoId;

    @Column(name = "task_start")
    private boolean taskStart;

    public ScheduleTaskInfo() {
    }

    public ScheduleTaskInfo(int infoId, boolean taskStart) {
        this.infoId = infoId;
        this.taskStart = taskStart;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public boolean isTaskStart() {
        return taskStart;
    }

    public void setTaskStart(boolean taskStart) {
        this.taskStart = taskStart;
    }
}
