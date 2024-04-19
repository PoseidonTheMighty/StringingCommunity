import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.*;


public class Schermata extends MioFrame implements ActionListener,WindowListener {

    JTextField t1, t2;
    JButton b1, b2, b3;
    JLabel l1, l2,l3,l4;

    public Schermata(String titolo){
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //ciao

        l1 = new JLabel("Stringing Community");
        l1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        l1.setForeground(Color.RED);
        l1.setBounds(20, 10, 315, 30);



        l3 = new JLabel("Email:");
        l3.setBounds(60, 60, 60, 30);

        t1 = new JTextField(60);
        t1.setBounds(100, 60, 120, 30);

        l2 = new JLabel("");
        l2.setBounds(80, 220, 300, 30);

        l4 = new JLabel("Password:");
        l4.setBounds(40, 100, 60, 30);

        t2 = new JPasswordField(60);
        t2.setBounds(100, 100, 120, 30);


        b1 = new JButton("Sign In");
        b1.setBounds(120, 140, 80, 30);


        b2 = new JButton("Sign Up");
        b2.setBounds(120, 180, 80, 30);

        b3 = new JButton("Reset Password");
        b3.setBounds(205, 160, 120, 30);

        add(l3);
        add(l1);
        add(l2);
        add(t1);
        add(l4);
        add(t2);
        add(b1);
        add(b2);
        add(b3);




        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean matched = false;
                String uname = t1.getText().toString();
                String pwd = t2.getText().toString();

                try {
                    FileReader fr = new FileReader("login.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.equals(uname + "\t" + pwd)) {
                            matched = true;
                            break;
                        }
                    }
                    fr.close();
                }catch(Exception exception){

                }


                if(matched){
                    dispose();
                    LoginSc sc = new LoginSc("StringingCommunity");
                    sc.setBounds(400, 200, 400, 300);
                    sc.setVisible(true);
                }
                else{
                    l2.setText("Invalid Username or Password");
                }

                if(!matched) {
                    add(b3);
                }


                /*if(t1.getText().toString().equals("admin") && t2.getText().toString().equals("ciao")){
                    dispose();
                    LoginSc sc = new LoginSc("StringingCommunity");
                    sc.setBounds(400, 200, 400, 300);
                    sc.setVisible(true);
                }
                else
                    l2.setText("Invalid Username or Password");*/
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUp s = new SignUp();
                s.setVisible(true);
                s.setBounds(200, 200, 500, 300);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


