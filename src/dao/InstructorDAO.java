package dao;

import Utils.JDBCUtils;
import entity.CourseEntity;
import entity.StudentEntity;
import entity.TakeEntity;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InstructorDAO {
    JDBCUtils utils =new JDBCUtils();
    public boolean login_success(String id,String password){
        try {
        String sql ="select * from instructor where i_id = ?";
        Connection connection = utils.getConnection();
        PreparedStatement ps =connection.prepareStatement(sql);
        ps.setString(1,id);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next() && password.equals("000000")){
            System.out.println("登陆成功");
//            studentEntity.setId(id);
            return true;
        }
        else {
            System.out.println("登录失败");
            return false;
        }

    } catch (
    SQLException e) {
        e.printStackTrace();
    }
       return false;

}

    public ArrayList<CourseEntity> see_course(String id){
        ArrayList<CourseEntity> arrayList =new ArrayList<>();
        try{
            String sql ="select course_id from teaches where i_id =?";
            PreparedStatement statement =utils.getStatement(sql);
            statement.setString(1,id);
            statement.execute();
            ArrayList<String > courses =new ArrayList<>();
            while (statement.getResultSet().next()){
                courses.add(statement.getResultSet().getString(1));
            }
            for (int i=0;i<courses.size();i++){
                String sql_course = "select title from course where course_id =?";
                PreparedStatement statement1 =utils.getStatement(sql_course);
                statement1.setString(1,courses.get(i));
                statement1.execute();
                String course_name="";
                if (statement1.getResultSet().next())
                    course_name =statement1.getResultSet().getString(1);
                arrayList.add(new CourseEntity(courses.get(i),course_name,"","","","",""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    public ArrayList<StudentEntity> see_students(String course_id){
        ArrayList<StudentEntity> arrayList =new ArrayList<>();
        try {
            String sql ="select s_id,grade from takes where course_id =?";
            PreparedStatement statement =utils.getStatement(sql);
            statement.setString(1,course_id);
            statement.execute();
            String s_id ="";
            String grade="";
            while (statement.getResultSet().next()){
                s_id =statement.getResultSet().getString(1);
                grade =statement.getResultSet().getString(2);
                String sql_student ="select name,dept_name from student where s_id =?";
                PreparedStatement preparedStatement =utils.getStatement(sql_student);
                preparedStatement.setString(1,s_id);
                preparedStatement.execute();
                if (preparedStatement.getResultSet().next()){
                    String name =preparedStatement.getResultSet().getString(1);
                    String dept_name =preparedStatement.getResultSet().getString(2);
                    arrayList.add(new StudentEntity(s_id,name,dept_name,"",grade));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    public void deal_selections(String id,String course_id){
        try {
            String sql ="insert into takes(s_id,course_id) values(?,?) ";
            PreparedStatement statement =utils.getStatement(sql);
            statement.setString(1,id);
            statement.setString(2,course_id);
            statement.executeUpdate();

            String sql_delete ="update course_select set status =? where s_id =? and course_id =?";
            PreparedStatement preparedStatement =utils.getStatement(sql_delete);
            preparedStatement.setString(1,"1");
            preparedStatement.setString(2,id);
            preparedStatement.setString(3,course_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refuse(String id,String course_id){
        try {
            String sql_delete ="update course_select set status =? where s_id =? and course_id =?";
            PreparedStatement preparedStatement =utils.getStatement(sql_delete);
            preparedStatement.setString(1,"0");
            preparedStatement.setString(2,id);
            preparedStatement.setString(3,course_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //传入参数应该是教师的id,返回的是学生对这个老师的选课申请arraylist
    public ArrayList<TakeEntity> see_selections(String id){
        ArrayList<TakeEntity> arrayList =new ArrayList<>();
        try{
//            这一步用来找到和这个老师相关的courseID
            String sql_course ="select course_id from teaches where i_id =? ";
            PreparedStatement statement =utils.getStatement(sql_course);
            statement.setString(1,id);
            statement.execute();
            ResultSet resultSet =statement.getResultSet();

            while (resultSet.next()){
                String course_id =resultSet.getString(1);
                String sql_student ="select s_id,reason from course_select where course_id =?";
                PreparedStatement statement1 =utils.getStatement(sql_student);
                statement1.setString(1,course_id);
                statement1.execute();
                String s_id ="";
                String reasons ="";
                if (statement1.getResultSet().next()){
//                    学生的id
                            s_id =statement1.getResultSet().getString(1);
                            reasons =statement1.getResultSet().getString(2);

                }
//
//                通过学生id找到学生的姓名
                String sql_sname ="select name from student where s_id =?";
                PreparedStatement preparedStatement =utils.getStatement(sql_sname);
                preparedStatement.setString(1,s_id);
                preparedStatement.execute();
                String sname="";
                if (preparedStatement.getResultSet().next())
                    sname =preparedStatement.getResultSet().getString(1);
//                通过课程id找到课程名称
                String sql_cname ="select title from course where course_id =?";
                PreparedStatement preparedStatement1 =utils.getStatement(sql_cname);
                preparedStatement1.setString(1,course_id);
                preparedStatement1.execute();
                String cname="";
                if (preparedStatement1.getResultSet().next())
                    cname =preparedStatement1.getResultSet().getString(1);
//                System.out.println(s_id+" "+sname);
                if (s_id!=null && sname !=null &&course_id !=null &&cname !=null
                &&s_id!="" && sname !="" &&course_id !="" &&cname !="" )
                    arrayList.add(new TakeEntity(s_id,sname,course_id,cname,reasons,""));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayList;
    }



//    貌似有问题
    public boolean readExcel(String path,String course_id){
//        System.out.println(234);
        File file =new File(path);
        FileInputStream fis = null;
        Workbook workbook =null;
        if (file.exists()){
//            System.out.println(20);
            try {
                fis =new FileInputStream(file);
                workbook =Workbook.getWorkbook(fis);
//                workbook =Workbook.getWorkbook(file);
//                Sheet sheets[] =workbook.getSheets();
                int number =workbook.getNumberOfSheets();
//                System.out.println(1);
                //遍历工作表
                for (int i=0;i<number;i++){
                    Sheet sheet =workbook.getSheet(i);
                    String sheetName =sheet.getName();//表名，对应数据库中的表名
                    int rows =sheet.getRows();
                    int cols =sheet.getColumns();
//                    String [] keys = new String[cols];
                    if (sheet ==null)
                        continue;

                    for (int j =1;j<rows;j++){
                        String sql ="update takes set grade =?  where s_id=? and course_id =?" ;
//                        String sql1 ="update takes set grade ="+sheet.getCell(1,j).getContents()+"  where s_id="+sheet.getCell(0,j).getContents()+" and course_id ="+course_id ;
//                        System.out.println(sql1);
                        PreparedStatement statement =utils.getStatement(sql);
                        statement.setString(1,sheet.getCell(1,j).getContents());
                        statement.setString(2,sheet.getCell(0,j).getContents());
                        statement.setString(3,course_id);
                        statement.executeUpdate();

                    }
                }
                return true;



            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            }

        }else {
            System.out.println("文件不存在");
            return false;
        }

        return false;

    }





}
