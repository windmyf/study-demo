package com.myf.wind.lock;

import com.myf.wind.utils.ZkUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : wind-myf
 * @desc : 分布式锁测试
 * @version : 1.0
 */
public class LockTest {
    ZooKeeper zooKeeper;

    @Before
    public void conn(){
        zooKeeper = ZkUtils.getZooKeeper();
    }

    @After
    public void close(){
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lock(){
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    WatchCallBack watchCallBack = new WatchCallBack();
                    watchCallBack.setZooKeeper(zooKeeper);
                    String name = Thread.currentThread().getName();
                    watchCallBack.setThreadName(name);
                    // 线程抢锁并释放
                    watchCallBack.tryLock();
                    // 业务代码
                    System.out.println(name + "working.......");
                    // 释放锁
                    watchCallBack.unLock();
                }
            }.start();
        }

        while (true) {


        }
    }
}
