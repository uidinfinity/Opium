package we.devs.opium.client.utils;

import we.devs.opium.client.OpiumClient;

public class ExceptionHandler {

    static PulseUncaughtExceptionHandler exceptionHandler = null;

    public static PulseUncaughtExceptionHandler getExceptionHandler() {
        if(exceptionHandler == null) {
            exceptionHandler = new PulseUncaughtExceptionHandler();
        }

        return exceptionHandler;
    }

    public static class PulseUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            OpiumClient.LOGGER.getBase().warn("Exception in {}!", thread.getName());
            Util.logFormattedException(throwable);
        }

    }
}
