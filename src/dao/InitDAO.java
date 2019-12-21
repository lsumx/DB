package dao;

import Utils.JDBCUtils;
import java.sql.*;
import java.util.ArrayList;

public class InitDAO {

    public int error_code = 0;

    String id = "System";
    ManagerDAO Init = new ManagerDAO();
    JDBCUtils utils =new JDBCUtils();

    public boolean check_proper() {
        boolean bool = true;
        if (check_exam_proper() == false) {
            System.out.println("考试冲突！");
            error_code = 1;
            bool = false;
            //return false;
        }
        if (check_instructor_proper() == false) {
            System.out.println("教师冲突！");
            error_code = 2;
            bool = false;
            //return false;
        }
        if (check_time_proper() == false) {
            System.out.println("上课冲突！");
            error_code = 3;
            bool = false;
            //return false;
        }
        return bool;
    }

    public boolean check_exam_proper() {
        ArrayList<String> exam = new ArrayList<>();
        ArrayList<String> room = new ArrayList<>();
        ArrayList<String> course = new ArrayList<>();
        ArrayList<String> section = new ArrayList<>();
        try {
            String sql = "select ?, ?, ?, ? from section";
            Connection connection = utils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,"exam_id");
            ps.setString(2,"room_number");
            ps.setString(3,"course_id");
            ps.setString(4,"sec_id");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = String.valueOf(resultSet.getObject(1));
                exam.add(temp);
                String temp1 = String.valueOf(resultSet.getObject(2));
                room.add(temp1);
                String temp2 = String.valueOf(resultSet.getObject(3));
                course.add(temp2);
                String temp3 = String.valueOf(resultSet.getObject(4));
                section.add(temp3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int len = exam.size();
        for (int i = 0; i < len; i++) {
            boolean judge = Init.exam_is_prpoer(exam.get(i), room.get(i));
            if (judge == false)
                return false;
        }
        System.out.println("考试无冲突！");
        return true;
    }

    public boolean check_time_proper() {
        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> room = new ArrayList<>();
        try {
            String sql = "select * from section";
            Connection connection = utils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = String.valueOf(resultSet.getObject(6));
                String temp1 = String.valueOf(resultSet.getObject(5));
                String temp2 = String.valueOf(resultSet.getObject(1));
                String temp3 = String.valueOf(resultSet.getObject(2));
                String[] times = temp.split(",");
                for (String item : times) {
                    boolean judge = Init.time_is_proper(item, temp1, temp2, temp3);
                    if (judge == false)
                        return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("上课无冲突！");
        return true;
    }

    public boolean check_instructor_proper() {
        ArrayList<String> instructor = new ArrayList<>();
        ArrayList<String> course = new ArrayList<>();
        ArrayList<String> section = new ArrayList<>();
        try {
            String sql = "select * from teaches";
            Connection connection = utils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = String.valueOf(resultSet.getObject(1));
                instructor.add(temp);
                String temp1 = String.valueOf(resultSet.getObject(2));
                course.add(temp1);
                String temp2 = String.valueOf(resultSet.getObject(3));
                section.add(temp1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int len = instructor.size();
        for (int i = 0; i < len; i++) {
            try {
                String ins = instructor.get(i);
                String sql = "select time_slot_id from section where course_id = '" + course.get(i) + "' and sec_id = '" + section.get(i) + "'";
                Connection connection = utils.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String temp = String.valueOf(resultSet.getObject(1));
                    String[] times = temp.split(",");
                    for (String item : times) {
                        boolean judge = Init.instructor_is_proper(ins, item, course.get(i), section.get(i));
                        if (judge == false)
                            return false;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("教师无冲突！");
        return true;
    }

}
