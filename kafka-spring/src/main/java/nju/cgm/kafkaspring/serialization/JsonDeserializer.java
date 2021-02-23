package nju.cgm.kafkaspring.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 15:27
 * @description: JsonDeserializer
 */
public class JsonDeserializer implements Deserializer<JSONObject> {
    @Override
    public JSONObject deserialize(String topic, byte[] data) {
        JSONObject obj = null;
        try {
            obj = JSON.parseObject(new String(data, StandardCharsets.UTF_8));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
