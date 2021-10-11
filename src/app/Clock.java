package app;

import javafx.event.Event;
import java.time.Duration;
import java.util.concurrent.locks.ReentrantLock;

public class Clock implements Runnable {

    public boolean kill = false;
    public boolean playing = false;
    public long timecode_ms = 0;
    public Duration timecode_dur;
    public ReentrantLock lock = new ReentrantLock();
    public Controller controller;
    public AppEvent appEventTick;

    Clock(Controller controller){
        this.controller = controller;
        this.appEventTick = new AppEvent(this.controller, AppEvent.TICK);
    }

    public void run() {
        Long a = 0L;
        Long b = System.currentTimeMillis();
        Long c = 0L;
        Long d = 0L;
        int refreshBuffer = 1;
        while (kill == false) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            a = System.currentTimeMillis();
            c = a-b;
            if (playing == true)
            {
                lock.lock();
                try{
                    timecode_ms += c;
                    timecode_ms += d;
                }finally{
                    lock.unlock();
                }
                timecode_dur = Duration.ZERO.plusMillis(timecode_ms);
                if (refreshBuffer >= 5){refreshTimecode();refreshBuffer=0;}
            }
            b = System.currentTimeMillis();
            d = b-a;
            refreshBuffer ++;
        }
    }

    public String timecode(){
        Duration now = Duration.ofMillis(this.timecode_ms);
        long h = now.toHours();
        now = now.minus(Duration.ofHours(h));
        long m = now.toMinutes();
        now = now.minus(Duration.ofMinutes(m));
        long s = now.toSeconds();
        String hh,mm,ss;
        if (h < 10){
            hh = "0" + Long.toString(h);
        }else{
            hh = Long.toString(h);
        }
        if (m < 10){
            mm = "0" + Long.toString(m);
        }else{
            mm = Long.toString(m);
        }
        if (s < 10){
            ss = "0" + Long.toString(s);
        }else{
            ss = Long.toString(s);
        }
        String timeString = hh+":"+mm+":"+ss;
        return timeString;
    }

    public void refreshTimecode(){
        Event.fireEvent(this.controller.lblTimecode, this.appEventTick);
        Event.fireEvent(this.controller.sldTime, this.appEventTick);
    }
}
