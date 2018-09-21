package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by caozhen on 2018/9/21
 */


public class LoadProperties {

    public static Map<String, String> CITY_MAPPING = new HashMap();
    public static Map<String, String>  CITY2_MAPPING = new HashMap();

    /**
     * 1. classLoader获取指定配置文件配置，一般用作读取额外的配置
     */
    static {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = LoadProperties.class.getClassLoader();

            //load 城市代码列表
            properties.load(new InputStreamReader(classLoader.getResourceAsStream("config/city.properties"), "UTF-8"));
            for (Object key : properties.keySet()) {
                CITY_MAPPING.put(key.toString(), properties.get(key).toString());
            }

            properties.clear();

            //load 城市2代码列表
            properties.load(new InputStreamReader(classLoader.getResourceAsStream("config/city2.properties"), "UTF-8"));
            for (Object key : properties.keySet()) {
                CITY2_MAPPING.put(key.toString(), properties.get(key).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  2. @Value 注解获取默认配置文件的配置，一般用作读某些配置
     *
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap_servers;


    /**
     * 3. 通过Environment接口来获取
     *
     */

    @Autowired
    Environment env;

    public void getEnv(){
        env.getProperty("");
    }



    /**
     * 4. 使用@ConfigurationProperties(prefix = "druid.slave")
     * 参见druid自动配置的类
     *
     * 当配置不在默认配置文件中，
     * 增加PropertySource(value = "classpath:config/city.properties") 联合使用
     */

    public void getProperties(){

    }


    /**
     *  5. 使用InPutStream流读取properties文件,优点可以读取任意位置的文件
     */
    static {
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(":/config.properties"));
            properties.load(bufferedReader);
            properties.getProperty("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
