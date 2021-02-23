package nju.cgm.kafkaspring.controller;

import nju.cgm.kafkaspring.model.ScheduleTaskInfo;
import nju.cgm.kafkaspring.service.ScheduleTaskInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Bright Chan
 * @date: 2020/11/18 21:16
 * @description: ScheduleTaskInfoController
 */

@RestController
public class ScheduleTaskInfoController {

    @Resource
    private ScheduleTaskInfoService scheduleTaskInfoService;

    @GetMapping("/scheduleTask/isStart")
    public ScheduleTaskInfo getScheduleTaskInfo() {
        return scheduleTaskInfoService.getScheduleTaskInfo();
    }

}
