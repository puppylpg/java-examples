import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Logger;

/**
 * @author liuhaibo on 2021/12/24
 */
public class AllLog {

    public static final Logger jul = Logger.getLogger(AllLog.class.getName());
    public static final org.apache.log4j.Logger log4j = org.apache.log4j.Logger.getLogger(AllLog.class);
    public static final org.apache.commons.logging.Log jcl = org.apache.commons.logging.LogFactory.getLog(AllLog.class);
    public static final org.slf4j.Logger slf4j = LoggerFactory.getLogger(AllLog.class);

    public static void main(String... args) {

        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();

        jul.info("jul");
        log4j.info("log4j");
        jcl.info("jcl");
        slf4j.info("slf4j");
    }
}
