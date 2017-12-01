package example.logs.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To switch between logging frameworks you need only to uncomment needed framework dependencies in pom.xml
 *
 * Since we are using `logback-classic`, it'll use logback.xml as configuration.
 * So it can only have 2 appenders: stdout & fileout.
 */
public class Slf4jDemo {
    private static Logger logger = LoggerFactory.getLogger(Slf4jDemo.class);

    public static void main(String[] args) {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");

        String variable = "Hello John";
        logger.debug("Printing variable value {} ", variable);
    }
}
