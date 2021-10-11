package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;
import javafx.event.Event;

class SrtRecord {

    int no = 0;
    long in = 0, out = 0;
    String sub = "";

    SrtRecord(int no, long in, long out, String sub) {
        this.no = no;
        this.in = in;
        this.out = out;
        this.sub = sub;
    }

}

public class SrtLoader {

    Controller controller;
    boolean ready = false;
    String fileName = "";
    boolean fileExist = false;
    ArrayList<SrtRecord> records = new ArrayList<SrtRecord>();
    Long srtDuration = 0L;
    Pattern srtPattern = Pattern.compile(
        "(\\d+\n)"+
        "(\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d[ ]*-+>\\s*\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d[ ]*\\n)"+
        "((?:.+\\n)+)"+
        "(\\n)");

    SrtLoader(Controller controller) {
        this.controller = controller;
    }

    public void load(String filename){
        File srtFile = new File(filename);
        ArrayList<SrtRecord> tempRecords = new ArrayList<SrtRecord>();
        try {
            UnicodeReader reader = new UnicodeReader(new FileInputStream(srtFile), "UTF-8");
            BufferedReader bf = new BufferedReader(reader);
            String line = "";
            String text = "";
            while ((line = bf.readLine()) != null){
                text = text + line + "\n";
                if (bf.ready() == false){
                    if (line.isEmpty()==false){
                        text += "\n";
                    }
                }
            }
            bf.close();
            Matcher matcher = srtPattern.matcher(text);
            while (matcher.find()){
                int no = 0;
                long in = 0, out = 0;
                String sub = "";
                no = Integer.valueOf(matcher.group(1).replaceAll("\n", ""));
                String[] temp = matcher.group(2).replaceAll("\\s","").split("-+>");
                {
                int inH = Character.getNumericValue(temp[0].toCharArray()[0]) * 10 + Character.getNumericValue(temp[0].toCharArray()[1]);
                int inM = Character.getNumericValue(temp[0].toCharArray()[3]) * 10 + Character.getNumericValue(temp[0].toCharArray()[4]);
                int inS = Character.getNumericValue(temp[0].toCharArray()[6]) * 10 + Character.getNumericValue(temp[0].toCharArray()[7]);
                int inMS = Character.getNumericValue(temp[0].toCharArray()[9]) * 100 + Character.getNumericValue(temp[0].toCharArray()[10]) * 10 + Character.getNumericValue(temp[0].toCharArray()[11]);
                in = inH*3600000 + inM*60000 + inS*1000 + inMS;
                int outH = Character.getNumericValue(temp[1].toCharArray()[0]) * 10 + Character.getNumericValue(temp[1].toCharArray()[1]);
                int outM = Character.getNumericValue(temp[1].toCharArray()[3]) * 10 + Character.getNumericValue(temp[1].toCharArray()[4]);
                int outS = Character.getNumericValue(temp[1].toCharArray()[6]) * 10 + Character.getNumericValue(temp[1].toCharArray()[7]);
                int outMS = Character.getNumericValue(temp[1].toCharArray()[9]) * 100 + Character.getNumericValue(temp[1].toCharArray()[10]) * 10 + Character.getNumericValue(temp[1].toCharArray()[11]);
                out = outH*3600000 + outM*60000 + outS*1000 + outMS;
                }
                sub = matcher.group(3);
                SrtRecord srtRecord = new SrtRecord(no, in, out, sub);
                int recordsSize = tempRecords.size();
                if (recordsSize != 0){
                    Long lastRecordOut = tempRecords.get(recordsSize-1).out;
                    if (srtRecord.out >= srtRecord.in && srtRecord.in >= lastRecordOut){
                        tempRecords.add(srtRecord);
                    }
                }else{
                    if(srtRecord.out >= srtRecord.in){
                        tempRecords.add(srtRecord);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Event.fireEvent(this.controller.textArea, new AppEvent(this, AppEvent.SRT_FAIL));
            e.printStackTrace();
            return;
        } catch (IOException e) {
            Event.fireEvent(this.controller.textArea, new AppEvent(this, AppEvent.SRT_FAIL));
            e.printStackTrace();
            return;
        } catch (Exception e){
            return;
        }
        if (tempRecords.size() == 0){
            Event.fireEvent(this.controller.textArea, new AppEvent(this, AppEvent.SRT_FAIL));
            return;
        }
        this.records.clear();
        for (SrtRecord srtRecord : tempRecords) {this.records.add(srtRecord);}
        this.fileName = srtFile.getName();
        this.fileExist = srtFile.exists();
        this.srtDuration = this.records.get(this.records.size()-1).out;
        this.ready = true;
        Event.fireEvent(this.controller.sldTime, new AppEvent(this, AppEvent.SRT_READY));
    }
}