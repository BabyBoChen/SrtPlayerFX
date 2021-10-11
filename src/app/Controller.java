package app;

import java.io.File;
import java.time.Duration;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class Controller {

    @FXML public AnchorPane anchorPane;
    @FXML public Button btnOpen;
    @FXML public Label lblTimecode;
    @FXML public Button btnPlay;
    @FXML public Button btnExit;
    @FXML public Slider sldTime;
    @FXML public TextArea textArea;
    public JFrame frame;
    public Clock clock;
    private Thread clockThread;
    public SrtLoader srtLoader;
    public SrtPrompter srtPrompter;
    private Thread srtPrompterThread;
    private FileChooser fileChooser = new FileChooser();

    @FXML
    public void initialize(){
        this.clock = new Clock(this);
        this.clockThread = new Thread(this.clock);
        this.clockThread.start();
        this.srtLoader = new SrtLoader(this);
        this.srtPrompter = new SrtPrompter(this);
        this.srtPrompterThread = new Thread(this.srtPrompter);
        this.srtPrompterThread.start();
        this.textArea.addEventHandler(AppEvent.SRT_FAIL, new HandlerSrtFail());
        this.textArea.addEventHandler(AppEvent.SHOW_SUB, new HandlerShowSub());
        this.sldTime.addEventHandler(AppEvent.SRT_READY, new HandlerSrtReady());
        this.sldTime.setOnMouseDragged(e -> {HandlerSldTimeDragged.handlerSldTimeDragged(e, this);});
        this.lblTimecode.addEventHandler(AppEvent.TICK, new HandlerTick());
        this.btnPlay.setDisable(true);
        this.fileChooser.setTitle("Open a srt file...");
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SRT","*.srt"));
    }

    public void open(){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                frame.setAlwaysOnTop(false);
            }
        });
        File srtFile = fileChooser.showOpenDialog(btnOpen.getScene().getWindow());
        if (srtFile == null) {return;}
        else{
            clock.lock.lock();
            try{
                clock.playing = false;
                clock.timecode_ms = 0;
                clock.timecode_dur = Duration.ZERO;
                clock.refreshTimecode();
            }finally{
                clock.lock.unlock();
            }
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    btnPlay.setText("Play");
                }
            });            
            try{
                srtLoader.load(srtFile.getPath());
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        frame.setAlwaysOnTop(true);
                    }
                });
            }catch (Exception e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        frame.setAlwaysOnTop(true);
                    }
                });
                return;
            }
        }
    }

    public void play(){
        this.clock.lock.lock();
        try{
            this.clock.playing = !(this.clock.playing);
        }finally{
            this.clock.lock.unlock();
        }
        if (this.clock.playing == true){
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    btnPlay.setText("Pause");
                }                
            });
        }
        if (this.clock.playing == false){
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    btnPlay.setText("Play");
                }
            });
        }
    }

    public void exit(){
        this.clock.lock.lock();
        try{
            this.clock.kill = true;
        }finally{
            this.clock.lock.unlock();
        }
        Platform.exit();
        System.exit(0);
    }

}