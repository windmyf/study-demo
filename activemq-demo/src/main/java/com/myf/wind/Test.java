package com.myf.wind;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        System.out.println(map.put("1","aa1"));
        System.out.println(map.put("1","aa2"));
        System.out.println("-----------");
        System.out.println(map.putIfAbsent("2","bb2"));
        System.out.println(map.putIfAbsent("2","bb2"));
    }
}
