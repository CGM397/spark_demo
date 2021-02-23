package nju.cgm.kafkaspring.service.impl;

import nju.cgm.kafkaspring.dao.RecruitmentDao;
import nju.cgm.kafkaspring.model.Recruitment;
import nju.cgm.kafkaspring.service.RecruitmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 14:21
 * @description: RecruitmentServiceImpl
 */

@Service
@Transactional
public class RecruitmentServiceImpl implements RecruitmentService {

    @Resource
    private RecruitmentDao recruitmentDao;

    @Override
    public List<Recruitment> fetch(int offset, int batchSize) {
        return recruitmentDao.query(offset, batchSize);
    }
}
