package vn.iotstar.baitapgiuaky_chuanbi.sqlServer;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    private static String ip = "172.172.0.4";
    private static String port = "1433";
    private static String classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "FastFoodStore";
    private static String username = "test";
    private static String password = "123456";
    private static Connection connection;

    // Phương thức kết nối đến cơ sở dữ liệu SQL Server
    public static Connection connectToDatabase() {
//        connection = null;
//        try {
//            Class.forName(classes);
//            connection = DriverManager.getConnection(url, username, password);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return connection; // Trả về kết nối
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            connection = null;
            try{
                Class.forName(classes);
                String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databaseName=" + database;
                connection = DriverManager.getConnection(url, username, password);
            }
            catch (ClassNotFoundException | SQLException e ){
                throw  new RuntimeException(e);
            }
            return connection;
    }
}

