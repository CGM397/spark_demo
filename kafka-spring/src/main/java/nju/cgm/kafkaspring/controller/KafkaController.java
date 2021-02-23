package nju.cgm.kafkaspring.controller;

import nju.cgm.kafkaspring.kafkaConfig.KafkaProducer;
import nju.cgm.kafkaspring.model.Recruitment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Bright Chan
 * @date: 2020/10/20 11:05
 * @description: KafkaController
 */

@RestController
public class KafkaController {

    @Resource
    private KafkaProducer kafkaProducer;

    @GetMapping("/send/message/simple")
    public void sendSimpleMsg(@RequestParam String topic, @RequestParam String message) {
        kafkaProducer.sendSimpleMsg(topic, message);
    }

    @GetMapping("/send/message/recruitment")
    public void sendRecruitmentMsg(@RequestParam String topic, @RequestBody Recruitment recruitment) {
        kafkaProducer.sendRecruitmentMsg(topic, recruitment);
    }
}
