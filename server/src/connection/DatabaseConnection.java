/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class DatabaseConnection {
    private Connection connection;
    private static DatabaseConnection instance;
    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    private DatabaseConnection(){
        
    }
    
    public void connectToDatabase() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String server = "localhost";
        String port = "3306";
        String database = "appchat";
        String userName = "root";
        String password = "123456";
        connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+database, userName, password);
    }
    
    public Connection getConnection(){
        return connection;
    }
    
    public void setConnectiob(Connection connection){
        this.connection = connection;
    }
}
