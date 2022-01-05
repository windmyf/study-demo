package com.myf.wind.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author : wind-myf
 * @desc : 分布式锁回调
 * @version : 1.0
 */
public class WatchCallBack implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

    ZooKeeper zooKeeper;
    String threadName;
    CountDownLatch downLatch = new CountDownLatch(1);
    String pathName;

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getThreadName() {
        return threadName;
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    /**
     * 获取锁
     */
    public void tryLock(){
        try {
            zooKeeper.create("/lock",threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,this,"abc");
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放锁
     */
    public void unLock(){
        try {
            zooKeeper.delete(pathName,-1);
            System.out.println(threadName + " over work .....");
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                zooKeeper.getChildren("/",false,this,"aaa");
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
            case PersistentWatchRemoved:
                break;
        }
    }

    /**
     * create方法 callback
     */
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        if (name != null){
            System.out.println(threadName + " create node " + name);
            pathName = name;
            // 不需要监控父节点
            zooKeeper.getChildren("/",false,this,"aaa");
        }
    }

    /**
     * getChildren方法回调此方法
     */
    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        Collections.sort(children);
        System.out.println("pathName = " + pathName);
        int index = children.indexOf(pathName.substring(1));
        System.out.println(index);
        // 第一个 countDown
        if (index == 0){
            System.out.println(threadName + " i am first......");
            try {
                zooKeeper.setData("/",threadName.getBytes(),-1);
                downLatch.countDown();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            zooKeeper.exists("/" + children.get(index-1),this,this,"aaa");
        }
    }

    /**
     * 判断存在exists后回调
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        // TODO
    }
}
