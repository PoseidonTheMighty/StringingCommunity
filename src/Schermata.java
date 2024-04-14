import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import com.formdev.flatlaf.FlatLightLaf;

public class Schermata extends MioFrame implements ActionListener,WindowListener {



    public Schermata(String titolo){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        this.setVisible(true);
        }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


