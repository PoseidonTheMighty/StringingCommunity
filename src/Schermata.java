import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.*;

public class Schermata extends MioFrame implements ActionListener, WindowListener {

    JTextField t1, t2;
    JButton b1, b2, b3;
    JLabel l1, l2, l3, l4;

    public Schermata(String titolo) {
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //ciao

        l1 = new JLabel("Stringing Community");
        l1.setFont(new Font("Gotham", Font.BOLD, 30));
        l1.setForeground(Color.white);
        l1.setBounds(10, 10, 315, 35);

        l3 = new JLabel("Email:");
        l3.setFont(new Font("Netflix Sans", Font.ITALIC, 14));
        l3.setForeground(Color.white);
        l3.setBounds(55, 60, 60, 30);

        t1 = new JTextField(60);
        t1.setBounds(100, 60, 120, 30);

        l2 = new JLabel("");
        l2.setBounds(80, 245, 300, 30);

        l4 = new JLabel("Password:");
        l4.setFont(new Font("Netflix Sans", Font.ITALIC, 14));
        l4.setForeground(Color.white);
        l4.setBounds(30, 100, 90, 30);

        t2 = new JPasswordField(60);
        t2.setBounds(100, 100, 120, 30);

        b1 = new JButton("Sign In");
        b1.setFont(new Font("Gotham", Font.BOLD, 14));
        b1.setForeground(Color.black);
        b1.setBackground(Color.white);
        b1.setBounds(110, 140, 100, 33);

        b2 = new JButton("Sign Up");
        b2.setFont(new Font("Gotham", Font.BOLD, 14));
        b2.setForeground(Color.black);
        b2.setBackground(Color.white);
        b2.setBounds(110, 180, 100, 33);

        b3 = new JButton("Reset Password");
        b3.setFont(new Font("Gotham", Font.BOLD, 12));
        b3.setForeground(Color.black);
        b3.setBackground(Color.white);
        b3.setBounds(100, 220, 130, 30);

        add(l3);
        add(l1);
        add(l2);
        add(t1);
        add(l4);
        add(t2);
        add(b1);
        add(b2);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uname = t1.getText().trim();
                String pwd = t2.getText().trim();

                if (uname.isEmpty() || pwd.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Stringing Community dice:\n       Non hai scritto nulla!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean matched = false;

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
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                if (matched) {
                    dispose();
                    LoginSc sc = new LoginSc("StringingCommunity");
                    sc.setBounds(0, 0, 2000, 2000);
                    sc.setVisible(true);
                } else {
                    l2.setText("Invalid Username or Password");
                    add(b3);
                    revalidate();
                    repaint();
                }
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

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PswForgot pf;
                pf = new PswForgot();
                pf.setVisible(true);
                pf.setBounds(400, 200, 330, 250);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
