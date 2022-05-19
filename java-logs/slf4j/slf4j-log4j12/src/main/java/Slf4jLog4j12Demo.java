import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuhaibo on 2017/12/02
 */
public class Slf4jLog4j12Demo {

    private static final Logger log = LoggerFactory.getLogger(Slf4jLog4j12Demo.class);

    public static void main(String[] args) {
        log.debug("Debug log message");
        log.info("Info log message");
        log.error("Error log message");
    }
}