package com.myf.wind.config;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author : wind-myf
 * @desc : 回调用
 * @version : 1.0
 */
public class WatchCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    ZooKeeper zooKeeper;
    MyConfig myConfig;
    CountDownLatch count = new CountDownLatch(1);

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void setMyConfig(MyConfig myConfig) {
        this.myConfig = myConfig;
    }

    public void await(){
        zooKeeper.exists("/AppConf",this,this,"ABC");
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {

        switch (event.getType()) {
            case None:
                System.out.println("None");
                break;
            case NodeCreated:
                System.out.println("NodeCreated");
                zooKeeper.getData("/AppConf",this,this,"aaa");
                break;
            case NodeDeleted:
                System.out.println("NodeDeleted");
                myConfig.setConf("");
                count = new CountDownLatch(1);
                break;
            case NodeDataChanged:
                System.out.println("NodeDataChanged");
                zooKeeper.getData("/AppConf",this,this,"aaa");
                break;
            case NodeChildrenChanged:
                System.out.println("NodeChildrenChanged");
                break;
            case DataWatchRemoved:
                System.out.println("DataWatchRemoved");
                break;
            case ChildWatchRemoved:
                System.out.println("ChildWatchRemoved");
                break;
            case PersistentWatchRemoved:
                System.out.println("PersistentWatchRemoved");
                break;
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        if (data != null){
            String s = new String(data);
            myConfig.setConf(s);
            count.countDown();
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {

        // 节点已经存在
        if (stat != null){
            zooKeeper.getData("/AppConf",this,this,"aaa");
        }
    }
}
