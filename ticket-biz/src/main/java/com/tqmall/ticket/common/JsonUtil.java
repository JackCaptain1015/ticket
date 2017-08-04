package com.tqmall.ticket.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * JsonUtil
 * Created by wurenzhi
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonValue(String resContent, String key) {
        JSONObject jsonObject;
        String v = null;
        try {
            jsonObject = new JSONObject(resContent);
            v = jsonObject.getString(key);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return v;
    }

    /**
     * 将JSON字符串反序列化为对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String json, Class<T> clazz) {
        Object object = null;
        try {
            object = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("转换出错", e);
        }
        return (T) object;
    }

    public static Object get(JSONObject json, String key) {
        try {
            if (json.isNull(key)) return null;
            return json.get(key);
        } catch (Exception e) {
            log.error("获取key失败", e);
        }
        return null;
    }

    public static JSONObject Str2Obj(String jsonStr) {
        try {
            return new JSONObject(jsonStr);
        } catch (Exception e) {
            log.error("反序列化json失败", e);
        }
        return null;
    }

    public static String obj2Str(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("反序列化json失败", e);
        }
        return null;
    }

    //调用url访问接口
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String keyValueToJsonStr(String key,String value){
        StringBuilder sb = new StringBuilder();
        sb = sb.append("{\"").append(key).append("\":\"").append(value).append("\"}");
        return sb.toString();
    }

    public static String list2JsonStr(List list,String keyName){
        if (list == null || list.size() == 0 || keyName == null || "".equals(keyName.trim())){
            return "";
        }
        return "{\""+keyName+"\":"+JSON.toJSONString(list)+"}";
    }

    /**解析Json数组，得到某个key下面的json数组中的某个key的value集合
     * 如:{"userlist":[{"name":"name","userid":"036510211921368142"}]}
     */
    public static List<String> getJsonArrayValueInKey(String jsonStr,String key,String keyInArray){
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONArray jsonArray = JSON.parseArray(jsonObject.getString(key));
        Iterator<Object> iterator = jsonArray.iterator();

        List<String> returnList = Lists.newArrayList();
        while (iterator.hasNext()){
            com.alibaba.fastjson.JSONObject next = (com.alibaba.fastjson.JSONObject)iterator.next();
            returnList.add(next.get(keyInArray).toString());
        }
        return returnList;
    }

    /***
     * 解析Json数组，得到某个key下面的json数组，并返回相应对象集合
     */
    public static <T> List<T> getJsonArrayBean(String jsonStr,String key,Class<T> clazz){
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONArray jsonArray = JSON.parseArray(jsonObject.getString(key));
        List<T> returnList = JSON.parseArray(jsonArray.toJSONString(), clazz);

        return returnList;
    }
}