package Utils;

import java.sql.*;
//数据库连接
public class JDBCUtils {
    private static String URL ="jdbc:mysql://localhost:3306/database?serverTimezone=UTC&characterEncoding=utf-8";
    private static String USER ="root";
    private static String PASSWORD ="123456";

    public JDBCUtils(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        Connection connection =null;
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public void close(ResultSet resultSet,Statement statement,Connection connection){
        try {
            if (resultSet!=null)
                resultSet.close();
            if (statement!=null)
                statement.close();
            if (connection!=null)
                connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public PreparedStatement getStatement(String sql){
        PreparedStatement ps =null;
        try {
            Connection connection =getConnection();
            ps =connection.prepareStatement(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ps;
    }
}
