package com.myf.wind.base.reflectdemo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : wind-myf
 * @desc : 对象池工厂
 * @version : 1.0
 */
public class ObjectPoolFactory {
    /**
     * 定义一个map存储对象，key-对象名，value-对象
     */
    private static Map<String,Object> objectMap = new HashMap<>();

    /**
     * TODO
     * 工厂初始化
     * 读取文件
     */
    public void initFactory(String fileName){

        // 读取文件内容

        // 循环创建对象
    }

    /**
     * TODO
     * 根据对象全名利用反射创建对象
     * 并存入map中
     * @param className 类全名
     */
    private void createObject(String className){

    }

    /**
     * 对象获取
     */
    public Object getObject(String className){
        return objectMap.get(className);
    }
}
