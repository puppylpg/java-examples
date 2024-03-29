package example.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puppylpg on 2018/12/23
 */
public class Memory {
    /**
     * 内存占位符对象，一个OOMObject大约占64K
     */
    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            // 稍作延时，令监视曲线的变化更加明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    /**
     * -Xms100m -Xmx100m -XX:+UseSerialGC
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Thread.sleep(5000);

        fillHeap(1000);
    }


}
