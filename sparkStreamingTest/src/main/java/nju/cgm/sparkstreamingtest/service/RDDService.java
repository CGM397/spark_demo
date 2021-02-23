package nju.cgm.sparkstreamingtest.service;

import org.apache.spark.api.java.JavaPairRDD;

/**
 * @author: Bright Chan
 * @date: 2020/11/5 17:11
 * @description: RDDService
 */
public interface RDDService {

    void saveRDDData(JavaPairRDD<String, Integer> one);

}
