package org.example.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseUtils {

    private String code;
    private String message;
    private Map<String,Object> map=new LinkedHashMap<>();

    public ResponseUtils()
    {
        this.code="0";
        this.message="success";
    }

    public  ResponseUtils(String code,String message)
    {
        this.code=code;
        this.message=message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public ResponseUtils put(String key,Object value)
    {
            this.map.put(key,value);
            return this;
    }
}
