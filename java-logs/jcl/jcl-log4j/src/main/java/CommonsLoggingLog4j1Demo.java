import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author puppylpg on 2021/08/10
 */
public class CommonsLoggingLog4j1Demo {

    private static final Log log = LogFactory.getLog(CommonsLoggingLog4j1Demo.class);

    public static void main(String[] args) {
        log.trace("Trace log message");
        log.debug("Debug log message");
        log.info("Info log message");
        log.error("Error log message");
    }
}
