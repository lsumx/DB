package dao;

import Utils.JDBCUtils;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RootDAO {
    JDBCUtils utils =new JDBCUtils();
    public boolean login_success(String id){
        if (id.equals("root"))
            return true;
        return false;
    }

//    展示表格
    public void show_information(String table_name){
        try {
            String sql_show ="select * from "+table_name;
            PreparedStatement statement =utils.getStatement(sql_show);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
