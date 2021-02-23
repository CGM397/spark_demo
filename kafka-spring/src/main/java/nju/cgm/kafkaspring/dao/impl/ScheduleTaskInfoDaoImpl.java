package nju.cgm.kafkaspring.dao.impl;

import nju.cgm.kafkaspring.dao.ScheduleTaskInfoDao;
import nju.cgm.kafkaspring.model.ScheduleTaskInfo;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author: Bright Chan
 * @date: 2020/11/18 17:53
 * @description: ScheduleTaskDaoImpl
 */

@Repository
public class ScheduleTaskInfoDaoImpl extends BaseDaoImpl<ScheduleTaskInfo> implements ScheduleTaskInfoDao {

    @Override
    public ScheduleTaskInfo getScheduleTaskInfo() {
        String hql = "from ScheduleTaskInfo";
        Query query = getSession().createQuery(hql);
        return (ScheduleTaskInfo) query.getSingleResult();
    }
}
