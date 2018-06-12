import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;

import static org.apache.zookeeper.Watcher.Event.EventType.NodeChildrenChanged;

class NodeChildrenWatcher implements Watcher {

    private ZooKeeper zooKeeper;
    private String nodeName;

    NodeChildrenWatcher(ZooKeeper zooKeeper, String nodeName) {
        this.zooKeeper = zooKeeper;
        this.nodeName = nodeName;
        registerAsWatcher();
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType().equals(NodeChildrenChanged))
            onChildrenChange();
        registerAsWatcher();
    }

    void registerAsWatcher() {
        try {
            if (zooKeeper.exists(nodeName, false) != null) {
                Queue<String> queue = new LinkedList<>();
                queue.add(nodeName);

                while (!queue.isEmpty()) {
                    String node = queue.poll();
                    for (String child : zooKeeper.getChildren(node, this))
                        queue.add(node + "/" + child);
                }
            }

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void onChildrenChange() {
        Queue<String> queue = new LinkedList<>();
        queue.add(nodeName);

        int count = -1;

        try {
            while (!queue.isEmpty()) {
                String node = queue.poll();
                count++;
                for (String child : zooKeeper.getChildren(node, true))
                    queue.add(node + "/" + child);
            }
        } catch (KeeperException | InterruptedException e) {
            System.out.println("Error while counting children" + e);
        }
        try {
            zooKeeper.setData("/children", String.valueOf(count).getBytes(), -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Children count: " + count);
    }
}
