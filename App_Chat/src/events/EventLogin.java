/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package events;

import model.Model_Create;
import model.Model_Login;
import model.Model_Message;

/**
 *
 * @author Admin
 */
public interface EventLogin {
    public void login(Model_Login data);
    
    public void create(Model_Create data, EventMessage message);
    
    public void goCreate();
    
    public void goLogin();
}
