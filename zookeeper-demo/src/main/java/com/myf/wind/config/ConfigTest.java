package com.myf.wind.config;

import com.myf.wind.utils.ZkUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigTest {
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
    public void getConn(){
        WatchCallBack watchCallBack = new WatchCallBack();
        watchCallBack.setZooKeeper(zooKeeper);
        MyConfig myConfig = new MyConfig();
        watchCallBack.setMyConfig(myConfig);
        watchCallBack.await();

        while (true){
            if ("".equals(myConfig.getConf())){
                System.out.println("myConfig is empty-----------");
                watchCallBack.await();
            }else {
                System.out.println(myConfig.getConf());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
