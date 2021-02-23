package nju.cgm.kafkaspring.dao.impl;

import nju.cgm.kafkaspring.dao.RecruitmentDao;
import nju.cgm.kafkaspring.model.Recruitment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 14:19
 * @description: RecruitmentDaoImpl
 */

@Repository
public class RecruitmentDaoImpl extends BaseDaoImpl<Recruitment> implements RecruitmentDao {

    @Override
    public List<Recruitment> query(int offset, int batchSize) {
        String hql = "from Recruitment";
        return getLimitResultByHQL(hql, offset, batchSize);
    }
}
