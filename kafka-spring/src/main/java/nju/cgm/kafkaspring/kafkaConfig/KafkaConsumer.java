package nju.cgm.kafkaspring.kafkaConfig;

import com.alibaba.fastjson.JSON;
import nju.cgm.kafkaspring.model.Recruitment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: Bright Chan
 * @date: 2020/10/20 11:21
 * @description: KafkaConsumer
 */

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "demo")
    public void consumes(String msg) {
//        System.out.println(msg);
//        Recruitment recruitment = JSON.parseObject(msg, Recruitment.class);
//        System.out.println(recruitment.getInfoId() + " " + recruitment.getPositionCount() + " " +
//                recruitment.getPositionClass());
    }

}
