package nju.cgm.kafkaspring.kafkaConfig;

import com.alibaba.fastjson.JSONObject;
import nju.cgm.kafkaspring.model.Recruitment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/10/20 10:45
 * @description: KafkaProducer
 */

@Component
public class KafkaProducer {

    @Resource
    private KafkaTemplate<String,Object> kafkaTemplate;

    public void sendSimpleMsg(String topic, String message) {
        ListenableFuture future = kafkaTemplate.send(topic, message);
        future.addCallback(o -> System.out.println("消息发送成功：" + message),
                throwable -> System.out.println("消息发送失败：" + message));
    }

    public void sendRecruitmentMsg(String topic, Recruitment recruitment) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(recruitment);
        ListenableFuture future = kafkaTemplate.send(topic, jsonObject);
        future.addCallback(o -> System.out.println("消息发送成功：" + recruitment.getInfoId()),
                throwable -> System.out.println("消息发送失败：" + recruitment.getInfoId()));
    }

    public void sendRecruitmentMsg(String topic, List<Recruitment> recruitmentList) {
        for (Recruitment one : recruitmentList) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(one);
            kafkaTemplate.send(topic, jsonObject);
        }
    }
}
