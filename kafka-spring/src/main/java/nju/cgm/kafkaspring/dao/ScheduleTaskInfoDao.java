package nju.cgm.kafkaspring.dao;

import nju.cgm.kafkaspring.model.ScheduleTaskInfo;

/**
 * @author: Bright Chan
 * @date: 2020/11/18 17:51
 * @description: ScheduleTaskDao
 */
public interface ScheduleTaskInfoDao extends BaseDao<ScheduleTaskInfo>{

    ScheduleTaskInfo getScheduleTaskInfo();
}
