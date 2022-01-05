package com.myf.wind;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 *
 * @author wind_myf
 */
public class App {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        // new zk时传入watch，这个是session级别的，跟path和node没有关系
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 3000,
                    new Watcher() {
                        // 回调方法
                        @Override
                        public void process(WatchedEvent event) {
                            System.out.println("---------------------------");
                            Event.KeeperState state = event.getState();
                            Event.EventType type = event.getType();
                            String path = event.getPath();
                            System.out.println(event.toString());
                            switch (state) {
                                case Unknown:
                                    break;
                                case Disconnected:
                                    break;
                                case NoSyncConnected:
                                    break;
                                case SyncConnected:
                                    countDownLatch.countDown();
                                    System.out.println("connected");
                                    break;
                                case AuthFailed:
                                    System.out.println("AuthFailed");
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

                            switch (type) {
                                case None:
                                    break;
                                case NodeCreated:
                                    break;
                                case NodeDeleted:
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
                    });
            countDownLatch.await();
            ZooKeeper.States states = zooKeeper.getState();
            switch (states) {
                case CONNECTING:
                    System.out.println("connecting--");
                    break;
                case ASSOCIATING:
                    break;
                case CONNECTED:
                    System.out.println("connected--");
                    break;
                case CONNECTEDREADONLY:
                    break;
                case CLOSED:
                    break;
                case AUTH_FAILED:
                    break;
                case NOT_CONNECTED:
                    break;
            }
            String pathName = zooKeeper.create("/orange", "olddata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            Stat stat = new Stat();
            byte[] data = zooKeeper.getData("/orange", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("getData event" + event.toString());
                }
            }, stat);
            System.out.println("data = " + new String(data));
            Stat orange = zooKeeper.setData("/orange", "newData".getBytes(), 0);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
