package nju.cgm.kafkaspring.service;

import nju.cgm.kafkaspring.model.Recruitment;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 14:20
 * @description: RecruitmentService
 */
public interface RecruitmentService {

    List<Recruitment> fetch(int offset, int batchSize);
}
