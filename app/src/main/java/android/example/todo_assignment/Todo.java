package android.example.todo_assignment;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

/**
 * Sets and retrieves data for give fragment
 */
public class Todo {

    private UUID aID;
    private String aTitle;
    private String aDetail;
    private String aDate;
    private boolean aComplete;

    public Todo(){
        aID =UUID.randomUUID();
    }

    public UUID getId(){
        return aID;
    }

    public void setId(UUID id){
        aID = id;
    }

    public String getTitle(){
        return aTitle;
    }

    public void setTitle(String title){
        aTitle = title;
    }

    public String getDate(){
        return aDate;
    }

    public void setDate(String date){
        aDate = date;
    }

    public String getDetail(){
        return aDetail;
    }

    public void setDetail(String detail){
        aDetail = detail;
    }

    public boolean isComplete() {
        return aComplete;
    }

    public void setComplete(boolean complete) {
        aComplete = complete;
    }


}
