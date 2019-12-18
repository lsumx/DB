package entity;

public class TakeEntity {
    String s_id;
    String s_name;
    String course_id;
    String course_name;
    String reasons;

    public TakeEntity(String s_id,String s_name,String course_id,String course_name,String reasons){
        this.course_id =course_id;
        this.course_name =course_name;
        this.s_id =s_id;
        this.s_name =s_name;
        this.reasons =reasons;
    }



    public String getCourse_id() {
        return course_id;
    }


    public String getCourse_name() {
        return course_name;
    }

    public String getS_id() {
        return s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public String getReasons() {
        return reasons;
    }
}
