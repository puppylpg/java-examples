package example.proxy.jdk;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author puppylpg on 2018/04/18
 */
@AllArgsConstructor
public class JavaCoder implements Coder {

    String name;

    public JavaCoder() {
        this("《CGlib has one important restriction: the target class must provide a default constructor》");
    }
    @Override
    public void implementDemands(String demand) {
        System.out.println(name + ": I use Java to finish " + demand);
    }

    @Override
    public void estimateTime(String demand) {
        System.out.println(name + ": I'll use " + (demand.length() + RandomUtils.nextInt(3, 9)) + " hours.");
    }

    @Override
    public String comment() {
        return "Java is perfect!";
    }

    /**
     * 这是个没卵用的方法，因为它不在协议内，所以也不会被调用到。。。
     */
    public void showAngry() {
        System.out.println(name + ": I'm very ANGRY!!!");
    }
}
