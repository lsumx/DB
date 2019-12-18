package Utils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileUtils {
    JDBCUtils utils =new JDBCUtils();
    //读取文件并将其写入数据库
    public void readExcel(String path,String course_id){
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
                System.out.println(1);
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
                        String sql1 ="update takes set grade ="+sheet.getCell(1,j).getContents()+"  where s_id="+sheet.getCell(0,j).getContents()+" and course_id ="+course_id ;
                        System.out.println(sql1);
                        PreparedStatement statement =utils.getStatement(sql);
                        statement.setString(1,sheet.getCell(j,1).getContents());
                        statement.setString(2,sheet.getCell(j,0).getContents());
                        statement.setString(3,course_id);
//                        statement.execute();
                    }
                }



            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            }

        }

    }

    public void writeExcel(String fileName){
        File file =new File(fileName+".xls");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                WritableWorkbook workbook =Workbook.createWorkbook(file);
                Sheet sheet  =workbook.createSheet(fileName,0);

            String sql ="select * from classroom";
            PreparedStatement statement =utils.getStatement(sql);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                   String colName = resultSet.getString(1);
                   System.out.println(colName);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            }



        }
    }

    public static void main(String[] args) {
        FileUtils fileUtils =new FileUtils();
        fileUtils.readExcel("C:\\Users\\bhlb\\Desktop\\DB\\src\\1.xls","CS001");
//        fileUtils.writeExcel("classroom");

    }

}
