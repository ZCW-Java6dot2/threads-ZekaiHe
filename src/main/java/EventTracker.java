import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker{

    private static EventTracker INSTANCE = new EventTracker();

    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventTracker();
        }
        return INSTANCE;
    }

    public Map<String, Integer> getTracker(){
        return tracker;
    }

    @Override
    synchronized public void push(String message) {
        if(tracker.containsKey(message)) {
            Integer oldValue = tracker.get(message);
            tracker.put(message, oldValue++);
        }
        else{
            tracker.put(message,1);
        }
    }

    @Override
    synchronized public Boolean has(String message) {
        return tracker.containsKey(message) && tracker.get(message)>0;
    }

    @Override
    synchronized public void handle(String message, EventHandler e) {
        e.handle(); //invokes the body of the new instance of eventhandler passed to EventListener reply method to system print the message.
        if(tracker.containsKey(message) && tracker.get(message)>1) {
            Integer oldValue = tracker.get(message);
            tracker.put(message, oldValue--);
        }
        else{
            tracker.remove(message);
        }
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }
}
