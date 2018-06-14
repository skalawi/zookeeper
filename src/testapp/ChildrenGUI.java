package testapp;

import javax.swing.*;
import java.awt.*;

public class ChildrenGUI extends javax.swing.JFrame {

    public ChildrenGUI() {
        initComponents();
    }

    private void initComponents() {
        fahrenheitLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fahrenheitLabel.setText("0");
        getContentPane().setLayout(new FlowLayout());
        setUndecorated(true);
        fahrenheitLabel.setForeground(Color.white);
        fahrenheitLabel.setFont(new Font("Arial", Font.PLAIN, 100));
        fahrenheitLabel.setHorizontalAlignment(JLabel.CENTER);
        fahrenheitLabel.setVerticalAlignment(JLabel.CENTER);
        add(fahrenheitLabel);
        setMinimumSize(new Dimension(150,150));
        getContentPane().setBackground(new Color(41,170,140));
        pack();
    }
    public void changeChildrenCount(int count){
        fahrenheitLabel.setText(Integer.toString(count));
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChildrenGUI().setVisible(true);
            }
        });
    }

    private javax.swing.JLabel fahrenheitLabel;


}