import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static org.apache.zookeeper.Watcher.Event.EventType.NodeChildrenChanged;
import static org.apache.zookeeper.Watcher.Event.EventType.NodeCreated;
import static org.apache.zookeeper.Watcher.Event.EventType.NodeDeleted;

public class DataMonitor implements Watcher {

    private ZooKeeper zooKeeper;
    private String nodeName;
    private String exec;
    private Process process;
    private NodeChildrenWatcher childrenWatcher;

    DataMonitor(ZooKeeper zooKeeper, String nodeName, String exec) {
        this.zooKeeper = zooKeeper;
        this.nodeName = nodeName;
        this.exec = exec;
        registerAsWatcher();
        childrenWatcher = new NodeChildrenWatcher(zooKeeper, nodeName);
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case NodeCreated:
                onNodeCreated();
                break;
            case NodeDeleted:
                onNodeDeleted();
                break;
            default:
                System.out.println("Parent: Unknown event type");
                break;
        }
        registerAsWatcher();
        childrenWatcher.registerAsWatcher();
    }

    private void registerAsWatcher() {
        try {
            zooKeeper.exists(nodeName, this);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void onNodeCreated() {
        Runtime runtime = Runtime.getRuntime();
        try {
            process = runtime.exec(exec);
        } catch (IOException e) {
            System.out.println("Error while executing process" + e);
        }
    }

    private void onNodeDeleted() {
        if (process != null) {
            process.destroyForcibly();
            process = null;
        }
    }

}
