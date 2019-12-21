package dao;

import Utils.JDBCUtils;
import entity.CourseEntity;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ManagerDAO {

    String id = "root";
    JDBCUtils utils =new JDBCUtils();
    public String result = "";

    public ArrayList<String> header = new ArrayList<>();
    public ArrayList<ArrayList<String>> alldata = new ArrayList<>();

    public int error = 0;

    public boolean login_success(String id,String password) {
        return this.id.equals(id) && password.equals("000000");
    }

    // 查看表格
    public boolean print_table(String name) {
        result = "";
        header = new ArrayList<>();
        alldata = new ArrayList<>();
        try {
            String sql = "select * from " + name;
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData md = resultSet.getMetaData();
            int count = md.getColumnCount();
            for (int i = 1; i <= count; i++) {
                System.out.print( md.getColumnName(i) + "\t");
                header.add(md.getColumnName(i));
            }
            System.out.println("");
            while (resultSet.next()) {
                ArrayList<String> temp = new ArrayList<>();
                for (int i = 1; i <= count; i++) {
                    System.out.print(resultSet.getObject(i) + "\t");
                    temp.add(String.valueOf(resultSet.getObject(i)));
                }
                System.out.println("");
                alldata.add(temp);
            }
            result = "成功打印";
            //StringTableAlign stringTableAlign = new StringTableAlign(Justify.CENTER);
            //result = stringTableAlign.format(header, alldata);
        } catch (SQLException e) {
            error = -1; // 这个属于表格不存在吧？虾鸡扒输入的锅，下同
            result = "表格不存在或无法查看";
            //e.printStackTrace();
            return false;
        }

        return true;
    }

    // 插入教师
    public boolean insert_instructor(String name, String id, String dept_name, String password) {
        char[] t = id.toCharArray();
        if (t[0] != 'T' || t.length != 4) {
            System.out.println("id不符合要求");
            return false;
        }

        try {
            String sql = "select dept_name from department where dept_name = '" + dept_name + "'";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                ;
            }
            else {
                System.out.println("院系不存在");
                error = -1;
                return false;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
        }

        try {
            String sql1 = "insert into instructor values (?, ?, ?, ?)";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,id);
            ps.setString(2,name);
            ps.setString(3,dept_name);
            ps.setString(4,password);
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }
        return true;
    }

    // 插入学生
    public boolean insert_student(String name, String id, String dept_name, String credits, String password) {
        char[] t = id.toCharArray();
        if (t[0] != 'S' || t.length != 4) {
            System.out.println("id不符合要求");
            return false;
        }

        try {
            String sql = "select dept_name from department where dept_name = '" + dept_name + "'";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                ;
            }
            else {
                System.out.println("院系不存在");
                error = -1;
                return false;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
        }

        try {
            String sql1 = "insert into student values (?, ?, ?, ?, ?)";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,id);
            ps.setString(2,name);
            ps.setString(3,dept_name);
            ps.setString(4,credits);
            ps.setString(5,password);
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }
        return true;
    }

    // 插入时间
    public boolean insert_time(String time_slot_id, String day, String start, String end) {
        try {
            String sql1 = "insert into time_slot values (?, ?, ?, ?)";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,time_slot_id);
            ps.setString(2,day);
            ps.setString(3,start);
            ps.setString(4,end);
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }
        return true;
    }

    // 插入考试
    public boolean insert_exam(String exam_id, String day, String start, String end, String type) {
        try {
            String sql1 = "insert into exam values (?, ?, ?, ?, ?)";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,exam_id);
            ps.setString(2,day);
            ps.setString(3,start);
            ps.setString(4,end);
            ps.setString(5,type);
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }
        return true;
    }

    // 返回true说明教室和时间不冲突
    public boolean time_is_proper(String time_slot_id, String room_number, String course_id, String sec_id) {
        ArrayList<String> time = new ArrayList<>();
        String[] thistime = time_slot_id.split(",");
        try {
            String sql = "select time_slot_id from section where room_number = '" + room_number + "' and ( course_id <> '"
                    + course_id + "' or sec_id <> '" + sec_id + "' )";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = String.valueOf(resultSet.getObject(1));
                String[] ids = temp.split(",");
                for (String id : ids) {
                    if (!time.contains(id))
                        time.add(id);
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
        }
        for (String t : thistime) {
            String day = "星期一";
            int start = 0, end = 0;
            try {
                String sql = "select * from time_slot where time_slot_id = '" + t + "'";
                Connection connection = utils.getConnection();
                PreparedStatement ps =connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    day = String.valueOf(resultSet.getObject(2));
                    start = Integer.valueOf(String.valueOf(resultSet.getObject(3)).split(":")[0]);
                    end = Integer.valueOf(String.valueOf(resultSet.getObject(4)).split(":")[0]);
                }
            } catch (SQLException e) {
                error = -1;
                e.printStackTrace();
            }
            try {
                for (int i = 0; i < time.size(); i++) {
                    String sql = "select * from time_slot where time_slot_id = '" + time.get(i) + "'";
                    Connection connection = utils.getConnection();
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        String day1 = String.valueOf(resultSet.getObject(2));
                        int start1 = Integer.valueOf(String.valueOf(resultSet.getObject(3)).split(":")[0]);
                        int end1 = Integer.valueOf(String.valueOf(resultSet.getObject(4)).split(":")[0]);
                        if (day.equals(day1) == false)
                            continue;
                        if (start >= start1 && start < end1)
                            return false;
                        if (end > start1 && end <= end1)
                            return false;
                    }
                }
            } catch (SQLException e) {
                //e.printStackTrace();
                error = -1;
            }
        }

        return true;
    }

    // 教师不能同时上两门课，所以要看一下
    public boolean instructor_is_proper(String i_id, String time_slot_id, String course_id, String sec_id) {
        ArrayList<String> course = new ArrayList<>();
        ArrayList<String> section = new ArrayList<>();
        try {
            String sql = "select course_id, sec_id from teaches where i_id = '" + i_id + "' and ( course_id <> '"
                    + course_id + "' or sec_id <> '" + sec_id + "' )";
            Connection connection = utils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = String.valueOf(resultSet.getObject(1));
                String temp1 = String.valueOf(resultSet.getObject(2));
                course.add(temp);
                section.add(temp1);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
        }
        int len = course.size();
        ArrayList<String> time = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            try {
                String c = course.get(i);
                String s = section.get(i);
                String sql = "select time_slot_id from section where course_id = ? and sec_id = ? ";
                Connection connection = utils.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, c);
                ps.setString(2, s);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String temp = String.valueOf(resultSet.getObject(1));
                    String[] temps = temp.split(",");
                    for (String element : temps) {
                        if (!time.contains(element)) {
                            time.add(element);
                        }
                    }
                }
            } catch (SQLException e) {
                //e.printStackTrace();
                error = -1;
            }
        }
        String day = "星期一";
        int start = 0, end = 0;
        try {
            String sql = "select * from time_slot where time_slot_id = '" + time_slot_id + "'";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                day = String.valueOf(resultSet.getObject(2));
                start = Integer.valueOf(String.valueOf(resultSet.getObject(3)).split(":")[0]);
                end = Integer.valueOf(String.valueOf(resultSet.getObject(4)).split(":")[0]);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
        }
        try {
            for (int i = 0; i < time.size(); i++) {
                String sql = "select * from time_slot where time_slot_id = '" + time.get(i) + "'";
                Connection connection = utils.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String day1 = String.valueOf(resultSet.getObject(2));
                    int start1 = Integer.valueOf(String.valueOf(resultSet.getObject(3)).split(":")[0]);
                    int end1 = Integer.valueOf(String.valueOf(resultSet.getObject(4)).split(":")[0]);
                    if (day.equals(day1) == false)
                        continue;
                    if (start >= start1 && start < end1)
                        return false;
                    if (end > start1 && end <= end1)
                        return false;
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
        }
        return true;
    }

    // 考试的时间和地点不冲突
    public boolean exam_is_prpoer(String exam_id, String room_number) {
        ArrayList<String> exam = new ArrayList<>();
        try {
            String sql = "select exam_id from section where room_number = '" + room_number + "' and exam_id <> '" + exam_id + "'";
            Connection connection = utils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = String.valueOf(resultSet.getObject(1));
                exam.add(temp);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
        }
        String day = "星期一";
        int start = 0, end = 0;
        try {
            String sql = "select * from exam where exam_id = '" + exam_id + "'";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                day = String.valueOf(resultSet.getObject(2));
                start = Integer.valueOf(String.valueOf(resultSet.getObject(3)).split(":")[0]);
                end = Integer.valueOf(String.valueOf(resultSet.getObject(4)).split(":")[0]);
            }
        } catch (SQLException e) {
            //error = -1;
            e.printStackTrace();
        }
        for (int i = 0; i < exam.size(); i++) {
            String temp_id = exam.get(i);
            try {
                String sql = "select * from exam where exam_id = '" + temp_id + "'";
                Connection connection = utils.getConnection();
                PreparedStatement ps =connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String day1 = String.valueOf(resultSet.getObject(2));
                    int start1 = Integer.valueOf(String.valueOf(resultSet.getObject(3)).split(":")[0]);
                    int end1 = Integer.valueOf(String.valueOf(resultSet.getObject(4)).split(":")[0]);
                    if (day.equals(day1) == false)
                        continue;
                    if (start >= start1 && start < end1)
                        return false;
                    if (end > start1 && end <= end1)
                        return false;
                }
            } catch (SQLException e) {
                //error = -1;
                e.printStackTrace();
            }
        }
        return true;
    }

    // 开课，注意time_slot_id是用逗号分隔的，需要补充教室在时间段内是否被占用
    // 输入time_slot_id和exam_id的好处是处理方便，所以需要之前就设置好
    public boolean create_course(String t_id, String course_name, String course_id,
                                 String dept_name, String credits, String number,
                                 String sec_id, String time_slot_id, String room_number,
                                 String exam_id) {
        if (is_time_valid() == false) {
            System.out.println("不在选课时间");
            error = 1;
            return false;
        }

        if ((!course_id.matches("[0-9A-Za-z_]*")) || (!sec_id.matches("[0-9A-Za-z_]*"))) {
            System.out.println("course_id 或 sec_id不符合要求");
            error = -1;
            return false;
        }

        String[] slot = time_slot_id.split(",");
        // 检查教室是否满员
        try {
            String sql1 = "select * from classroom where room_number = ? " +
                    "and capacity >= ?";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,room_number);
            ps.setString(2,number);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                ;
            }
            else {
                System.out.println("教室的容纳能力不足！");
                error = 2;
                return false;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }
        // 检查教室和教师是否被占用
        String[] ids = time_slot_id.split(",");
        for (String id : ids) {
            if (time_is_proper(id, room_number, course_id, sec_id) == false) {
                System.out.println("时间和教室冲突！");
                error = 3;
                return false;
            }
        }
        for (String id : ids) {
            if (instructor_is_proper(t_id, id, course_id, sec_id) == false) {
                System.out.println("时间和教师冲突！");
                error = 4;
                return false;
            }
        }
        // 考试教室被占用
        if (exam_is_prpoer(exam_id, room_number) == false) {
            System.out.println("考试时间和教室冲突！");
            error = 5;
            return false;
        }

        // 在course里创建
        try {
            String sql1 = "insert into course values (?, ?, ?, ?, ?)";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,course_id);
            ps.setString(2,course_name);
            ps.setString(3,dept_name);
            ps.setString(4,credits);
            ps.setString(5,number);
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }
        // 在section里创建，学期和年份采用默认的了
        try {
            String sql1 = "insert into section values (?, ?, ?, ?, ?, ?, ?)";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,course_id);
            ps.setString(2,sec_id);
            ps.setString(3,"spring");
            ps.setString(4,"2019");
            ps.setString(5,room_number);
            ps.setString(6,time_slot_id);
            ps.setString(7,exam_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }

        // 在teach里创建
        try {
            String sql1 = "insert into teaches values (?, ?, ?, ?, ?)";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,t_id);
            ps.setString(2,course_id);
            ps.setString(3,sec_id);
            ps.setString(4,"spring");
            ps.setString(5,"2019");
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }

        return true;
    }

    // 删课
    public boolean delete_course(String course_id, String sec_id) {
        if (is_time_valid() == false) {
            return false;
        }
        try {
            String sql1 = "select * from takes where course_id = ? " +
                    "and sec_id = ?";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,course_id);
            ps.setString(2,sec_id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                System.out.println("有人选课，不能删");
                error = 6;
                return false;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }

        try {
            String sql1 = "select * from section where course_id = ? " +
                    "and sec_id = ?";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,course_id);
            ps.setString(2,sec_id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                ;
            }
            else {
                System.out.println("课程不存在，无法删");
                error = 6;
                return false;
            }
        } catch (SQLException e) {
           // e.printStackTrace();
            error = -1;
            return false;
        }
        // 是否需要判断section里是否删光了，删光了就删course？这个我没写，course我觉得似乎可以不用删

        // 从section里删
        try {
            String sql1 = "delete from section where course_id = ? and sec_id = ?";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,course_id);
            ps.setString(2,sec_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }
        // 从teach里删
        try {
            String sql1 = "delete from teaches where course_id = ? and sec_id = ?";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,course_id);
            ps.setString(2,sec_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            error = -1;
            return false;
        }

        // 检测create是否删
        try {
            String sql1 = "select * from section where course_id = ? ";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,course_id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                System.out.println("有其他section，此次不删");
                return true;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }
        try {
            String sql1 = "delete from course where course_id = ?";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql1);
            ps.setString(1,course_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            error = -1;
            return false;
        }
        return true;
    }


    // 关于合法时间，我认为跟学生是一样的，就复制过来了
    public boolean is_time_valid(){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse("2019-12-1");
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse("2019-12-30");
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        Date date =new Date();
        if (date.getTime()<=date2.getTime() && date.getTime()>=date1.getTime())
            return true;
        return false;
    }

}
