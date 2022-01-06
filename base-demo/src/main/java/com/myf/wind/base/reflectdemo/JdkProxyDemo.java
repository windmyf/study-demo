package com.myf.wind.base.reflectdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : wind-myf
 * @desc : JDK动态代理
 */
public class JdkProxyDemo {
    public static void main(String[] args) {
        Flower target = new FlowerImpl();
        Flower flower = (Flower)MyProxyFactory.getProxy(target);
        flower.sayFlowering("春季");
        flower.queryColors();
    }

}

/**
 * 代理类工厂
 */
class MyProxyFactory{
    /**
     * 获取代理类
     */
    public static Object getProxy(Object target){
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.setTarget(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
    }
}

/**
 * 鲜花接口
 */
interface Flower {

    void sayFlowering(String season);

    List<String> queryColors();

}

/**
 * 实现类
 */
class FlowerImpl implements Flower{

    @Override
    public void sayFlowering(String season) {
        System.out.println("this is beautiful season : " + season);
    }

    @Override
    public List<String> queryColors() {
        System.out.println("this is queryColors-----");
        List<String> colors = new ArrayList<>();
        colors.add("green");
        colors.add("red");
        colors.add("blue");
        return colors;
    }
}

class MyInvocationHandler implements InvocationHandler{

    /**
     * 需要被代理的对象
     */
    private Object target;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在执行原方法前执行方法1
        FlowerUtil.flowerMethod1();
        Object result = method.invoke(target, args);
        // 在原方法执行完成后执行拦截方法二
        FlowerUtil.flowerMethod2();
        return result;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}

/**
 * 拦截方法工具类
 */
class FlowerUtil{
    /**
     * 拦截方法一
     */
    public static void flowerMethod1(){
        System.out.println("FlowerUtil--method 1------");
    }
    /**
     * 拦截方法二
     */
    public static void flowerMethod2(){
        System.out.println("FlowerUtil--method 2------");
    }
}