import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public class EventListener extends Thread{

    private String messageToListenFor;
    private String messageToReplyWith;
    private Tracker eventTracker;
    //private Lock lock = new ReentrantLock();

    public EventListener(String message, String reply) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = EventTracker.getInstance();
    }

    public EventListener(String message, String reply, Tracker tracker) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = tracker;
    }

    public void run() {
        while(!readyToQuit()) {
                if (shouldReply()) {
                    reply();
                }
        }
    }

    public Boolean readyToQuit() {
        return eventTracker.has("quit");
    }

    public Boolean shouldReply() {
        return eventTracker.has(messageToListenFor);
    }

    public void reply() {
        eventTracker.handle(messageToListenFor, () -> {  //invokes eventrackers handle() method and passing in the message
            // to listen for and an eventhandler generated via lambda function
            System.out.println(messageToReplyWith);
        });
    }

}