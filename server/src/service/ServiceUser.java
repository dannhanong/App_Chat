/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Model_Client;
import model.Model_Create;
import model.Model_Login;
import model.Model_Message;
import model.Model_User_Account;

/**
 *
 * @author Admin
 */
public class ServiceUser {
    
    public ServiceUser(){
        this.con = DatabaseConnection.getInstance().getConnection();
    }
    
    public Model_Message create(Model_Create data) throws SQLException{
        Model_Message message = new Model_Message();
        System.out.println(data.getUserName());
        System.out.println(data.getPassword());
        
        try {
            PreparedStatement prepared = con.prepareStatement(CHECK_USER);
            prepared.setString(1, data.getUserName());
            ResultSet result = prepared.executeQuery();
            if(result.next()){
                message.setAction(false);
                message.setMessage("User đã tồn tại");
            }else{
                message.setAction(true);
            }

            result.close();
            prepared.close();

            if(message.isAction()){
                con.setAutoCommit(false);
                prepared=con.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                prepared.setString(1, data.getUserName());
                prepared.setString(2, data.getPassword());
                prepared.execute();
                result = prepared.getGeneratedKeys();
                result.next();
                int userID =  result.getInt(1);
                result.close();
                prepared.close();
                //
                prepared = con.prepareStatement(INSERT_USER_ACCOUNT);
                prepared.setInt(1, userID);
                prepared.setString(2, data.getUserName());
                prepared.execute();
                prepared.close();
                con.commit();
                con.setAutoCommit(true);
                
                message.setAction(true);
                message.setMessage("ok");
                message.setData(new Model_User_Account(userID, data.getUserName(), "", "", true));
            }
        } catch (SQLException e) {
            message.setAction(false);
            message.setMessage("Server Error");
            try {
                if(con.getAutoCommit()==false){
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (Exception e1) {
            }
        }
        
        return message;
    }
    
    public Model_User_Account login(Model_Login login) throws SQLException{
        Model_User_Account data = null;
        PreparedStatement prepared = con.prepareStatement(LOGIN);
        prepared.setString(1, login.getUserName());
        prepared.setString(2, login.getPassword());
        ResultSet result = prepared.executeQuery();
        if(result.next()){
            int userID = result.getInt(1);
            String userName = result.getString(2);
            String gender = result.getString(3);
            String image = result.getString(4);
            data = new Model_User_Account(userID, userName, gender, image, true);
        }
        result.close();
        prepared.close();
        return data;
    }
    
    public List<Model_User_Account> getUser(int exitUser) throws SQLException{
        List<Model_User_Account> list = new ArrayList<Model_User_Account>();
        PreparedStatement prepared = con.prepareStatement(SELECT_USER_ACCOUNT);
        prepared.setInt(1, exitUser);
        ResultSet result = prepared.executeQuery();
        while(result.next()){
            int userID = result.getInt(1);
            String userName = result.getString(2);
            String gender = result.getString(3);
            String image = result.getString(4);
            list.add(new Model_User_Account(userID, userName, gender, image, checkUserStatus(userID)));
        }
        result.close();
        prepared.close();
        return list;
    }
    
    private boolean checkUserStatus(int userID){
        List<Model_Client> clients = Service.getInstance(null).getListClient();
        for(Model_Client c : clients){
            if(c.getUser().getUserID() == userID){
                return true;
            }
        }
        return false;
    }
    
    private final String LOGIN = "select * from user join user_account using (UserID) where `user`.UserName=BINARY(?) and `user`.`Pass`=BINARY(?) and user_account.`Status`='1'";
    
    private final String SELECT_USER_ACCOUNT = "select `UserID`, `UserName`, `Gender`, `ImageString` from user_account where user_account.`Status`='1' and `UserID` <> ?";
    
    private final String INSERT_USER = "insert into user (`UserName`, `Pass`) values (?, ?)";
    
    private final String INSERT_USER_ACCOUNT = "insert into user_account (`UserID`, `UserName`) values (?, ?)";
    
    private final String CHECK_USER = "select `UserID` from user where `UserName`=? limit 1";
    
    private final Connection con;
}
