package app;

import javafx.event.Event;
import javafx.event.EventType;

public class AppEvent extends Event{

    private static final long serialVersionUID = 1L;
    public SrtLoader srtLoader;
    public String sub;
    public Controller controller;
    

    public static final EventType<AppEvent> TICK = new EventType<>(ANY,"TICK");
    public static final EventType<AppEvent> SRT_READY = new EventType<>(ANY,"SRT_READY");
    public static final EventType<AppEvent> SRT_FAIL = new EventType<>(ANY,"SRT_FAIL");
    public static final EventType<AppEvent> SHOW_SUB = new EventType<>(ANY,"SHOW_SUB");
    public static final EventType<AppEvent> MOUSE_LEAVE = new EventType<>(ANY,"MOUSE_LEAVE");

    public AppEvent(Controller controller, EventType<? extends AppEvent> eventType) {
        super(eventType);
        this.controller = controller;
    }

    public AppEvent(SrtLoader srtLoader, EventType<? extends AppEvent> eventType) {
        super(eventType);
        this.srtLoader = srtLoader;
    }

    public AppEvent(String sub, Controller controller, EventType<? extends AppEvent> eventType){
        super(eventType);
        this.sub = sub;
        this.controller = controller;
    }

    public AppEvent(EventType<? extends AppEvent> eventType){
        super(eventType);
    }
}
