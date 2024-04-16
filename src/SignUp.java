import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.*;

import com.formdev.flatlaf.FlatLightLaf;

public class SignUp extends MioFrame implements ActionListener,WindowListener{
    JTextField t1, t2;
    JButton b1;

    public SignUp(){
        setLayout(null);

        t1 = new JTextField(60);
        t1.setBounds(100, 20, 80, 30);

        t2 = new JPasswordField(60);
        t2.setBounds(100, 60, 80, 30);

        b1 = new JButton("Sign Up");
        b1.setBounds(100, 100, 80, 30);

        b1.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter fw = new FileWriter("login.txt", true);
                    fw.write(t1.getText()+"\t"+t2.getText());
                    fw.close();
                    JFrame f = new JFrame();
                    JOptionPane.showMessageDialog(f, "Registration Completed");
                    dispose();
                }catch(Exception exception){}
            }
        }));

        add(t1);
        add(t2);
        add(b1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
