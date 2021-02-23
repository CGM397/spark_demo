package nju.cgm.kafkaspring.controller;

import nju.cgm.kafkaspring.model.Recruitment;
import nju.cgm.kafkaspring.service.RecruitmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 20:32
 * @description: RecruitmentController
 */

@RestController
public class RecruitmentController {

    @Resource
    private RecruitmentService recruitmentService;

    @GetMapping("/recruitment/info")
    public List<Recruitment> getRecruitmentInfos(@RequestParam int offset, @RequestParam int batchSize) {
        return recruitmentService.fetch(offset, batchSize);
    }
}
