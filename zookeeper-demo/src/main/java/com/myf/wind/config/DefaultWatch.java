package com.myf.wind.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * @author : wind-myf
 * @version : 1.0
 */
public class DefaultWatch implements Watcher {

    CountDownLatch downLatch;

    public void setDownLatch(CountDownLatch downLatch) {
        this.downLatch = downLatch;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event.toString());
        switch (event.getState()) {
            case Unknown:
                break;
            case Disconnected:
                break;
            case NoSyncConnected:
                break;
            case SyncConnected:
                downLatch.countDown();
                break;
            case AuthFailed:
                break;
            case ConnectedReadOnly:
                break;
            case SaslAuthenticated:
                break;
            case Expired:
                break;
            case Closed:
                break;
        }
    }
}
