import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author puppylpg on 2017/12/02
 */
public class LogbackDemo {

    private static final Logger log = LoggerFactory.getLogger("send.succeed");

    public static void main(String[] args) {
        log.debug("Debug log message");
        log.info("Info log message");
        log.error("Error log message");
        try {
            a();
        } catch (Exception e) {
            log.error("an error", e);
        }
    }

    public static void a() throws Exception {
        b();
    }

    public static void b() throws Exception {
        throw new Exception("an error");
    }
}