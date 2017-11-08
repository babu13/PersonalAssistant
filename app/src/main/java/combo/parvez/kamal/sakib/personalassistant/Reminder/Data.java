package combo.parvez.kamal.sakib.personalassistant.Reminder;

/**
 * Created by sakib on 11/8/17.
 */

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by username on 11/6/17.
 */

public class Data  implements Serializable {
    private Date date;
    private String text;
    private String tarikh;
    private String time;
    private boolean fullDisplayed;
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");
    //
    public Data(){
        this.date = new Date();
    }

    public Data(long date,String text,String tarikh,String time){
        this.date = new Date(date);
        this.text = text;
        this.tarikh = tarikh;
        this.time = time;
    }

    public Data(String text,String tarikh,String time){
        this.text = text;
        this.tarikh = tarikh;
        this.time = time;
    }

    public String getDate(){
        return dateFormat.format(date);
    }
    public long getTime(){
        return date.getTime();
    }

    public void setTime(long time){
        this.date = new Date(time);
    }

    public void setText(String text)
    {
        this.text=text;
    }

    public String getText() {
        return this.text;
    }

    public String getShortText() {
        String temp = text.replaceAll("\n", " ");
        if (temp.length() > 25) {
            return temp.substring(0, 25) + "...";
        } else {
            return temp;
        }
    }

    public void setFullDisplayed(boolean fullDisplayed) {
        this.fullDisplayed = fullDisplayed;
    }

    public boolean isFullDisplayed() {
        return this.fullDisplayed;
    }
    @Override
    public String toString() {
        return this.text;
    }

    public String getTarikh(){
        return tarikh;
    }

    public String getTIME(){
        return time;
    }


    public void setTarikh(String date){
        this.tarikh = date;
    }
    public void setTIME(String time){
        this.time = time;
    }
}