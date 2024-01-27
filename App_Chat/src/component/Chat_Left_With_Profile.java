/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Admin
 */
public class Chat_Left_With_Profile extends javax.swing.JLayeredPane {

    /**
     * Creates new form Chat_Left
     */
    public Chat_Left_With_Profile() {
        initComponents();
        txt.setBackground(new Color(242, 242, 242));
    }
    
    public void setUserProfile(String user){
        txt.setUserProfile(user);
    }
    
    public void setImageProfile(Icon image){
        AvtImage.setImage(image);
    }
    
    public void setText(String text){
        if(text.equals("")){
            txt.hideText();
        }else{
            txt.setText(text);
        }
    }
    
    public void setImage(Icon... image){
        txt.setImage(false, image);
    }
    
    public void setImage(String... image){
        txt.setImage(false, image);
    }
    
    public void setTime(){
        txt.setTime("10:30 AM");
    }
    
    public void setFile(String fileName, String fileSize){
        txt.setFile(fileName, fileSize);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        AvtImage = new Swing.ImageAvatar();
        txt = new component.Chat_Item();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        AvtImage.setBorderColor(new java.awt.Color(255, 255, 255));
        AvtImage.setBorderSize(0);
        AvtImage.setImage(new javax.swing.ImageIcon(getClass().getResource("/icons/doremon.jpg"))); // NOI18N
        AvtImage.setMaximumSize(new java.awt.Dimension(30, 30));
        AvtImage.setMinimumSize(new java.awt.Dimension(30, 30));

        jLayeredPane1.setLayer(AvtImage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(AvtImage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(AvtImage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(jLayeredPane1);

        txt.setMaximumSize(new java.awt.Dimension(250, 500));
        add(txt);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Swing.ImageAvatar AvtImage;
    private javax.swing.JLayeredPane jLayeredPane1;
    private component.Chat_Item txt;
    // End of variables declaration//GEN-END:variables
}