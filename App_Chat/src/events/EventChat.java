/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package events;

import model.Model_Receive_Message;
import model.Model_Send_Message;

/**
 *
 * @author Admin
 */
public interface EventChat {
    public void sendMesage(Model_Send_Message data);
    
    public void receiveMessage(Model_Receive_Message data);
}
