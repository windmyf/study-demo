package com.myf.wind.base.reflectdemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
     * 工厂初始化
     * 读取文件
     */
    public void initFactory(String fileName) {

        // 读取文件内容
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            // 循环创建对象
            for (String name : properties.stringPropertyNames()) {
                objectMap.put(name,createObject(properties.getProperty(name)));
            }

        } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("读取文件异常，文件名称为：" + fileName);
        }

    }

    /**
     * 根据对象全名利用反射创建对象
     * 并存入map中
     * @param className 类全名
     */
    private Object createObject(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 获取class对象
        Class<?> clazz = Class.forName(className);
        return clazz.newInstance();
    }

    /**
     * 对象获取
     */
    public Object getObject(String className){
        return objectMap.get(className);
    }

    public static void main(String[] args) {
        ObjectPoolFactory objectPoolFactory = new ObjectPoolFactory();
        // 此处需绝对路径
        objectPoolFactory.initFactory("src/main/java/com/myf/wind/base/reflectdemo/object.txt");
        Object drum = objectMap.get("drum");
        System.out.println("drum = " + drum);
        Object guitar = objectMap.get("guitar");
        System.out.println("guitar = " + guitar);

    }
}
