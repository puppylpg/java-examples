package example.exception.optimize;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author puppylpg on 2024/10/27
 */
public class DefaultExceptionOptimize2 {

    public static void main(String[] args) {
        String[] msg = new String[] {"a"};

        String rawTrace = null, optimizedTrace = null;
        int changedTimes = 0;

        for (int i = 0; i < 500000; i++) {
            try {
                String str = msg[1];
            } catch (Exception e) {
                String trace = ExceptionUtils.getStackTrace(e);
                System.err.println(trace);
                if (StringUtils.isEmpty(rawTrace)) {
                    rawTrace = trace;
                }

                if (StringUtils.isEmpty(optimizedTrace) && !rawTrace.equals(trace)) {
                    optimizedTrace = trace;
                    changedTimes = i;
                }
            }
        }

        System.out.println("rawTrace = " + rawTrace);
        System.out.println("optimizedTrace = " + optimizedTrace);
        System.out.println("changedTimes = " + changedTimes);
    }
}
