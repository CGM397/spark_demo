package nju.cgm.kafkaspring.scheduledTask;

import nju.cgm.kafkaspring.kafkaConfig.KafkaProducer;
import nju.cgm.kafkaspring.model.Recruitment;
import nju.cgm.kafkaspring.model.ScheduleTaskInfo;
import nju.cgm.kafkaspring.pool.MsgPool;
import nju.cgm.kafkaspring.service.RecruitmentService;
import nju.cgm.kafkaspring.service.ScheduleTaskInfoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 13:25
 * @description: KafkaMsg
 */

@Configuration
@EnableScheduling
public class KafkaMsg {

    private static int OFFSET = 0;

    private static final int BATCH_SIZE = 100000;

    @Resource
    private RecruitmentService recruitmentService;

    @Resource
    private ScheduleTaskInfoService scheduleTaskInfoService;

    @Resource
    private KafkaProducer kafkaProducer;

    // 每五秒执行一次
    @Scheduled(fixedRate = 5000)
    private void sendMsgToKafka(){
        // 如果前端没有发起 启动 指令，则不执行后面的代码
        ScheduleTaskInfo info = scheduleTaskInfoService.getScheduleTaskInfo();
        if (!info.isTaskStart()) return;

        List<Recruitment> res = recruitmentService.fetch(OFFSET, BATCH_SIZE);
        MsgPool.getSendPool().execute(() -> kafkaProducer.sendRecruitmentMsg("demo", res));

        OFFSET += BATCH_SIZE;
    }
}
