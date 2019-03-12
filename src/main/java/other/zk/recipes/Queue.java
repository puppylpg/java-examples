package other.zk.recipes;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Producer-Consumer queue
 */
public class Queue extends SyncPrimitive {

    /**
     * Constructor of producer-consumer queue
     */
    private Queue(String address, String name) {
        super(address);
        this.root = name;
        // Create ZK node name
        if (zk != null) {
            try {
                Stat s = zk.exists(root, false);
                if (s == null) {
                    zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                System.out.println("Keeper exception when instantiating queue: " + e.toString());
            } catch (InterruptedException e) {
                System.out.println("Interrupted exception");
            }
        }
    }

    /**
     * Add element to the queue.
     */
    private void produce(int i) throws KeeperException, InterruptedException {
        ByteBuffer b = ByteBuffer.allocate(4);
        byte[] value;

        // Add child with value i
        b.putInt(i);
        value = b.array();
        zk.create(root + "/element", value, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
    }


    /**
     * Remove first element from the queue.
     */
    private int consume() throws KeeperException, InterruptedException {
        int retvalue = -1;
        Stat stat = null;

        // Get the first element available
        while (true) {
            synchronized (MUTEX) {
                List<String> list = zk.getChildren(root, true);
                if (list.size() == 0) {
                    System.out.println("Going to wait");
                    MUTEX.wait();
                } else {
                    Integer min = new Integer(list.get(0).substring(7));
                    String minNode = list.get(0);
                    for (String s : list) {
                        Integer tempValue = new Integer(s.substring(7));
                        //System.out.println("Temporary value: " + tempValue);
                        if (tempValue < min) {
                            min = tempValue;
                            minNode = s;
                        }
                    }
                    System.out.println("Temporary value: " + root + "/" + minNode);
                    byte[] b = zk.getData(root + "/" + minNode,
                            false, stat);
                    zk.delete(root + "/" + minNode, 0);
                    ByteBuffer buffer = ByteBuffer.wrap(b);
                    retvalue = buffer.getInt();

                    return retvalue;
                }
            }
        }
    }

    public static void main(String... args) {
        Queue q = new Queue(args[1], "/app1");

        System.out.println("Input: " + args[1]);
        int i;
        Integer max = new Integer(args[2]);

        if (args[3].equals("p")) {
            System.out.println("Producer");
            for (i = 0; i < max; i++)
                try {
                    q.produce(10 + i);
                } catch (KeeperException | InterruptedException e) {
                    // do nothing...
                }
        } else {
            System.out.println("Consumer");

            for (i = 0; i < max; i++) {
                try {
                    int r = q.consume();
                    System.out.println("Item: " + r);
                } catch (KeeperException e) {
                    i--;
                } catch (InterruptedException e) {
                    // do nothing...
                }
            }
        }
    }

}
