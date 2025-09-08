package we.devs.opium.client.utils.thread;

import we.devs.opium.client.OpiumClient;

public class PulseThread {
    boolean locked = false;
    Runnable givenTask = null;


    ThreadStack parent;

    Thread selfThread;
    boolean run = true;

    public PulseThread(ThreadStack stack) {
        parent = stack;
    }

    public void giveTask(Runnable givenTask) {
        if(locked){
            throw new RuntimeException("Cannot assign task to a locked thread!");
        }
        this.givenTask = givenTask;
    }

    public void terminate() {
        run = false;
        OpiumClient.LOGGER.info("Stopped thread");
    }

    public void begin(int id, String prefix) {
        selfThread = new Thread(() -> {
            OpiumClient.LOGGER.info("Started {}Thread-{}", prefix, id);
            while(run) {
                if(givenTask != null) {
                    locked = true;
                    try {
                        givenTask.run();
                    } catch (Exception e) {
                        OpiumClient.LOGGER.warn("[id {}] Caught exception while running task!", id);
                        OpiumClient.throwException(e);
                    }
                    givenTask = null;
                    locked = false;
                    parent.callback();
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        selfThread.setName("%sThread-%s".formatted(prefix, id));
        selfThread.start();
    }
}
