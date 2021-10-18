import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author liuhaibo on 2021/08/10
 */
public class CommonsLoggingLog4j2Demo {

    private static final Log log = LogFactory.getLog(CommonsLoggingLog4j2Demo.class);

    public static void main(String[] args) {
        log.trace("Trace log message");
        log.debug("Debug log message");
        log.info("Info log message");
        log.error("Error log message");
    }
}