package cn.bugfish.dove_wz25.UserMannageSystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class GsonUtil {

    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()) // 注册自定义的 LocalDateTime 适配器
                .create();
    }
}
