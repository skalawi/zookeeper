import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Executor {

    private String host;
    private int port;
    private String exec;
    private String nodeName;

    private ZooKeeper zooKeeper;

    Executor(String host, int port, String exec, String nodeName) {
        this.host = host;
        this.port = port;
        this.exec = exec;
        this.nodeName = nodeName;
    }

    void start() {
        try {
            zooKeeper = new ZooKeeper(host + ":" + port, 10000, null);
        } catch (IOException e) {
            System.out.println("Error while creating new ZooKeper instance");
        }

        new DataMonitor(zooKeeper, nodeName, exec);
    }

    void printNodeFamily() {
        try {
            if (zooKeeper.exists(nodeName, false) != null) {
                Queue<String> queue = new LinkedList<>();
                queue.add(nodeName);
                System.out.println(nodeName);
                while (!queue.isEmpty()) {
                    String node = queue.poll();
                    List<String> children = zooKeeper.getChildren(node, false);
                    if (children.size() > 0) {
                        System.out.println("Children of " + node + ":");
                        for (String child : children) {
                            System.out.println(child);
                            queue.add(node + "/" + child);
                        }
                    }
                }
            } else {
                System.out.println("Node doesn't exist");
            }

        } catch (KeeperException | InterruptedException e) {
            System.out.println("Error while printing node family " + e);
        }
    }

    void stop() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            System.out.println("Error while closing ZooKeper instance");
        }
    }

}
