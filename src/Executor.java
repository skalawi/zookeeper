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
                int indent = 0;
                StringBuilder sb = new StringBuilder();
                printNodeTree(nodeName, indent, sb, zooKeeper, nodeName);
                System.out.print(sb.toString());
            } else {
                System.out.println("Node doesn't exist");
            }

        } catch (KeeperException | InterruptedException e) {
            System.out.println("Error while printing node family " + e);
        }
    }

    private static void printNodeTree(String node, int indent,
                                           StringBuilder sb, ZooKeeper zooKeeper, String totalPath) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(node);
        sb.append("\n");
        try {
            List<String> children = zooKeeper.getChildren(totalPath, false);
            if(children.size()>0) {
                for (String child : children) {
                    printNodeTree(child, indent + 1, sb, zooKeeper, totalPath+"/"+child);
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }

    void stop() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            System.out.println("Error while closing ZooKeper instance");
        }
    }

}
