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
    JButton b1, b2;
    JLabel l1, l2;

    public Schermata(String titolo){
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        l1 = new JLabel("Login");
        l1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        l1.setForeground(Color.RED);
        l1.setBounds(130, 10, 300, 30);



        t1 = new JTextField(60);
        t1.setBounds(100, 60, 120, 30);

        l2 = new JLabel("");
        l2.setBounds(250, 80, 300, 30);


        t2 = new JPasswordField(60);
        t2.setBounds(100, 100, 120, 30);


        b1 = new JButton("Sign In");
        b1.setBounds(120, 140, 80, 30);


        b2 = new JButton("Sign Up");
        b2.setBounds(120, 170, 80, 30);

        add(l1);
        add(l2);
        add(t1);
        add(t2);
        add(b1);
        add(b2);




        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(t1.getText().toString().equals("admin") && t2.getText().toString().equals("ciao")){
                    dispose();
                    LoginSc sc = new LoginSc("StringingCommunity");
                    sc.setBounds(400, 200, 400, 300);
                    sc.setVisible(true);
                }
                else
                    l2.setText("Invalid Username or Password");
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

        /*this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();

        JLabel email = new JLabel("E-Mail:");
        JLabel pass = new JLabel("Password:");

        JTextField input_email = new JTextField();
        JTextField input_pass = new JTextField();

        JButton log = new JButton("LOGIN");
        JButton reg = new JButton("REGISTER");

        panel.add(email);
        panel.add(input_email);
        panel.add(pass);
        panel.add(input_pass);
        panel.add(log);
        panel.add(reg);
        panel.setPreferredSize(new Dimension(400, 100));
        panel.setMaximumSize(new Dimension(400, 100));
        panel.setBorder(BorderFactory.createTitledBorder("demo"));

        this.getContentPane().add(panel);
        this.setSize(550, 300);
        this.setVisible(true);*/
        }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


