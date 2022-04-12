package cn.com.metamedical.util.tool;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class JsonUtil {

	/**
	 * 根据传入的json数组，返回指定包含类型的集合
	 * 
	 * @param jsonStr
	 *            [{id:1,name:gx},{id:2,name:wxm}]
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonArrayToList(String jsonStr, Class<T> clazz) {
		if (jsonStr == null || jsonStr.trim().equals("")) {
			return null;
		}
		List<T> resultList = new ArrayList<T>();
		Gson gson = new Gson();
		JsonElement jsonElement = JsonParser.parseString(jsonStr); // 将json字符串转换成JsonElement
		JsonArray jsonArray = jsonElement.getAsJsonArray(); // 将JsonElement转换成JsonArray
		Iterator<JsonElement> it = jsonArray.iterator(); // Iterator处理
		while (it.hasNext()) { // 循环
			jsonElement = (JsonElement) it.next(); // 提取JsonElement
			String menu = jsonElement.toString(); // JsonElement转换成String
			T bean = gson.fromJson(menu, clazz); // String转化成JavaBean
			resultList.add(bean); // 加入List
		}
		return resultList;
	}

	/**
	 * 将传入的对象转为json字符串，支持不固定参数 使用示例：allToJson(1,2,3)，allToJson()，
	 * allToJson(null)， allToJson(new TreeBean(), "aaa")
	 * 
	 * @author Allen
	 * @return json字符串
	 */
	public static String allToJson(Object... objArray) {
		Gson gson = new Gson();
		if (objArray != null) {
			return gson.toJson(objArray);
		}
		return gson.toJson(new Object[0]);
	}
	
	public static final TypeAdapter<java.sql.Date> DATE = new TypeAdapter<java.sql.Date>() {
        @Override
        public java.sql.Date read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                return new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(in.nextString()).getTime());
            } catch (Exception e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public void write(JsonWriter out, java.sql.Date value) throws IOException {
            out.value(new SimpleDateFormat("yyyy-MM-dd").format(value));
        }
    };
    
	public static final TypeAdapter<java.sql.Time> TIME = new TypeAdapter<java.sql.Time>() {
        @Override
        public java.sql.Time read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                return new java.sql.Time(new SimpleDateFormat("HH:mm").parse(in.nextString()).getTime());
            } catch (Exception e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public void write(JsonWriter out, java.sql.Time value) throws IOException {
            out.value(new SimpleDateFormat("HH:mm:ss").format(value));
        }
    };
    
    public static final TypeAdapter<java.sql.Timestamp> TIMESTAMP = new TypeAdapter<java.sql.Timestamp>() {
        @Override
        public java.sql.Timestamp read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                return new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(in.nextString()).getTime());
            } catch (Exception e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public void write(JsonWriter out, java.sql.Timestamp value) throws IOException {
            out.value(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
        }
    };
}
