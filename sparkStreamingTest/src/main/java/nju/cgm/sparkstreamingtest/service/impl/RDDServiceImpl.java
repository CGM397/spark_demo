package nju.cgm.sparkstreamingtest.service.impl;

import nju.cgm.sparkstreamingtest.dao.RDDDao;
import nju.cgm.sparkstreamingtest.dao.impl.RDDDaoImpl;
import nju.cgm.sparkstreamingtest.model.RecruitmentCountRes;
import nju.cgm.sparkstreamingtest.service.RDDService;
import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/5 17:14
 * @description: RDDServiceImpl
 */
public class RDDServiceImpl implements RDDService, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void saveRDDData(JavaPairRDD<String, Integer> one) {
        one.foreachPartition(tuple2Iterator -> {
            List<RecruitmentCountRes> store = new ArrayList<>();
            RDDDao rddDao = new RDDDaoImpl();
            while (tuple2Iterator.hasNext()) {
                Tuple2<String, Integer> tmp = tuple2Iterator.next();
                RecruitmentCountRes oneData = new RecruitmentCountRes(tmp._1, tmp._2);
                store.add(oneData);
            }
            rddDao.insertBatch(store);
        });
    }
}
