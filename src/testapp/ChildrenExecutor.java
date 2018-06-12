package testapp;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ChildrenExecutor {
    private String host;
    private int port;
    private ChildrenGUI gui;
    private String nodeName;

    private ZooKeeper zooKeeper;

    ChildrenExecutor(String host, int port, String nodeName, ChildrenGUI gui) {
        this.host = host;
        this.port = port;
        this.gui = gui;
        this.nodeName = nodeName;
    }

    void start() {
        try {
            zooKeeper = new ZooKeeper(host + ":" + port, 10000, null);
        } catch (IOException e) {
            System.out.println("Error while creating new ZooKeper instance");
        }

        new ChildrenDataMonitor(zooKeeper, nodeName, gui);
    }

    void stop() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            System.out.println("Error while closing ZooKeper instance");
        }
    }
}
