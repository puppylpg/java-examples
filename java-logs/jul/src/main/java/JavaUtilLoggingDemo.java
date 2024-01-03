import java.util.logging.Logger;

/**
 * @author puppylpg on 2021/08/10
 */
public class JavaUtilLoggingDemo {

    private static final Logger logger= Logger.getLogger(JavaUtilLoggingDemo.class.getName());

    public static void main(String[] args){
        logger.info("jdk logging info: a msg");
    }
}
