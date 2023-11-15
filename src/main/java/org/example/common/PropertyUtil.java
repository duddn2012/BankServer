package org.example.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static PropertyUtil instacne;
    private static final String PROPERTIES_FILE = "config.properties";
    private Properties properties;

    public static PropertyUtil getInstance() {
        if(instacne == null){
            instacne = new PropertyUtil();
        }
        return instacne;
    }


    public PropertyUtil() {
        try(InputStream input = PropertyUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.out.println("파일을 찾을 수 없습니다." + PROPERTIES_FILE);
                return;
            }
            properties = new Properties();
            this.properties.load(input);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getPropertyByString(String str){
        return properties.getProperty(str);
    }
}
