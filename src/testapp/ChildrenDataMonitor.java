package testapp;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ChildrenDataMonitor implements Watcher {
    private ZooKeeper zooKeeper;
    private String nodeName;
    private ChildrenGUI gui;

    ChildrenDataMonitor(ZooKeeper zooKeeper, String nodeName, ChildrenGUI gui) {
        this.zooKeeper = zooKeeper;
        this.nodeName = nodeName;
        this.gui = gui;
        registerAsWatcher();
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case NodeDataChanged:
                onNodeDataChanged();
                break;
            default:
                System.out.println("Parent: Unknown event type");
                break;
        }
        registerAsWatcher();
    }

    private void registerAsWatcher() {
        try {
            zooKeeper.exists(nodeName, this);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void onNodeDataChanged() {
        try {
            byte[] data = zooKeeper.getData(nodeName, false, new Stat());
            int count = Integer.valueOf(new String(data));
            gui.changeChildrenCount(count);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
