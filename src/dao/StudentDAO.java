package dao;


import Utils.JDBCUtils;
import entity.CourseEntity;
import entity.GradesEntity;
import entity.StudentEntity;
import entity.TakeEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//登录，选课，退课
//数据库执行语句

//没有考虑时间冲突
public class StudentDAO {
    JDBCUtils utils =new JDBCUtils();

   public boolean login_success(String id,String password){

       try {
           String sql ="select * from student where s_id = ?";
           Connection connection = utils.getConnection();
           PreparedStatement ps =connection.prepareStatement(sql);
           ps.setString(1,id);
           ResultSet resultSet = ps.executeQuery();
           if (resultSet.next() && password.equals("000000")){
               System.out.println("登陆成功");
//               studentEntity.setId(id);
               return true;
           }
           else {
               System.out.println("登录失败");
               return false;
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;

   }

   //成绩查询

   public ArrayList<GradesEntity> get_grades(String id){
        ArrayList<GradesEntity> gradesEntities =new ArrayList<>();
        try {
            String sql ="select course_id,grade from takes where s_id =?";
            PreparedStatement statement =utils.getStatement(sql);
            statement.setString(1,id);
            statement.execute();
            if (statement.getResultSet()!=null)
                while (statement.getResultSet().next()){
                    String key = statement.getResultSet().getString(1);
                    String grade =statement.getResultSet().getString(2);
                    String sql2 ="select title from course where course_id =?";
                    PreparedStatement statement1 =utils.getStatement(sql2);
                    statement1.setString(1,key);
                    statement1.execute();
                    String name ="";
                    if (statement1.getResultSet().next())
                        name =statement1.getResultSet().getString(1);
                    gradesEntities.add(new GradesEntity(key,name,grade));
                }
                return gradesEntities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
   }

//课表查看
    public ArrayList<CourseEntity> getCourses(String id){
        ArrayList<CourseEntity> courseEntities =new ArrayList<>();
        String room_number ="";
        String course_name ="";
        String exam_time ="";
        String exam_type ="";
        String course_id ="";
        try {
            String sql1 ="select course_id,sec_id from takes where s_id =?";
            PreparedStatement statement =utils.getStatement(sql1);
            statement.setString(1,id);
            statement.execute();
            if (statement.getResultSet()!=null){
                while (statement.getResultSet().next()){
//这是每一节课的相关内容
                    course_id =statement.getResultSet().getString(1);
                    String sec_id =statement.getResultSet().getString(2);
                    String sql2 = "select room_number,time_slot_id,exam_id from section where course_id =? and sec_id =?";
                    PreparedStatement statement1 =utils.getStatement(sql2);
                    statement1.setString(1,course_id);
                    statement1.setString(2,sec_id);
                    statement1.execute();
//                    //用来查询room_number和time
                    ResultSet resultSet =statement1.getResultSet();
//                    获得相应的课程id
                    String course_time ="";
                    while (resultSet.next()){
//                        1次
                        room_number =resultSet.getString(1);
                        String time_slot_id =resultSet.getString(2);
                        String exam_id =resultSet.getString(3);
//                        System.out.println(room_number +" "+time_slot_id+" "+exam_id);
                        String[] times =time_slot_id.split(",");
//                        1次
                        for (int i =0;i<times.length;i++){
                            System.out.println(1);
                            String sql3 ="select day,start_time,end_time from time_slot where time_slot_id =?";
                            PreparedStatement statement2 =utils.getStatement(sql3);
                            statement2.setString(1,times[i]);
                            statement2.execute();
//                             course_time ="";
                            if (statement2.getResultSet().next()){
                                course_time +=" "+statement2.getResultSet().getString(1)+" "+statement2.getResultSet().getString(2)+"-"+statement2.getResultSet().getString(3)+"\n";
                            }
                        }
                        String sql4 ="select day,start_time,end_time,exam_type from exam where exam_id=?";
                        PreparedStatement statement2 =utils.getStatement(sql4);
                        statement2.setString(1,exam_id);
//                        System.out.println(sql4);
                        statement2.execute();
//                        1次
                        if (statement2.getResultSet().next()){
                            exam_time =statement2.getResultSet().getString(1)+" "+statement2.getResultSet().getString(2)+"-"+statement2.getResultSet().getString(3);
                            exam_type =statement2.getResultSet().getString(4);
                        }
                        String sql5 ="select title from course where course_id =?";
                        PreparedStatement preparedStatement =utils.getStatement(sql5);
//                        System.out.println(course_id);
                        preparedStatement.setString(1,course_id);
                        preparedStatement.execute();
                        if (preparedStatement.getResultSet().next())
                            course_name = preparedStatement.getResultSet().getString(1);
//                        System.out.println(exam_time+" \n"+exam_type);


                    }
                    courseEntities.add(new CourseEntity(course_id,course_name,course_time,room_number,exam_time,exam_type,""));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseEntities;
    }



    public ArrayList<CourseEntity> getAllCourse(){
        ArrayList<CourseEntity> courseEntities =new ArrayList<>();
        String room_number ="";
        String course_name ="";
        String exam_time ="";
        String exam_type ="";
        String course_id ="";
        String number="";
        try {
            String sql1 ="select course_id,sec_id from section ";
            PreparedStatement statement =utils.getStatement(sql1);
//            statement.setString(1,id);
            statement.execute();
                while (statement.getResultSet().next()){

//这是每一节课的相关内容
                    course_id =statement.getResultSet().getString(1);
                    String sec_id =statement.getResultSet().getString(2);
                    String sql6 ="select count(*) from takes where course_id =? and sec_id =?";
                    PreparedStatement preparedStatementA =utils.getStatement(sql6);
                    preparedStatementA.setString(1,course_id);
                    preparedStatementA.setString(2,sec_id);
                    preparedStatementA.execute();
                    if (preparedStatementA.getResultSet().next())
                    number =preparedStatementA.getResultSet().getInt(1)+"/";
                    String sql2 = "select room_number,time_slot_id,exam_id from section where course_id =? and sec_id =?";
                    PreparedStatement statement1 =utils.getStatement(sql2);
                    statement1.setString(1,course_id);
                    statement1.setString(2,sec_id);
                    statement1.execute();
//                    //用来查询room_number和time
                    ResultSet resultSet =statement1.getResultSet();
//                    获得相应的课程id
                    String course_time ="";
                    while (resultSet.next()){
//                        1次
                        room_number =resultSet.getString(1);
                        String time_slot_id =resultSet.getString(2);
                        String exam_id =resultSet.getString(3);
//                        System.out.println(room_number +" "+time_slot_id+" "+exam_id);
                        String[] times =time_slot_id.split(",");
//                        1次
                        for (int i =0;i<times.length;i++){
//                            System.out.println(1);
                            String sql3 ="select day,start_time,end_time from time_slot where time_slot_id =?";
                            PreparedStatement statement2 =utils.getStatement(sql3);
                            statement2.setString(1,times[i]);
                            statement2.execute();
//                             course_time ="";
                            if (statement2.getResultSet().next()){
                                course_time +=" "+statement2.getResultSet().getString(1)+" "+statement2.getResultSet().getString(2)+"-"+statement2.getResultSet().getString(3)+"\n";
                            }
                        }
                        String sql4 ="select day,start_time,end_time,exam_type from exam where exam_id=?";
                        PreparedStatement statement2 =utils.getStatement(sql4);
                        statement2.setString(1,exam_id);
//                        System.out.println(sql4);
                        statement2.execute();
//                        1次
                        if (statement2.getResultSet().next()){
                            exam_time =statement2.getResultSet().getString(1)+" "+statement2.getResultSet().getString(2)+"-"+statement2.getResultSet().getString(3);
                            exam_type =statement2.getResultSet().getString(4);
                        }
                        String sql5 ="select title,number from course where course_id =?";
                        PreparedStatement preparedStatement =utils.getStatement(sql5);
//                        System.out.println(course_id);
                        preparedStatement.setString(1,course_id);
                        preparedStatement.execute();
                        if (preparedStatement.getResultSet().next()){
                            course_name = preparedStatement.getResultSet().getString(1);
                            number +=preparedStatement.getResultSet().getInt(2);
//                            System.out.println(number);
                        }


//                        System.out.println(exam_time+" \n"+exam_type);


                    }
                    System.out.println(course_id +" "+course_name+" "+course_time+" " +room_number+" "+exam_time+ " "+exam_type +" "+number);
                    courseEntities.add(new CourseEntity(course_id,course_name,course_time,room_number,exam_time,exam_type,number));
                }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseEntities;
    }


    public boolean select_course(String id,String course_id){
//        System.out.println(is_time_valid());
        if (is_time_valid()){

            try {
//查看是否开设了这门课程
                String sql ="select time_slot_id,exam_id from section where course_id=? ";
                PreparedStatement statement =utils.getStatement(sql);
                statement.setString(1,course_id);
//                statement.setString(2);
                statement.execute();
                ResultSet resultSet =statement.getResultSet();
                String time_select ="";
                String time_exam ="";
                if (resultSet.next()){
                    //开设了这门课程，选择已经选课的课程
                    time_select =resultSet.getString(1);
                    time_exam =resultSet.getString(2);
                    String sql_time ="select course_id from takes where s_id =?";
                    PreparedStatement statement_course =utils.getStatement(sql_time);
                    statement_course.setString(1,id);
                    statement_course.execute();

                    ArrayList<String> times =new ArrayList<>();
                    ArrayList<String> exam_times =new ArrayList<>();
                    if (statement_course.getResultSet().next()){
//                        选课集合
                        String courseId =statement_course.getResultSet().getString(1);
                        String select_time ="select time_slot_id,exam_id from section where course_id =? ";
                        PreparedStatement statement1 =utils.getStatement(select_time);
                        statement1.setString(1,courseId);
                        statement1.execute();
                        if (statement1.getResultSet().next()){
                            String time[] =statement1.getResultSet().getString(1).split(",");
                            exam_times.add(statement1.getResultSet().getString(2));
                            for (int i=0;i<time.length;i++){
                                times.add(time[i]);
                            }
                        }
                    }
                    if (!is_time_conflict(times,time_select) ||!is_time_conflict(exam_times,time_exam)){
                        System.out.println("时间冲突");
                    }else {
                        //                    这门课程是否已经选过，人数已满
                        if (select_valid(id,course_id)){
//                            System.out.println(course_id);
                            String sql_insert ="insert into takes(s_id,course_id) values(?,?)";
                            PreparedStatement statement1 =utils.getStatement(sql_insert);
                            statement1.setString(1,id);
                            statement1.setString(2,course_id);
//                        System.out.println(statement1.);//
                            statement1.executeUpdate();
                            return true;
                        }else {
                            System.out.println("人数已满不可选或您已经修读过该课程");
                            return false;
                        }
                    }


                }else {
                    return  false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;

    }



    public void quit(String id,String course_id){
        try {
            if (is_time_valid()){
                String sql_select ="select * from takes where s_id =? and course_id =?";
                PreparedStatement statement =utils.getStatement(sql_select);
                statement.setString(1,id);
                statement.setString(2,course_id);
                statement.execute();
                if (statement.getResultSet().next()){
                    String sql ="delete from takes where s_id =? and course_id =?";
                    PreparedStatement preparedStatement =utils.getStatement(sql);
                    preparedStatement.setString(1,id);
                    preparedStatement.setString(2,course_id);
                    preparedStatement.execute();
                }else {
                    System.out.println("课程号出错");
                }

            }else {
                System.out.println("不在退课时间段内");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public boolean is_time_valid(){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse("2019-12-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse("2019-12-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date =new Date();
        if (date.getTime()<=date2.getTime() && date.getTime()>=date1.getTime())
            return true;
        return false;
    }

//选课的条件限制
    public boolean select_valid(String id,String course_id){
//        是否选过该课程
        try {
            String sql1 ="select * from takes where s_id=? and course_id=?";
            PreparedStatement statement =utils.getStatement(sql1);
            statement.setString(1,id);
            statement.setString(2,course_id);
            statement.execute();
            ResultSet resultSet =statement.getResultSet();
            if (resultSet.next())
                return false;
            else {
                //时间冲突
                String sql ="select count(*) from takes where course_id=?";
                PreparedStatement statement1 =utils.getStatement(sql);
                statement1.setString(1,course_id);
                statement1.execute();
                ResultSet resultSet1 =statement1.getResultSet();
                System.out.println(resultSet1);
                int row=0;
                if (resultSet1.next())
                    row = resultSet1.getInt(1);
//                System.out.println("row"+row);
                String sql2 ="select number from course where course_id=?";
                PreparedStatement preparedStatement =utils.getStatement(sql2);
                preparedStatement.setString(1,course_id);
                preparedStatement.execute();
               String number ="";
                if (preparedStatement.getResultSet().next()){
                    number =preparedStatement.getResultSet().getString(1);
//                    System.out.println("number"+number);
                    if (row<Integer.parseInt(number))
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean select_for_course(String id,String course_id,String reasons){
        try {
            if (is_time_valid()){
                if (can_select(id, course_id)){
                    String sql_insert ="select * from course_quit where s_id =? and course_id =?";
                    PreparedStatement statement =utils.getStatement(sql_insert);
                    statement.setString(1,id);
                    statement.setString(2,course_id);
//                    System.out.println(id);
                    statement.execute();
                    if (!statement.getResultSet().next()){
                        String sql_select ="select * from course_select where s_id =? and course_id =?";
                        PreparedStatement preparedStatement =utils.getStatement(sql_select);
                        preparedStatement.setString(1,id);
                        preparedStatement.setString(2,course_id);
                        preparedStatement.execute();
                        if (preparedStatement.getResultSet().next()){
                            System.out.println("你已经选该门课的选课申请");
                            return false;
                        }else {
                            String sql ="insert into course_select(s_id,course_id,reason) values(?,?,?)";
                            PreparedStatement ss =utils.getStatement(sql);
                            ss.setString(1,id);
                            ss.setString(2,course_id);
                            ss.setString(3,reasons);
                            ss.executeUpdate();
                        }

                    }else {
                        System.out.println("你已经退过该课程，不能选课申请");
                        return false;
                    }

                }else {
                    System.out.println("不符合选课申请条件");
                    return false;
                }
            }else {
                System.out.println("不在选课申请时间段内");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

//选课申请的条件没有考虑时间冲突
    public  boolean can_select(String id,String course_id){
        try {
            String sql1 ="select * from takes where s_id=? and course_id=?";
            PreparedStatement statement =utils.getStatement(sql1);
            statement.setString(1,id);
            statement.setString(2,course_id);
            statement.execute();
            ResultSet resultSet =statement.getResultSet();
            if (resultSet.next())
                return false;
            else {
                String sql ="select count(*) from takes where course_id=?";
                PreparedStatement statement1 =utils.getStatement(sql);
                statement1.setString(1,course_id);
                statement1.execute();
                ResultSet resultSet1 =statement1.getResultSet();
                int row =0;
                if (resultSet1.next())
                    row =Integer.parseInt(resultSet1.getString(1)) ;
                System.out.println(row);
//                这门课的最大人数
                String sql2 ="select number from course where course_id=?";
                PreparedStatement preparedStatement =utils.getStatement(sql2);
                preparedStatement.setString(1,course_id);
                preparedStatement.execute();
//                教室可以容纳的人数
                String sql3 ="select room_number from section where course_id =? ";
                PreparedStatement preparedStatement1 =utils.getStatement(sql3);
                preparedStatement1.setString(1,course_id);
                preparedStatement1.execute();
                String room_number ="";
                if (preparedStatement1.getResultSet().next())
                    room_number =preparedStatement1.getResultSet().getString(1);
                System.out.println(room_number);
                String sqls ="select capacity from classroom where room_number=?";
                PreparedStatement statement2 =utils.getStatement(sqls);
                statement2.setString(1,room_number);
                statement2.execute();
                int capacity =0;
                if (statement2.getResultSet().next())
                    capacity=Integer.parseInt(statement2.getResultSet().getString(1));
                System.out.println(capacity);
                int number =0;
                if (preparedStatement.getResultSet().next()){
                    number =Integer.parseInt(preparedStatement.getResultSet().getString(1));
                    if (row>=number &&row<capacity)
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean is_time_conflict(ArrayList<String> times,String time ){
        try {
            String sql ="select start_time,end_time,day from time_slot where time_slot_id =?";
            PreparedStatement preparedStatement =utils.getStatement(sql);
            preparedStatement.setString(1,time);
            preparedStatement.execute();
            String string ="";
            String string1 ="";
            String day ="";
            if (preparedStatement.getResultSet().next()){
                string =preparedStatement.getResultSet().getString(1).split(":")[0];
                string1 =preparedStatement.getResultSet().getString(2).split(":")[0];
                day =preparedStatement.getResultSet().getString(3);
            }
            for (int i =0;i<times.size();i++){
                String sql_time ="select start_time,end_time,day from time_slot where time_slot_id =?";
                PreparedStatement statement =utils.getStatement(sql_time);
                statement.setString(1,times.get(i));
                statement.execute();
                String start_time="";
                String end_time="";
                String day1 ="";
                if (statement.getResultSet().next()){
                    start_time =statement.getResultSet().getString(1).split(":")[0];
                    end_time =statement.getResultSet().getString(2).split(":")[0];
                    day1 =statement.getResultSet().getString(3);
                    if (day.equals(day1)){
//                        有交集
                        if (Integer.parseInt(start_time)<=Integer.parseInt(string1) &&Integer.parseInt(string)<=Integer.parseInt(end_time))
                            return false;
                    }


                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public ArrayList<TakeEntity> slection_list(String id ){
       ArrayList<TakeEntity> arrayList =new ArrayList<>();
       try{
           String select ="select course_id,reason,status  from course_select where s_id =? ";
           PreparedStatement statement =utils.getStatement(select);
           statement.setString(1,id);
           statement.execute();
           ResultSet resultSet =statement.getResultSet();
           while (resultSet.next()){
               String course_id =resultSet.getString(1);
               String reason =resultSet.getString(2);
               String status =resultSet.getString(3);
               if (status.equals("-1"))
                   status ="尚未处理";
               else if (status.equals("1"))
                   status ="同意申请";
               else if(status.equals("0"))
                   status ="拒绝申请";
               String sql ="select title from course where course_id =?";
               PreparedStatement course =utils.getStatement(sql);
               course.setString(1,course_id);
               course.execute();
               String title ="";
               String student_name ="";
               if (course.getResultSet().next())
                   title =course.getResultSet().getString(1);
               String sname ="select name from student where s_id =? ";
               PreparedStatement name =utils.getStatement(sname);
               name.setString(1,id);
               name.execute();
               if (name.getResultSet().next())
                   student_name =name.getResultSet().getString(1);
//               if (student_name!=null &&!student_name.equals("") && )
               arrayList.add(new TakeEntity(id,student_name,course_id,title,reason,status));


           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return arrayList;
    }


    public void quit_selection(String id,String course_id){
       try {
           String sql ="delete from course_select where s_id =? and course_id =?";
           PreparedStatement preparedStatement =utils.getStatement(sql);
           preparedStatement.setString(1,id);
           preparedStatement.setString(2,course_id);
           preparedStatement.execute();
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }


    public double totalGrades(String id){
       double grades =0;
       double totalGrades =0;
       int totalCredits =0;
       try {
           String sql ="select course_id,grade from takes where s_id =?";
           PreparedStatement statement =utils.getStatement(sql);
           statement.setString(1,id);
           statement.execute();
           ResultSet resultSet =statement.getResultSet();
           while (resultSet.next()){
               String course_id =resultSet.getString(1);
               String grade = resultSet.getString(2);
               String sql2 ="select credits from course where course_id =?";
               PreparedStatement statement1 =utils.getStatement(sql2);
               statement1.setString(1,course_id);
               statement1.execute();
               int credit =0;
               if (statement1.getResultSet().next()){
                   credit =Integer.parseInt(statement1.getResultSet().getString(1));
               }
               totalCredits +=credit;
               grades +=credit *gradeToFloat(grade);
//               System.out.println(totalCredits +"\n"+grades);

           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       totalGrades =grades/totalCredits;
       return totalGrades;
    }


    public double gradeToFloat(String grade){
       switch (grade){
           case "A":
               return 4;
           case "A-":
               return 3.7;

           case "B+":
               return 3.3;
           case "B":
               return 3;

           case "B-":
               return 2.7;

           case "C+":
               return 2.3;

           case "C":
               return 2;

           case "C-":
               return 1.7;

           case "D":
               return 1;

           case "F":
               return 0;

           default:
               return -1;



       }

    }





}
