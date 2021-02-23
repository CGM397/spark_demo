package nju.cgm.kafkaspring.dao;

import nju.cgm.kafkaspring.model.Recruitment;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 14:11
 * @description: RecruitmentDao
 */
public interface RecruitmentDao extends BaseDao<Recruitment> {

    List<Recruitment> query(int offset, int batchSize);
}
