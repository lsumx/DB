package entity;

public class GradesEntity {
    String course_name;
    String course_id;
    String grades;

    public GradesEntity(String course_id,String name,String grade){
        this.course_name =name;
        this.course_id =course_id;
        this.grades =grade;
    }
    public String getCourse_name() {
        return course_name;
    }

    public String getGrades() {
        return grades;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
