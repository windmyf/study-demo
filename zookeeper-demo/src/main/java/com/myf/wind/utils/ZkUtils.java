package com.myf.wind.utils;

import com.myf.wind.config.DefaultWatch;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author : wind-myf
 * @desc :  zookeeper工具类
 * @version : 1.0
 */
public class ZkUtils {
    private static ZooKeeper zooKeeper;

    /**
     * 替换为Zookeeper服务器ip
     */
    private static String address = "47.96.167.160:2181/testLock";
    private static CountDownLatch downLatch = new CountDownLatch(1);
    private static DefaultWatch watch = new DefaultWatch();
    public static ZooKeeper getZooKeeper(){
        try {
            zooKeeper = new ZooKeeper(address,1000,watch);
            watch.setDownLatch(downLatch);
            downLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }
}
