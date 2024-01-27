/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Swing;

/**
 *
 * @author Admin
 */
import javax.swing.Icon;
import javax.swing.JProgressBar;

public class Progress extends JProgressBar {
    private Icon image;
    
    public Icon getImage(){
        return image;
    }
    
    public void setImage(Icon image){
        this.image = image;
    }

    public ProgressType getProgressType() {
        return progressType;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
        repaint();
    }

    private ProgressType progressType = ProgressType.NONE;

    public Progress() {
        setOpaque(false);
        setUI(new ProgressCircleUI(this));
    }

    public static enum ProgressType {
        NONE, DOWN_FILE, CANCEL, FILE
    }
}
