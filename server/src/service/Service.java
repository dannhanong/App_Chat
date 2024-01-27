/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import model.Model_Client;
import model.Model_Create;
import model.Model_Login;
import model.Model_Message;
import model.Model_Receive_Message;
import model.Model_Send_Message;
import model.Model_User_Account;

/**
 *
 * @author Admin
 */
public class Service {
    private static Service instance;
    
    private SocketIOServer server;
    
    private ServiceUser serviceUser;
    
    private List<Model_Client> listClient;
    
    private JTextArea textArea;
    
    private final int PORT_NUMBER = 9999;
        
    public static Service getInstance(JTextArea textArea){
        if(instance == null){
            instance = new Service(textArea);
        }
        return instance;
    }
    
    private Service(JTextArea textArea){
        this.textArea = textArea;
        serviceUser = new ServiceUser();
        listClient = new ArrayList<>();
    }
    
    public void startServer(){
        Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        
        server.addConnectListener(new ConnectListener(){
            @Override
            public void onConnect(SocketIOClient sioc) {
                textArea.append("1 user connected \n");
            }
         
        });
        server.addEventListener("create", Model_Create.class, new DataListener<Model_Create>() {
            @Override
            public void onData(SocketIOClient sioc, Model_Create t, AckRequest ar) throws Exception {
                
                Model_Message message = serviceUser.create(t);
                ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
                if(message.isAction()){
                    textArea.append("User Created:"+t.getUserName()+ " Pass: "+t.getPassword() + "\n");
                    server.getBroadcastOperations().sendEvent("list_user", (Model_User_Account)message.getData());
                    addClient(sioc, (Model_User_Account)message.getData());
                }
          
            }
        });
        
        server.addEventListener("login", Model_Login.class, new DataListener<Model_Login>(){
            @Override
            public void onData(SocketIOClient sioc, Model_Login t, AckRequest ar) throws Exception {
                Model_User_Account login = serviceUser.login(t);
                if(login != null){
                    ar.sendAckData(true, login);
                    addClient(sioc, login);
                    userConnect(login.getUserID());
                }else{
                    ar.sendAckData(false);
                }
            }
            
        });
        
        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer userID, AckRequest ar) throws Exception {
                try {
                    List<Model_User_Account> list = serviceUser.getUser(userID);
                    sioc.sendEvent("list_user", list.toArray());
                } catch (Exception e) {
                }
            }
        });
        
        server.addEventListener("send_to_user", Model_Send_Message.class, new DataListener<Model_Send_Message>() {
            @Override
            public void onData(SocketIOClient sioc, Model_Send_Message t, AckRequest ar) throws Exception {
                sendToClient(t);
                System.out.println("t: "+t);
            }
        });
        
        server.addDisconnectListener(new DisconnectListener(){
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                int userID = removeClient(sioc);
                if(userID != 0){
                    userDisconnect(userID);
                }
            }
        });
        
        server.start();
        textArea.append("Port: "+PORT_NUMBER+"\n");
    }
    
    private void userConnect(int userID){
        server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }
    
    private void userDisconnect(int userID){
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }
    
    private void addClient(SocketIOClient client, Model_User_Account user){
        listClient.add(new Model_Client(client, user)); 
    }
    
    private void sendToClient(Model_Send_Message data){
        System.out.println(listClient);
        for(Model_Client c : listClient){
            if(c.getUser().getUserID() == data.getToUserID()){
                c.getClient().sendEvent("receive_ms", new Model_Receive_Message(data.getFromUserID(), data.getText()));
                break;
            }
        }
    }
    
    public int removeClient(SocketIOClient client){
        for(Model_Client d : listClient){
            if(d.getClient() == client){
                listClient.remove(d);
                return d.getUser().getUserID();
            }
        }
        return 0;
    }

    public List<Model_Client> getListClient() {
        return listClient;
    }
}
