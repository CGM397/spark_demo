package nju.cgm.kafkaspring.service.impl;

import nju.cgm.kafkaspring.dao.ScheduleTaskInfoDao;
import nju.cgm.kafkaspring.model.ScheduleTaskInfo;
import nju.cgm.kafkaspring.service.ScheduleTaskInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: Bright Chan
 * @date: 2020/11/18 21:14
 * @description: ScheduleTaskInfoServiceImpl
 */

@Service
@Transactional
public class ScheduleTaskInfoServiceImpl implements ScheduleTaskInfoService {

    @Resource
    private ScheduleTaskInfoDao scheduleTaskInfoDao;

    @Override
    public ScheduleTaskInfo getScheduleTaskInfo() {
        return scheduleTaskInfoDao.getScheduleTaskInfo();
    }
}
