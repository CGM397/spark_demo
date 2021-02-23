package nju.cgm.sparkstreamingtest;

import com.alibaba.fastjson.JSON;
import nju.cgm.sparkstreamingtest.model.Recruitment;
import nju.cgm.sparkstreamingtest.service.RDDService;
import nju.cgm.sparkstreamingtest.service.impl.RDDServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.*;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 14:03
 * @description: RecruitmentCount
 */
public class RecruitmentCount {

    public static void main(String[] args) {
        int duration = 5;   // seconds
        String brokers = "host:9092";
        String groupId = "demo_test";
        String topics = "demo";

        // Create context
        SparkConf sparkConf = new SparkConf().setAppName("RecruitmentCount").setMaster("local[*]");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(duration));
        ssc.checkpoint("./check");

        // set spark log level
        ssc.sparkContext().setLogLevel("WARN");

        // Initial state RDD input to mapWithState
        // @SuppressWarnings("unchecked")
        List<Tuple2<String, Integer>> tuples = new ArrayList<>();
        JavaPairRDD<String, Integer> initialRDD = ssc.sparkContext().parallelizePairs(tuples);

        // kafka config: topic, broker, groupId...
        Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Create direct kafka stream with brokers and topics
        JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(
                ssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topicsSet, kafkaParams));

        // 得到消息队列中的消息内容
        JavaDStream<String> recruitmentInfoStr = messages.map(ConsumerRecord::value);
        // 将字符串消息转成对象
        JavaDStream<Recruitment> recruitmentInfos =
                recruitmentInfoStr.map(msg -> JSON.parseObject(msg, Recruitment.class));
        // 按职业种类对本次读取的数据中的岗位数量做求和操作
        JavaPairDStream<String, Integer> posDStream = recruitmentInfos
                .mapToPair(one -> new Tuple2<>(one.getPositionClass(), one.getPositionCount()))
                .reduceByKey(Integer::sum);

        // 更新累加求和的函数
        Function3<String, Optional<Integer>, State<Integer>, Tuple2<String, Integer>> mappingFunc =
                (word, one, state) -> {
                    int sum = one.orElse(0) + (state.exists() ? state.get() : 0);
                    Tuple2<String, Integer> output = new Tuple2<>(word, sum);
                    state.update(sum);
                    return output;
                };

        // 将本次的求和结果加入累加求和的集合中
        JavaMapWithStateDStream<String, Integer, Integer, Tuple2<String, Integer>> stateDStream =
                posDStream.mapWithState(StateSpec.function(mappingFunc).initialState(initialRDD));
        // 得到累加求和的最新结果
        JavaPairDStream<String, Integer> res = stateDStream.stateSnapshots();

        res.print();
        // 将结果存入数据库中
        RDDService rddService = new RDDServiceImpl();
        res.foreachRDD(rddService::saveRDDData);

        // Start the computation
        ssc.start();
        try {
            ssc.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ssc.close();
        }
    }
}
