import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PswForgot extends MioFrame implements ActionListener,WindowListener{
    JTextField t1, t2;
    JButton b1;
    JLabel l1, l2, l3, l5;

    public PswForgot(){



        l3 = new JLabel("Reset Password");
        l3.setFont(new Font("Times New Roman", Font.BOLD, 30));
        l3.setForeground(Color.RED);
        l3.setBounds(75, 20, 300, 40);

        l1 = new JLabel("Email:");
        l1.setBounds(60, 60, 80, 30);

        l2 = new JLabel("Password:");
        l2.setBounds(40, 100, 80, 30);

        t1 = new JTextField(60);
        t1.setBounds(100, 60, 80, 30);

        t2 = new JPasswordField(60);
        t2.setBounds(100, 100, 80, 30);

        b1 = new JButton("Sign Up");
        b1.setBounds(100, 140, 80, 30);

        b1.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uname = t1.getText().trim();
                String pwd = t2.getText().trim();

                if (uname.isEmpty() || pwd.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Stringing Community dice:\n       Non hai scritto nulla!", "Stringing Community", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    FileWriter fw = new FileWriter("login.txt", true);
                    fw.write(t1.getText()+"\t"+t2.getText()+"\n");
                    fw.close();
                    JFrame f = new JFrame();
                    JOptionPane.showMessageDialog(f, "Registration Completed");
                    dispose();
                }catch(Exception exception){}
            }
        }));



        add(l3);
        add(l1);
        add(l2);
        add(t1);
        add(t2);
        add(b1);



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}