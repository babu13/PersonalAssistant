 package combo.parvez.kamal.sakib.personalassistant;
/**
 * Created by sakib on 10/28/17.
 */
public class Routine_info {
    public int id;
    public String time;
    public String task;

    // a helping class of database
    public Routine_info(){}//Constructor without initialized value

    public Routine_info(int id){
        this.id = id;
    }   //Constructor with one initialized value used for delete method of database

    public Routine_info(String time, String task){
        this.time = time;
        this.task = task;
    }  //Constructor for extra uses

    public Routine_info(int id, String time, String task){
        this.id = id;
        this.time = time;
        this.task = task;
    } //Constructor for saving values

    public int getId() {
        return id;
    }//getter function

    public String getTime() {
        return time;
    }//getter function

    public String getTask() {
        return task;
    }//getter function

    public void setId(int id) {
        this.id = id;
    }//setter function

    public void setTime(String time) {
        this.time = time;
    }//setter function

    public void setTask(String task) {
        this.task = task;
    }//setter function
}