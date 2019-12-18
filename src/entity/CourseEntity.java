package entity;

import javafx.beans.property.SimpleStringProperty;

public class CourseEntity {
    SimpleStringProperty course_name;
    SimpleStringProperty time;
    SimpleStringProperty room_number;
    SimpleStringProperty exam_time;
    SimpleStringProperty exam_type;
    SimpleStringProperty course_id;

    public CourseEntity(String course_id,String course_name,String time,String room_number,String exam_time,String exam_type){
        this.course_name =new SimpleStringProperty(course_name);
        this.course_id =new SimpleStringProperty(course_id);
        this.time =new SimpleStringProperty(time);
        this.room_number =new SimpleStringProperty(room_number);
        this.exam_time =new SimpleStringProperty(exam_time);
        this.exam_type =new SimpleStringProperty(exam_type);
    }

    public String getCourse_name() {
        return course_name.get();
    }

    public String getCourse_id() {
        return course_id.get();
    }

    public String getTime() {
        return time.get();
    }


    public String getExam_time() {
        return exam_time.get();
    }

    public String getExam_type() {
        return exam_type.get();
    }

    public String getRoom_number() {
        return room_number.get();
    }
}
