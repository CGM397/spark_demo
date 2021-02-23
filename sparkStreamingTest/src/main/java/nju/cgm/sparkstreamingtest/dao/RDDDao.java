package nju.cgm.sparkstreamingtest.dao;

import nju.cgm.sparkstreamingtest.model.RecruitmentCountRes;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/7 9:06
 * @description: RDDDao
 */
public interface RDDDao {

    void insertBatch(List<RecruitmentCountRes> store);

}
