package we.devs.opium.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.utils.timer.TimerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper, faster to use for large argument lists
 * @see Logger
 * @author _qweru_
 */
public class OpiumLogger {
    static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private final Logger logger;

    public void logToChat(boolean logToChat) {
        this.logToChat = logToChat;
    }

    // todo implement
    boolean logToChat = false;

    public OpiumLogger(Logger logger) {
        this.logger = logger;
    }

    public OpiumLogger(String name) {
        this.logger = LoggerFactory.getLogger(name);
    }

    public void info(String msg, Object... args) {
        String prefix = STACK_WALKER.getCallerClass().getSimpleName() + " > ";
        logger.info("{}{}", prefix, fastFormat(msg, List.of(args)));
    }

    public void warn(String msg, Object... args) {
        String prefix = STACK_WALKER.getCallerClass().getSimpleName() + " > ";
        logger.warn("{}{}", prefix, fastFormat(msg, List.of(args)));
    }

    public void error(String msg, Object... args) {
        String prefix = STACK_WALKER.getCallerClass().getSimpleName() + " > ";
        logger.error("{}{}", prefix, fastFormat(msg, List.of(args)));
    }

    public void debug(String msg, Object... args) {
        String prefix = STACK_WALKER.getCallerClass().getSimpleName() + " > ";
        if(OpiumClient.debug) logger.info("DEBUG | {}{}", prefix, fastFormat(msg, List.of(args)));
    }

    /**
     * @param str the target string
     * @param args the arguments
     * @return the string where all <code>{}</code> are replaced with the args
     */
    private static String fastFormat(String str, List<Object> args) {
        StringBuilder finalStr = new StringBuilder();
        boolean fp = false;
        int i = 0;

        args = new ArrayList<>(args);

        for (char aChar : str.toCharArray()) {
            if (args.isEmpty()) {
                finalStr.append(str.substring(i));
                break;
            }

            if (aChar == '}' && fp) {
                finalStr.append(args.remove(0));
                fp = false;
            } else if (aChar == '{') {
                fp = true;
            } else {
                if (fp) {
                    finalStr.append('{');
                    fp = false;
                }
                finalStr.append(aChar);
            }
            i++;
        }

        if (fp) {
            finalStr.append('{');
        }

        return finalStr.toString();
    }

    public TimedLogger getTimed() {
        return new TimedLogger(this);
    }

    public static class TimedLogger {
        private final OpiumLogger log;
        private final TimerUtil timer;

        private TimedLogger(OpiumLogger logger) {
            log = logger;
            timer = new TimerUtil();
            timer.reset();
        }

        public void info(String msg, Object... args) {
            log.info( STACK_WALKER.getCallerClass().getSimpleName() + " > " + msg + " (took " + timer.getFromLast() + "ms)", args);
        }

        public void warn(String msg, Object... args) {
            log.warn( STACK_WALKER.getCallerClass().getSimpleName() + " > " + msg + " (took " + timer.getFromLast() + "ms)", args);
        }

        public void error(String msg, Object... args) {
            log.error( STACK_WALKER.getCallerClass().getSimpleName() + " > " + msg + " (took " + timer.getFromLast() + "ms)", args);        }

        public void debug(String msg, Object... args) {
            log.debug( STACK_WALKER.getCallerClass().getSimpleName() + " > " + msg + " (took " + timer.getFromLast() + "ms)", args);        }
    }

    public Logger getBase() {
        return logger;
    }
}
