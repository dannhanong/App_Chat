/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.json.*;

/**
 *
 * @author Admin
 */
public class Model_Create {
    String userName;
    String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Model_Create() {
    }

    public Model_Create(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    public JSONObject toJsonObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("userName", userName);
            json.put("password", password);

            return json;
        } catch (Exception e) {
            return null;
        }
    }
}
