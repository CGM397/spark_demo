package nju.cgm.kafkaspring.serialization;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;


/**
 * @author: Bright Chan
 * @date: 2020/11/2 15:12
 * @description: JsonSerializer
 */
public class JsonSerializer implements Serializer<JSONObject> {

    @Override
    public byte[] serialize(String topic, JSONObject jsonObject) {
        return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
    }
}
